package br.com.zupacademy.fabio.ecommerce.feign;

import javax.validation.constraints.NotNull;

public class RankingNovaCompraRequest {

    @NotNull
    private Long idCompra;

    @NotNull
    private Long idVendedor;

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }

    @Override
    public String toString() {
        return "RankingNovaCompraRequest{" +
                "idCompra=" + idCompra +
                ", idVendedor=" + idVendedor +
                '}';
    }
}
