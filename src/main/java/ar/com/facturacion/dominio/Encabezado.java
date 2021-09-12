package ar.com.facturacion.dominio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name="facturas_encabezado")
public class Encabezado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3479505724865821556L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Date fecha;

	@NotNull
	private String numero;

	@NotNull
	private String letra;

	@OneToOne
	@NotNull
	private Cliente cliente;

	@NotNull
	private Integer anulado;

	/*no debería estar acá porque ya esta en factura*/
	@OneToMany(mappedBy = "encabezado")
	private List<Item> items;
	@OneToOne(mappedBy = "encabezado")
	private Pie pie;

}
