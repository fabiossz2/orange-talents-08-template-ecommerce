package br.com.zupacademy.fabio.ecommerce.entity;

import br.com.zupacademy.fabio.ecommerce.entity.enumeration.StatusTransacao;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDateTime instante;
    @NotBlank
    private String idTransacao;
    @NotNull
    private StatusTransacao status;
    @ManyToOne
    private Compra compra;

    @Deprecated
    public Transacao() {
    }

    public Transacao(StatusTransacao status, @NotBlank String idTransacao, @NotNull @Valid Compra compra) {
        this.status = status;
        this.idTransacao = idTransacao;
        this.instante = LocalDateTime.now();
        this.compra = compra;

    }

    public boolean concluidaComSucesso(){
        return this.status.equals(StatusTransacao.sucesso);
    }

    @Override
    public String toString() {
        return " Transacao{" +
                "idTransacao='" + idTransacao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return idTransacao.equals(transacao.idTransacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransacao);
    }
}
