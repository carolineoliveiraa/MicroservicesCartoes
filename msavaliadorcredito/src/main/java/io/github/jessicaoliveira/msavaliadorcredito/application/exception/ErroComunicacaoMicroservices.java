package io.github.jessicaoliveira.msavaliadorcredito.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroservices extends Exception {

    @Getter
    private Integer status;
    public ErroComunicacaoMicroservices(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
