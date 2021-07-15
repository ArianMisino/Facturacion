package ar.com.facturacion.repositorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.facturacion.dominio.Producto;
@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long>{
	List<Producto> findByCodigoContainingOrNombreContaining(String codigo, String nombre);
	Page<Producto> findAllByEstado(Integer estado, Pageable pageable);
	ArrayList<Producto> findByEstado(Integer estado);
}
