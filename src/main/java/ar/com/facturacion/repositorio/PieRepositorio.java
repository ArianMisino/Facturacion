package ar.com.facturacion.repositorio;

import ar.com.facturacion.dominio.Encabezado;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.com.facturacion.dominio.Pie;
import org.springframework.stereotype.Repository;
import java.awt.print.Pageable;

@Repository
public interface PieRepositorio extends JpaRepository<Pie, Long>{
    //Page<Pie> findAllByEncabezado(Encabezado encabezado, Pageable pageable);
}
