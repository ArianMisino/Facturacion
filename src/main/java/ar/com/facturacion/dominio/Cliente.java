package ar.com.facturacion.dominio;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name="clientes")
public class Cliente implements Serializable {
	
	/**
	 * 3159888465448526457L
	 */
	private static final long serialVersionUID = -5378390958475659461L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 4,max = 13)
	private String codigo;

	@NotNull
	@Size(min = 3,max = 120)
	private String nombre;

	@NotNull
	@Size(min = 6,max = 120)
	private String direccion;

	@Nullable
	@Size(min = 8,max = 13)
	private String cuit;

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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	@Override
	public String toString() {
		return "Cliente [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", cuit=" + cuit + ", estado=" + estado + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Cliente)) return false;
		Cliente cliente = (Cliente) o;
		return getId().equals(cliente.getId()) &&
				getCodigo().equals(cliente.getCodigo()) &&
				getNombre().equals(cliente.getNombre()) &&
				getDireccion().equals(cliente.getDireccion()) &&
				getCuit().equals(cliente.getCuit()) &&
				getEstado().equals(cliente.getEstado());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCodigo(), getNombre(), getDireccion(), getCuit(), getEstado());
	}
}
