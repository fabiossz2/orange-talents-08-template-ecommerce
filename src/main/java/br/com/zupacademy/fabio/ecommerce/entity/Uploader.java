package br.com.zupacademy.fabio.ecommerce.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class Uploader implements IUploader {

    public List<String> envia(List<MultipartFile> imagens) {
        return imagens.stream().map(imagem -> "http://bucket.io/"
                + imagem.getOriginalFilename()
                + UUID.randomUUID().toString()).collect(Collectors.toList());
    }
}
