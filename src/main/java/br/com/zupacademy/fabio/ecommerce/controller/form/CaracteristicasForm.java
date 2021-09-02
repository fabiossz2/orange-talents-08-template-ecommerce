package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.CaracteristicasProduto;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CaracteristicasForm {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    public CaracteristicasForm(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public CaracteristicasProduto converterTo(@NotNull @Valid Produto produto) {
        return new CaracteristicasProduto(nome, descricao, produto);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaracteristicasForm that = (CaracteristicasForm) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
