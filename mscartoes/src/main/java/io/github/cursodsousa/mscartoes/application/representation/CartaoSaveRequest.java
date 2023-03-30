package io.github.cursodsousa.mscartoes.application.representation;

import io.github.cursodsousa.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

//Request = solicitar
/*
essa classe representa um pedido de criacao de um cartao de credito
que pode ser convertido em um objeto Cartao, utilizando toModel.
 */
@Data // lombok
public class CartaoSaveRequest {

    private String nome;
    private String bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limite);
    }
}
