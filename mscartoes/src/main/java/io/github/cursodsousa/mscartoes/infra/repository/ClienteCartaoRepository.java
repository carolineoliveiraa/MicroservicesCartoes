package io.github.cursodsousa.mscartoes.infra.repository;

import io.github.cursodsousa.mscartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
    // Metodo personalizado
    Optional<ClienteCartao> findById(Long cpf);
}
