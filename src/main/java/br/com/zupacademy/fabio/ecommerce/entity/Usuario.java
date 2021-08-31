package br.com.zupacademy.fabio.ecommerce.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String login;

    @Column(nullable = false)
    @NotBlank
    @Length(min = 6)
    private String senha;

    @NotNull
    @PastOrPresent
    private LocalDateTime dataHoraCadastro;

    public Usuario(@Email @NotBlank String login, @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = enconderPassword(senha);
        this.dataHoraCadastro = LocalDateTime.now();
    }

    private String enconderPassword(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }
}
