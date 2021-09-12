package ar.com.facturacion.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Factura {
	@Id
	private Long id;
	@OneToOne
	private Encabezado encabezado;
	@OneToMany
	private List<Item> items;
	@OneToOne
	private Pie pie;
}
