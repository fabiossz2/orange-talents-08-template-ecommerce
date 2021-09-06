package br.com.zupacademy.fabio.ecommerce.controller.dto;

import br.com.zupacademy.fabio.ecommerce.entity.CaracteristicasProduto;

public class DetalheProdutoCaracteristicaDto {

    private String descricao;
    private String nome;

    public DetalheProdutoCaracteristicaDto(CaracteristicasProduto caracteristica) {
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }
}
