package br.com.zupacademy.fabio.ecommerce.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUploader {
    List<String> envia(List<MultipartFile> imagens);
}
