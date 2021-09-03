package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.ImagemProduto;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.entity.Uploader;
import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImagemProdutoRequest {

    @NotNull
    @Size(min = 1)
    private List<MultipartFile> imagens = new ArrayList<>();

    public List<ImagemProduto> converterToImagens(Long idProduto, Usuario user, ProdutoRepository produtoRepository,
                                                  Uploader uploader) {
        Optional<Produto> produtoOptional = produtoRepository.findProdutoByUser(idProduto, user.getId());
        if (!produtoOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O produto não pertence ao usuário");

        List<String> listImagens = uploader.envia(this.imagens);

        return listImagens.stream()
                .map(imagem -> new ImagemProduto(imagem, produtoOptional.get())).collect(Collectors.toList());
    }

    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }
}
