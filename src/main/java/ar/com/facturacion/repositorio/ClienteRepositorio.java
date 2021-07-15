package ar.com.facturacion.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.com.facturacion.dominio.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{
	Cliente findByNombreContaining(String nombre);
	Page<Cliente> findAllByEstado(Integer estado, Pageable pageable);

}
