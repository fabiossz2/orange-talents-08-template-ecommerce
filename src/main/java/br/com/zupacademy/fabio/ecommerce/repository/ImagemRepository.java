package br.com.zupacademy.fabio.ecommerce.repository;

import br.com.zupacademy.fabio.ecommerce.entity.ImagemProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends CrudRepository<ImagemProduto, Long> {
}
