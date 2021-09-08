package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.Compra;
import br.com.zupacademy.fabio.ecommerce.entity.RetornoGatewayPagamento;
import br.com.zupacademy.fabio.ecommerce.entity.Transacao;
import br.com.zupacademy.fabio.ecommerce.entity.enumeration.StatusRetornoPagSeguro;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RetornoPagSeguroForm implements RetornoGatewayPagamento {
    @NotBlank
    private String idTransacao;
    @NotNull
    private StatusRetornoPagSeguro status;

    public RetornoPagSeguroForm(@NotBlank String idTransacao, @NotNull StatusRetornoPagSeguro status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    @Override
    public Transacao toTransacao(Compra compra) {
        return new Transacao(status.normaliza(), idTransacao, compra);
    }

    @Override
    public String toString() {
        return "RetornoPagSeguroForm{" +
                "idTransacao='" + idTransacao + '\'' +
                ", status=" + status +
                '}';
    }
}
