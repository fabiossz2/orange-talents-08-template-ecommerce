package br.com.zupacademy.fabio.ecommerce.feign;

import javax.validation.constraints.NotNull;

public class NfRequest {

    @NotNull
    private Long idCompra;

    @NotNull
    private Long idComprador;

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdComprador() {
        return idComprador;
    }

    @Override
    public String toString() {
        return "NfRequest{" +
                "idCompra=" + idCompra +
                ", idComprador=" + idComprador +
                '}';
    }
}
