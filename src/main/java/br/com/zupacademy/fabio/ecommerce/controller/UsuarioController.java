package br.com.zupacademy.fabio.ecommerce.controller;

import br.com.zupacademy.fabio.ecommerce.controller.form.UsuarioForm;
import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import br.com.zupacademy.fabio.ecommerce.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @Transactional
    public void cria(@RequestBody @Valid UsuarioForm usuarioForm) {
        Usuario usuario = usuarioForm.converterToUsuario();
        this.usuarioRepository.save(usuario);
    }
}
