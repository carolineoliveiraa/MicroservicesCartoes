package io.github.cursodsousa.msclientes.Application;

import io.github.cursodsousa.msclientes.Domain.Cliente;
import io.github.cursodsousa.msclientes.representation.ClienteSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de clientes");
        return "OK! Tá de pé!";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        // cria um endpoint POST "/clientes" que recebe um objeto ClienteSaveRequest no corpo da requisição
        // e retorna uma ResponseEntity

        // converte o objeto ClienteSaveRequest em um objeto Cliente
        Cliente cliente = request.toModel();

        // salva o objeto Cliente no banco de dados usando a camada de serviço
        service.save(cliente);

        // cria uma URI para o novo recurso criado, adicionando o CPF do cliente como parâmetro de consulta
        URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("cpf={cpf}").buildAndExpand(cliente.getCpf()).toUri();

        // retorna uma resposta HTTP 201 Created, com a URI do novo recurso criado no cabeçalho da resposta
        return ResponseEntity.created(headerLocation).build();
    }


    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam String cpf){
        // cria um endpoint GET "/clientes" que recebe um parâmetro "cpf" na URL da requisição
        // e retorna uma ResponseEntity

        // busca um cliente pelo CPF usando a camada de serviço
        Optional<Cliente> cliente = service.getByCPF(cpf);

        // verifica se o cliente foi encontrado
        if (cliente.isEmpty()){
            // se o cliente não foi encontrado, retorna uma resposta HTTP 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // se o cliente foi encontrado, retorna uma resposta HTTP 200 OK, com o objeto Cliente no corpo da resposta
        return ResponseEntity.ok(cliente);
    }
}
