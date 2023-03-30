package io.github.cursodsousa.mscartoes.infra.repository;

import io.github.cursodsousa.mscartoes.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    //metodo personalizado sobre a renda ser menor ou igual
    List<Cartao> findByRendaLessThanEqual(BigDecimal rendaBigDecimal);
}
