package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.PerguntaProduto;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import br.com.zupacademy.fabio.ecommerce.repository.UsuarioRepository;
import br.com.zupacademy.fabio.ecommerce.validator.ExistsId;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class PerguntaProdutoForm {

    @NotBlank
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public PerguntaProduto realizaPergunta(Usuario user, UsuarioRepository usuarioRepository,
                                           ProdutoRepository produtoRepository,
                                           @ExistsId(domainClass = Produto.class, fieldName = "id") Long idProduto) {
        final Produto produto = produtoRepository.findProdutoById(idProduto);
        final Optional<Usuario> usuario = usuarioRepository.findByLogin(user.getUsername());
        if(!usuario.isPresent()) throw new UsernameNotFoundException("usuário inválido");
        return new PerguntaProduto(titulo, usuario.get(), produto);
    }
}
