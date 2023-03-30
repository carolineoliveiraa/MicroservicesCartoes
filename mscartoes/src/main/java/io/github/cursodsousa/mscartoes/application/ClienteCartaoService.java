package io.github.cursodsousa.mscartoes.application;

import io.github.cursodsousa.mscartoes.domain.ClienteCartao;
import io.github.cursodsousa.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
Classe utilizada para fornecer servicos ao objeto ClienteCartao
implementando a logica de negocios necessaria.
 */
@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    //Injeçao de dependencia
    private final ClienteCartaoRepository repository;


    public Optional<ClienteCartao> listCartaoByCpf(Long cpf){
        return repository.findById(cpf);
    }
}
