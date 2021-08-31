package br.com.zupacademy.fabio.ecommerce.repository;

import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
