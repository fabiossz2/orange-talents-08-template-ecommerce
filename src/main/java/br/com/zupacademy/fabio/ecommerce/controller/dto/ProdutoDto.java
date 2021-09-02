package br.com.zupacademy.fabio.ecommerce.controller.dto;

import br.com.zupacademy.fabio.ecommerce.entity.CaracteristicasProduto;
import br.com.zupacademy.fabio.ecommerce.entity.Categoria;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class ProdutoDto {

    private Long id;
    private String nome;
    private BigDecimal valor;
    private int quantidade;
    private String descricao;
    private Categoria categoria;
    private Set<CaracteristicasProduto> caracteristicas;

    public ProdutoDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.quantidade = produto.getQuantidade();
        this.descricao = produto.getDescricao();
        this.categoria = produto.getCategoria();
        this.caracteristicas = produto.getCaracteristicas();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Set<CaracteristicasProduto> getCaracteristicas() {
        return Collections.unmodifiableSet(caracteristicas);
    }
}
