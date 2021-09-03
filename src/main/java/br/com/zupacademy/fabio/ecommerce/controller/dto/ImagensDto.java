package br.com.zupacademy.fabio.ecommerce.controller.dto;

import br.com.zupacademy.fabio.ecommerce.entity.ImagemProduto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ImagensDto {

    private List<ImagemDto> imagens;

    public ImagensDto(List<ImagemProduto> imagensProduto) {
        this.imagens = imagensProduto.stream().map(i -> new ImagemDto(i.getUrl())).collect(Collectors.toList());
    }

    public List<ImagemDto> getImagens() {
        return Collections.unmodifiableList(imagens);
    }
}
