package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.OpiniaoProduto;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

public class OpiniaoProdutoForm {

    @Positive
    @Min(value = 1)
    @Max(value = 5)
    private int nota;

    @NotBlank
    private String titulo;

    @NotBlank
    @Length(max = 500)
    private String descricao;


    public OpiniaoProdutoForm(@Positive @Min(value = 1) @Max(value = 5) int nota, @NotBlank String titulo,
                              @NotBlank @Length(max = 500) String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public OpiniaoProduto adicionaOpiniaoProduto(ProdutoRepository produtoRepository, Usuario user, Long idProduto) {
        Optional<Produto> produtoOptional = produtoRepository.findProdutoByUser(idProduto, user.getId());
        if (!produtoOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O produto não pertence ao usuário");
        return new OpiniaoProduto(nota,titulo,descricao, produtoOptional.get());
    }
}
