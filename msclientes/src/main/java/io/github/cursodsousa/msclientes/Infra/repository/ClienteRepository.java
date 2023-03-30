package io.github.cursodsousa.msclientes.Infra.repository;

import io.github.cursodsousa.msclientes.Domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Metodo personalizado para ser buscado o CPF do Domain(Model, entity)
    Optional<Cliente> findByCpf(String cpf);
}
