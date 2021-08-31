package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioForm {

    @Email
    @NotBlank
    private String login;

    @NotBlank
    @Length(min = 6)
    private String senha;

    public UsuarioForm(@Email @NotBlank String login, @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;

    }

    public Usuario converterToUsuario() {
        return new Usuario(login, senha);
    }
}
