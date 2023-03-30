package io.github.jessicaoliveira.msavaliadorcredito.application.exception;

public class DadosClienteNotFound extends Exception{
    public DadosClienteNotFound() {
        super("Dados do cliente não encontrados para o CPF informado!");
    }
}
