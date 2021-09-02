package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.config.security.TokenService;
import br.com.zupacademy.fabio.ecommerce.entity.Categoria;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import br.com.zupacademy.fabio.ecommerce.repository.CategoriaRepository;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import br.com.zupacademy.fabio.ecommerce.repository.UsuarioRepository;
import br.com.zupacademy.fabio.ecommerce.validator.ExistsId;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

public class ProdutoForm {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @Positive
    private int quantidade;

    @Size(min = 3)
    @Valid
    private List<CaracteristicasForm> caracteristicas = new ArrayList<>();

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoria;


    public ProdutoForm(@NotBlank String nome, @NotNull @Positive BigDecimal valor, @Positive int quantidade,
                       @NotBlank @Length(max = 1000) String descricao,
                       @Size(min = 3) @Valid List<CaracteristicasForm> caracteristicas, @NotNull Long idCategoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas.addAll(caracteristicas);
        this.descricao = descricao;
        this.idCategoria = idCategoria;
    }

    public List<CaracteristicasForm> getCaracteristicas() {
        return Collections.unmodifiableList(caracteristicas);
    }

    public Produto converterProduto(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
                                    UsuarioRepository usuarioRepository, TokenService tokenService,
                                    @NotBlank String token) {
        String tokenBearer = token.substring(7, token.length());
        Long idUsuario = tokenService.getIdUsuario(tokenBearer);

        if(idUsuario == null){
            throw new IllegalStateException("Token do usuário inválido");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        Assert.state(usuarioOptional.isPresent(), "Usuário Logado Inválido");

        Optional<Categoria> categoriaOptional = categoriaRepository.findById(idCategoria);

        Assert.state(categoriaOptional.isPresent(), "Categoria Inválida");

        return new Produto(nome, valor, quantidade, descricao, categoriaOptional.get(), this.caracteristicas,
                usuarioOptional.get());
    }


    public boolean hasCaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>();
        for (CaracteristicasForm caracteristicas : caracteristicas) {
            if (!nomesIguais.add(caracteristicas.getNome())) {
                return true;
            }
        }
        return false;
    }


    public Set<String> buscaCaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>();
        HashSet<String> result = new HashSet<>();
        for (CaracteristicasForm caracteristicas : caracteristicas) {
            String nome = caracteristicas.getNome();
            if (!nomesIguais.add(nome)) {
                result.add(nome);
            }
        }
        return result;
    }
}
