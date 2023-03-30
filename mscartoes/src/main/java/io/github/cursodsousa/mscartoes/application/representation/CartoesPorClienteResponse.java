package io.github.cursodsousa.mscartoes.application.representation;

import io.github.cursodsousa.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// A anotação indica que a classe é uma classe modelo para a obtenção de informações de cartões por cliente.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    // Método estático que converte um objeto ClienteCartao em um objeto CartoesPorClienteResponse.
    public static  CartoesPorClienteResponse fromModel(ClienteCartao model){
        // Retorna um novo objeto CartoesPorClienteResponse com as informações correspondentes do objeto ClienteCartao recebido como parâmetro.
        return new CartoesPorClienteResponse(
                model.getCartao().getNome(),// Define o nome do cartão a partir do objeto ClienteCartao recebido.
                model.getCartao().getBandeira().toString(),// Define a bandeira do cartão a partir do objeto ClienteCartao recebido e converte para string.
                model.getLimite() // Define o limite liberado do cartão a partir do objeto ClienteCartao recebido.
        );
    }
}
