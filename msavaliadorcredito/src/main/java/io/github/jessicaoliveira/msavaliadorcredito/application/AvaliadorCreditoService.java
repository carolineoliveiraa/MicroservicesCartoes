package io.github.jessicaoliveira.msavaliadorcredito.application;

import feign.FeignException;
import io.github.jessicaoliveira.msavaliadorcredito.application.exception.DadosClienteNotFound;
import io.github.jessicaoliveira.msavaliadorcredito.application.exception.ErroComunicacaoMicroservices;
import io.github.jessicaoliveira.msavaliadorcredito.application.exception.ErroSolicitacaoCartaoException;
import io.github.jessicaoliveira.msavaliadorcredito.domain.model.*;
import io.github.jessicaoliveira.msavaliadorcredito.infra.Clients;
import io.github.jessicaoliveira.msavaliadorcredito.infra.ClientsCartao;
import io.github.jessicaoliveira.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    //Injecoes de dependencia
    private final Clients clients;
    private final ClientsCartao clientsCartao;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;


    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFound,
            ErroComunicacaoMicroservices {

        try {
            // Faz uma chamada ao microserviço 'dadosCliente' para obter informações do cliente com o CPF fornecido
            ResponseEntity<DadosCliente> dadosClienteResponseEntity = clients.dadosCliente(cpf);
            // Faz uma chamada ao microserviço 'getCartoesByCliente' para obter os cartões de crédito associados ao cliente
            ResponseEntity<List<CartaoCliente>> cartoesResponse = clientsCartao.getCartoesByCliente(Long.valueOf(cpf));
            // Retorna um objeto SituacaoCliente, que contém as informações do cliente e seus cartões de crédito
            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponseEntity.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            // Se ocorrer um erro ao chamar um microserviço, lança a exceção correspondente
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFound();
            }
            throw new ErroComunicacaoMicroservices(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFound, ErroComunicacaoMicroservices {
        try {
            // chama um método em um cliente HTTP usando a biblioteca Feign, passando o CPF como parâmetro
            ResponseEntity<DadosCliente> dadosClienteResponseEntity = clients.dadosCliente(cpf);
            // chama outro método em um cliente HTTP, passando a renda como parâmetro
            ResponseEntity<List<Cartao>> cartoesResponse = clientsCartao.getCartoesRendaAteh(renda);

            // obtém a lista de cartões aprovados para o cliente, calculando o limite aprovado para cada um
            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                // obtém os dados do cliente a partir da resposta do primeiro serviço
                DadosCliente dadosCliente = dadosClienteResponseEntity.getBody();

                // calcula o limite aprovado com base na renda e na idade do cliente
                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal rendaBD = BigDecimal.valueOf(renda);
                BigDecimal idadeBD =  BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                // cria um objeto de cartão aprovado com as informações relevantes
                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            // cria um objeto de retorno contendo a lista de cartões aprovados
            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) {
            // captura exceções lançadas por chamadas aos serviços externos
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFound();
            }
            throw new ErroComunicacaoMicroservices(e.getMessage(), status);

        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao){
        try {
            emissaoCartaoPublisher.solicitarCartao(dadosSolicitacaoEmissaoCartao);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}

