package br.com.zupacademy.fabio.ecommerce.entity;

import br.com.zupacademy.fabio.ecommerce.entity.enumeration.GatewayPagamento;
import br.com.zupacademy.fabio.ecommerce.entity.enumeration.StatusCompra;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    @Valid
    private Produto produtoEscolhido;
    @Positive
    private int quantidade;
    @ManyToOne
    @NotNull
    @Valid
    private Usuario comprador;
    @Enumerated(EnumType.STRING)
    @NotNull
    private GatewayPagamento gateway;

    @Positive
    @NotNull
    private BigDecimal valorCompra;

    @Enumerated(EnumType.STRING)
    private StatusCompra statusCompra = StatusCompra.INICIAL;

    @Deprecated
    protected Compra() {
    }

    public Compra(@NotNull @Valid Produto produtoEscolhido, @Positive int quantidade, @NotNull @Valid Usuario comprador,
                  @NotNull GatewayPagamento gateway) {
        this.produtoEscolhido = produtoEscolhido;
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.gateway = gateway;
        this.valorCompra = produtoEscolhido.getValor().multiply(new BigDecimal(quantidade));
    }

    public Long getId() {
        return id;
    }

    public Produto getProdutoEscolhido() {
        return produtoEscolhido;
    }

    public Usuario getComprador() {
        return comprador;
    }
}
