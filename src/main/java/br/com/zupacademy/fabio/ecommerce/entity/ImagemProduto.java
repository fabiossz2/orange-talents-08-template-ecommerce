package br.com.zupacademy.fabio.ecommerce.entity;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Produto produto;

    @NotBlank
    @URL
    private String url;

    @Deprecated
    protected ImagemProduto() {
    }

    public ImagemProduto(@NotBlank String url, @NotNull Produto produto) {
        this.url = url;
        this.produto = produto;
    }

    public String getUrl() {
        return url;
    }
}
