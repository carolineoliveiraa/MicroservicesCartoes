package io.github.cursodsousa.msclientes.Application;

import io.github.cursodsousa.msclientes.Domain.Cliente;
import io.github.cursodsousa.msclientes.Infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor //Cria um construtor em tempo de execução, especificamente da injecao a baixo
public class ClienteService {

    //Injecao de dependencia do repository para ter uma interacao com o banco de dados
    private final ClienteRepository repository;

    //metodo para salvar um cliente
    @Transactional
    public Cliente save(Cliente cliente){
        return  repository.save(cliente);
    }

    //Metodo opicional para retornar o CPF
    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}
