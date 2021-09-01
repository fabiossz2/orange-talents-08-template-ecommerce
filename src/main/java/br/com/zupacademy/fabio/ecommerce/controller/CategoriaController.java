package br.com.zupacademy.fabio.ecommerce.controller;

import br.com.zupacademy.fabio.ecommerce.controller.dto.CategoriaDto;
import br.com.zupacademy.fabio.ecommerce.controller.form.CategoriaForm;
import br.com.zupacademy.fabio.ecommerce.entity.Categoria;
import br.com.zupacademy.fabio.ecommerce.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    @Transactional
    public CategoriaDto cria(@RequestBody @Valid CategoriaForm categoriaForm) {
        Categoria categoria = categoriaForm.converterCategoria(categoriaRepository);
        this.categoriaRepository.save(categoria);
        return new CategoriaDto(categoria);
    }
}
