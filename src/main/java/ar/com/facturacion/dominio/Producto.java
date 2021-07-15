package ar.com.facturacion.dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="productos")
public class Producto implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5115506962497445195L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@NotNull
	@Size(min = 5,max = 13)
	private String codigo;

	@NotNull
	@Size(min=4,max = 50)
	private String nombre;

	@NotNull
	@Size(min = 7,max = 255)
	private String descripcion;

	@NotNull
	private Integer estado;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", estado=" + estado
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Producto)) return false;
		Producto producto = (Producto) o;
		return getId().equals(producto.getId()) &&
				getCodigo().equals(producto.getCodigo()) &&
				getNombre().equals(producto.getNombre()) &&
				getDescripcion().equals(producto.getDescripcion()) &&
				getEstado().equals(producto.getEstado());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCodigo(), getNombre(), getDescripcion(), getEstado());
	}
}
