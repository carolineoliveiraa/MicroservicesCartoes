package io.github.cursodsousa.mscartoes.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

//Classe entidade, é a que mapeia as colunas de uma tabela em um objeto
@Entity
@Data
@NoArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao(String nome,
                  String bandeira,
                  BigDecimal renda,
                  BigDecimal limiteBasico) {
        this.nome = nome;
        this.bandeira = BandeiraCartao.valueOf(bandeira);
        this.renda = renda;
        this.limiteBasico = limiteBasico;
    }

    /*
    @Enumerated(EnumType.STRING) é usada para
    indicar que a propriedade bandeira deve ser mapeada para
    uma coluna do tipo VARCHAR,que representa a enumeração BandeiraCartao como uma string.
     */
}
