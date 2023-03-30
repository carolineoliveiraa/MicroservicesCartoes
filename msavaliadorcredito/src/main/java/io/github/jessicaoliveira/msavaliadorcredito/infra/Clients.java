package io.github.jessicaoliveira.msavaliadorcredito.infra;

import io.github.jessicaoliveira.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.jessicaoliveira.msavaliadorcredito.domain.model.DadosCliente;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "msclientes", path = "/clientes")
public interface Clients {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
