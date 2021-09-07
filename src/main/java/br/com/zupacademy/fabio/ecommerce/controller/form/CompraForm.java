package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.enumeration.GatewayPagamento;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.validator.ExistsId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraForm {
    @Positive
    private int quantidade;
    @NotNull
    @ExistsId(domainClass = Produto.class, fieldName = "id")
    private Long idProduto;
    @NotNull
    private GatewayPagamento gateway;

    public CompraForm(@Positive int quantidade, @NotNull Long idProduto, @NotNull GatewayPagamento gateway) {
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.gateway = gateway;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public GatewayPagamento getGateway() {
        return gateway;
    }
}
