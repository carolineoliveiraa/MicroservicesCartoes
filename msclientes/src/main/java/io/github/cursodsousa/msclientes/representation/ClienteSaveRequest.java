package io.github.cursodsousa.msclientes.representation;

import io.github.cursodsousa.msclientes.Domain.Cliente;
import lombok.Data;

 /*
    Aqui fica o DTO
    a classe é utilizada para receber os dados da requisição HTTP e
    depois converter esses dados para o objeto do tipo Cliente,
    que é a entidade de domínio da aplicação.
    Essa conversão é feita pelo método toModel(),
    que encapsula a lógica de transformação dos dados.
     */
@Data
public class ClienteSaveRequest {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome, idade);
    }
}
