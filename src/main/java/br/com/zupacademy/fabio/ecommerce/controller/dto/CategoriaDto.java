package br.com.zupacademy.fabio.ecommerce.controller.dto;

import br.com.zupacademy.fabio.ecommerce.entity.Categoria;

public class CategoriaDto {

    private Long id;
    private String nome;
    private Categoria categoriaMae;

    public CategoriaDto(Categoria categoria) {
        this.nome = categoria.getNome();
        this.id = categoria.getId();
        this.categoriaMae = categoria.getCategoriaMae();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoriaMae() {
        return categoriaMae;
    }
}
