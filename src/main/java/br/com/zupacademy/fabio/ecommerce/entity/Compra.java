package br.com.zupacademy.fabio.ecommerce.entity;

import br.com.zupacademy.fabio.ecommerce.entity.enumeration.GatewayPagamento;
import br.com.zupacademy.fabio.ecommerce.entity.enumeration.StatusCompra;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

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

    public GatewayPagamento getGateway() {
        return gateway;
    }

    public void setGateway(GatewayPagamento gateway) {
        this.gateway = gateway;
    }

    public void tentativaPagamento(@Valid RetornoGatewayPagamento request) {
        Transacao novaTransacao = request.toTransacao(this);

        Assert.isTrue(!this.transacoes.contains(novaTransacao), "Já existe uma transação igual a essa processada"
                + novaTransacao);

        Set<Transacao> transacoesComSucesso = getTransacoesComSucesso();

        Assert.isTrue(getTransacoesComSucesso().isEmpty(), "Essa compra já foi concluída");
        this.transacoes.add(novaTransacao);
    }

    private Set<Transacao> getTransacoesComSucesso() {
        Set<Transacao> transacoesComSucesso = this.transacoes.stream()
                 .filter(Transacao::concluidaComSucesso).collect(Collectors.toSet());

        Assert.isTrue(transacoesComSucesso.size() <= 1, "Possuí mais tem uma transação na compra "
                + this.id);
        return transacoesComSucesso;
    }

    public boolean processadaComSucesso() {
        return !getTransacoesComSucesso().isEmpty();
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", produtoEscolhido=" + produtoEscolhido +
                ", quantidade=" + quantidade +
                ", comprador=" + comprador +
                ", gateway=" + gateway +
                ", valorCompra=" + valorCompra +
                ", statusCompra=" + statusCompra +
                ", transacoes=" + transacoes +
                '}';
    }

}
