package io.github.cursodsousa.mscartoes.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class ClienteCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cpf;
    @ManyToOne // muitos pra um
    @JoinColumn(name = "id_cartao")
    private Cartao cartao;
    private BigDecimal limite;
    /*
    @ManyToOne é usada para indicar que a entidade ClienteCartao
    tem uma associação com muitas instâncias da entidade Cartao

    @JoinColumn é usada para especificar a coluna de chave estrangeira
     na tabela do banco de dados que armazena a relação entre as entidades ClienteCartao e Cartao
     */
}
