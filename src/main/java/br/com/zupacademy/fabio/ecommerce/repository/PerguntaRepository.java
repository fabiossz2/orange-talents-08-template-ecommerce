package br.com.zupacademy.fabio.ecommerce.repository;

import br.com.zupacademy.fabio.ecommerce.entity.PerguntaProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntaRepository extends CrudRepository<PerguntaProduto, Long> {
}
