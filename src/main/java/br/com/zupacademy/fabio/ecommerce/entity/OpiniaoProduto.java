package br.com.zupacademy.fabio.ecommerce.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
public class OpiniaoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @Min(value = 1)
    @Max(value = 5)
    @Column(nullable = false)
    private int nota;

    @NotBlank
    @Column(nullable = false)
    private String titulo;


    @NotBlank
    @Length(max = 500)
    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Usuario consumidor;

    @Deprecated
    protected OpiniaoProduto() {
    }

    public OpiniaoProduto(@Positive @Min(value = 1) @Max(value = 5) int nota, @NotBlank String titulo,
                          @NotBlank @Length(max = 500) String descricao, @Valid @NotNull Produto produto,
                          @Valid @NotNull Usuario consumidor) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public int getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpiniaoProduto that = (OpiniaoProduto) o;
        return titulo.equals(that.titulo) && descricao.equals(that.descricao) && produto.equals(that.produto) && consumidor.equals(that.consumidor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, descricao, produto);
    }
}
