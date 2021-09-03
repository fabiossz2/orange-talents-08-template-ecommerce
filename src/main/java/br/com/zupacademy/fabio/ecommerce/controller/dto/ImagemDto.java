package br.com.zupacademy.fabio.ecommerce.controller.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class ImagemDto {

    @NotBlank
    @URL
    private String url;

    public ImagemDto(@NotBlank @URL String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
