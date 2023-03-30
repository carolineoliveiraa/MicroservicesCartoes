package io.github.jessicaoliveira.msavaliadorcredito.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;

    public RetornoAvaliacaoCliente(List<CartaoAprovado> listaCartoesAprovados) {

    }
}
