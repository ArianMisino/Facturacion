package ar.com.facturacion.repositorio;

import ar.com.facturacion.dominio.Item;
import ar.com.facturacion.dominio.Encabezado;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepositorio extends JpaRepository<Item, Long>{
    //Item findByFacturaEncabezadoId();
    //Page<Item> findByEncabezadoId(Long facturas_encabezado_id, Pageable pageable);
    //List<Item> findByEncabezado(Encabezado encabezado, String nombre);
    Page<Item> findByEncabezado(Encabezado facturas_encabezado_id, Pageable pageable);
}
