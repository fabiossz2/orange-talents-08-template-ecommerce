package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.Compra;
import br.com.zupacademy.fabio.ecommerce.entity.RetornoGatewayPagamento;
import br.com.zupacademy.fabio.ecommerce.entity.Transacao;
import br.com.zupacademy.fabio.ecommerce.entity.enumeration.StatusTransacao;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class RetornoPaypalForm implements RetornoGatewayPagamento {
    @NotBlank
    private String idTransacao;
    @Min(0)
    @Max(1)
    private int status;

    public RetornoPaypalForm(@NotBlank String idTransacao, @Min(0) @Max(1) int status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    @Override
    public Transacao toTransacao(Compra compra) {
        final StatusTransacao statusTransacao = this.status == 0 ? StatusTransacao.erro : StatusTransacao.sucesso;
        return new Transacao(statusTransacao, idTransacao, compra);
    }

    @Override
    public String toString() {
        return "RetornoPagSeguroForm{" +
                "idTransacao='" + idTransacao + '\'' +
                ", status=" + status +
                '}';
    }

}
