package br.com.zupacademy.fabio.ecommerce.controller;

import br.com.zupacademy.fabio.ecommerce.config.security.TokenService;
import br.com.zupacademy.fabio.ecommerce.controller.dto.ProdutoDto;
import br.com.zupacademy.fabio.ecommerce.controller.form.ProdutoForm;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.repository.CategoriaRepository;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import br.com.zupacademy.fabio.ecommerce.repository.UsuarioRepository;
import br.com.zupacademy.fabio.ecommerce.validator.ProibeCaracteristicaNomeIgualValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
                             UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ProdutoDto cria(@RequestBody @Valid ProdutoForm produtoForm, @RequestHeader HttpHeaders headers) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        Produto produto = produtoForm.converterProduto(produtoRepository, categoriaRepository, usuarioRepository,
                tokenService, token);
        produto = produtoRepository.save(produto);
        return new ProdutoDto(produto);
    }
}
