package br.com.zupacademy.fabio.ecommerce.controller.form;

import br.com.zupacademy.fabio.ecommerce.entity.Categoria;
import br.com.zupacademy.fabio.ecommerce.repository.CategoriaRepository;
import br.com.zupacademy.fabio.ecommerce.validator.UniqueValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

public class CategoriaForm {

    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    @Positive
    private Long idCategoriaMae;

    public Categoria converterCategoria(CategoriaRepository categoriaRepository) {
        Categoria categoria = new Categoria(this.nome);
        if (idCategoriaMae != null) {
            Optional<Categoria> categoriaOptional = categoriaRepository.findById(this.idCategoriaMae);
            if (categoriaOptional.isEmpty()) {
                throw new IllegalArgumentException("O id da categoria mãe precisa ser válido");
            }
            categoria.setCategoriaMae(categoriaOptional.get());
        }
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdCategoriaMae() {
        return idCategoriaMae;
    }
}
