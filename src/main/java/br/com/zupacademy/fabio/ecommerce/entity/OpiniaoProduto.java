package br.com.zupacademy.fabio.ecommerce.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @Deprecated
    protected OpiniaoProduto() {
    }

    public OpiniaoProduto(@Positive @Min(value = 1) @Max(value = 5) int nota, @NotBlank String titulo,
                          @NotBlank @Length(max = 500) String descricao, @NotNull Produto produto) {
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
}
