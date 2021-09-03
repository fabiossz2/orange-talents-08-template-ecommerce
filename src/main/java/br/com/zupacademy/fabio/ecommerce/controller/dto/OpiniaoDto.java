package br.com.zupacademy.fabio.ecommerce.controller.dto;

import br.com.zupacademy.fabio.ecommerce.entity.OpiniaoProduto;

public class OpiniaoDto {

    private Long id;
    private int nota;
    private String titulo;
    private String descricao;

    public OpiniaoDto(OpiniaoProduto opiniao) {
        this.id = opiniao.getId();
        this.nota = opiniao.getNota();
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
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
