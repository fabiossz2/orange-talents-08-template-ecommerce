package br.com.zupacademy.fabio.ecommerce.controller.dto;

import br.com.zupacademy.fabio.ecommerce.entity.PerguntaProduto;

import javax.validation.constraints.NotBlank;

public class PerguntaDto {

    @NotBlank
    private String titulo;

    public PerguntaDto(PerguntaProduto pergunta) {
        this.titulo = pergunta.getTitulo();
    }

    public String getTitulo() {
        return titulo;
    }
}
