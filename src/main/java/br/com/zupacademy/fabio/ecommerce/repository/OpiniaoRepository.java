package br.com.zupacademy.fabio.ecommerce.repository;

import br.com.zupacademy.fabio.ecommerce.entity.OpiniaoProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpiniaoRepository extends CrudRepository<OpiniaoProduto, Long> {
}
