package io.github.jessicaoliveira.msavaliadorcredito.application;

import io.github.jessicaoliveira.msavaliadorcredito.application.exception.DadosClienteNotFound;
import io.github.jessicaoliveira.msavaliadorcredito.application.exception.ErroComunicacaoMicroservices;
import io.github.jessicaoliveira.msavaliadorcredito.application.exception.ErroSolicitacaoCartaoException;
import io.github.jessicaoliveira.msavaliadorcredito.domain.model.*;
import io.github.jessicaoliveira.msavaliadorcredito.infra.Clients;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;
    private final Clients clients;

    @GetMapping
    public String status(){
        return "Ok, está de pé!";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultarSituacaoClient(@RequestParam("cpf") String cpf){
        SituacaoCliente situacaoCliente = null;
        try {
            situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservices e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados){
        try {
           RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservices e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacao-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) {
        try {
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dadosSolicitacaoEmissaoCartao);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        } catch (ErroSolicitacaoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
