package io.github.cursodsousa.msclientes.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Classe entidade, é a que mapeia as colunas de uma tabela em um objeto
@Entity
@Data
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String cpf;
    @Column
    private String nome;
    @Column
    private String idade;

    public Cliente(String cpf, String nome, Integer idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = String.valueOf(idade);
    }
}
