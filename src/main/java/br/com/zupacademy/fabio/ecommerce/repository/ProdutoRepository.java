package br.com.zupacademy.fabio.ecommerce.repository;

import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

    @Query("from Produto p inner join p.dono d where p.id = :idProduto and d.id = :idDonoProd")
    Optional<Produto> findProdutoByUser(@Param("idProduto")Long idProduto, @Param("idDonoProd") Long idDonoProd);

    Produto findProdutoById(Long idProduto);
}
