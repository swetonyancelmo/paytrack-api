package com.swetonyancelmo.paytrack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 80, message = "O nome deve conter entre 3 e 80 caracteres")
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    @Size(min = 3, max = 20, message = "A senha deve conter entre 3 e 20 caracteres")
    private String senha;

    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
