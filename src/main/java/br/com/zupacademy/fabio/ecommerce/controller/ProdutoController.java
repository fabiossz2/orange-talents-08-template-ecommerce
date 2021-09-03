package br.com.zupacademy.fabio.ecommerce.controller;

import br.com.zupacademy.fabio.ecommerce.config.security.TokenService;
import br.com.zupacademy.fabio.ecommerce.controller.dto.ImagemDto;
import br.com.zupacademy.fabio.ecommerce.controller.dto.ImagensDto;
import br.com.zupacademy.fabio.ecommerce.controller.dto.ProdutoDto;
import br.com.zupacademy.fabio.ecommerce.controller.form.ImagemProdutoRequest;
import br.com.zupacademy.fabio.ecommerce.controller.form.ProdutoForm;
import br.com.zupacademy.fabio.ecommerce.entity.ImagemProduto;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.entity.Uploader;
import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import br.com.zupacademy.fabio.ecommerce.repository.CategoriaRepository;
import br.com.zupacademy.fabio.ecommerce.repository.ImagemRepository;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import br.com.zupacademy.fabio.ecommerce.repository.UsuarioRepository;
import br.com.zupacademy.fabio.ecommerce.validator.ProibeCaracteristicaNomeIgualValidator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImagemRepository imagemRepository;
    private final Uploader uploader;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
                             UsuarioRepository usuarioRepository, TokenService tokenService, ImagemRepository imagemRepository,
                             Uploader uploader) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.imagemRepository = imagemRepository;
        this.uploader = uploader;
    }

    @InitBinder(value = "produtoForm")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ProdutoDto cria(@RequestBody @Valid ProdutoForm produtoForm, @AuthenticationPrincipal Usuario user) {
        Produto produto = produtoForm.converterProduto(produtoRepository, categoriaRepository, usuarioRepository, user);
        produto = produtoRepository.save(produto);
        return new ProdutoDto(produto);
    }


    @Transactional
    @PostMapping("/{id}/imagens")
    public List<ImagemDto> adicionaImagem(@Valid ImagemProdutoRequest imagemRequest, @AuthenticationPrincipal Usuario user,
                                          @PathVariable(name = "id") Long idProduto) {
        List<ImagemProduto> imagens = imagemRequest.converterToImagens(idProduto, user, produtoRepository, uploader);
        this.imagemRepository.saveAll(imagens);
        return new ImagensDto(imagens).getImagens();
    }
}
