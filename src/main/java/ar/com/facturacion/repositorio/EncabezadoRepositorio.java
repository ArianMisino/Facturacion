package ar.com.facturacion.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.facturacion.dominio.Encabezado;

public interface EncabezadoRepositorio extends JpaRepository<Encabezado, Long>{
    Page<Encabezado> findAllByAnulado(Integer anulado, Pageable pageable);
}
