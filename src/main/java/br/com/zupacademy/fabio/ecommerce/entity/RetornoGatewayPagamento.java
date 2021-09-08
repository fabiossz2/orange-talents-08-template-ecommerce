package br.com.zupacademy.fabio.ecommerce.entity;

public interface RetornoGatewayPagamento {
    Transacao toTransacao(Compra compra);
}
