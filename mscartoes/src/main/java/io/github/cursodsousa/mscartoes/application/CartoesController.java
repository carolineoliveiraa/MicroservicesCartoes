package io.github.cursodsousa.mscartoes.application;

import io.github.cursodsousa.mscartoes.application.representation.CartaoSaveRequest;
import io.github.cursodsousa.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.cursodsousa.mscartoes.domain.Cartao;
import io.github.cursodsousa.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesController {

    //Injecoes de dependencia
    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "Tá ok!";
    }

    @PostMapping //Método HTTP POST
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toModel(); //Converte a solicitação recebida em um objeto Cartao
        cartaoService.save(cartao); //Salva o objeto criado usando um serviço
        return ResponseEntity.status(HttpStatus.CREATED).build(); //Retorna uma resposta com o código de status 201 (criado)
    }

    @GetMapping(params = "renda") //Método HTTP GET com o parâmetro "renda"
    public ResponseEntity <List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda){
        List<Cartao> list = cartaoService.getCartaoRendaMenorIgual(renda); //Obtém a lista de cartões com renda menor ou igual ao valor passado como parâmetro
        return ResponseEntity.ok(list); //Retorna a lista obtida em uma resposta com o código de status 200 (OK)
    }

    @GetMapping(params = "cpf") //Método HTTP GET com o parâmetro "cpf"
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") Long cpf){
        Optional<ClienteCartao> lista = clienteCartaoService.listCartaoByCpf(cpf); //Obtém uma lista de objetos ClienteCartao correspondentes aos cartões do cliente com o CPF passado como parâmetro
        List<CartoesPorClienteResponse> resultList = lista.stream().map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList()); //Converte cada objeto ClienteCartao para um objeto CartoesPorClienteResponse e coloca os objetos convertidos em uma lista
        return ResponseEntity.ok(resultList); //Retorna a lista de objetos CartoesPorClienteResponse em uma resposta com o código de status 200 (OK)
    }
}
