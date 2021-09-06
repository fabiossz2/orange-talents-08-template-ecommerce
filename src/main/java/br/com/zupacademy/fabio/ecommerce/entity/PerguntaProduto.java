package br.com.zupacademy.fabio.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PerguntaProduto implements Comparable<PerguntaProduto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    private LocalDateTime dataHoraPergunta;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Produto produto;

    @Deprecated
    public PerguntaProduto() {
    }

    public PerguntaProduto(@NotBlank String titulo, @NotNull Usuario usuario, @NotNull Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
        this.dataHoraPergunta = LocalDateTime.now();
    }

    public String getTitulo() {
        return titulo;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerguntaProduto that = (PerguntaProduto) o;
        return titulo.equals(that.titulo) && usuario.equals(that.usuario) && produto.equals(that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, usuario, produto);
    }

    @Override
    public int compareTo(PerguntaProduto o) {
        return this.titulo.compareTo(o.getTitulo());
    }
}
