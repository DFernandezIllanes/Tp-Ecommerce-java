package ecommerce.Compradores.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "itemCompra")
@Getter
@Setter
public class ItemCompra {
	
	//------------------------------- ATRIBUTOS --------------------------------------------------
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@Column(name = "nombrePublicacion")
	private String nombrePublicacion;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@ManyToOne
	@JoinColumn(name = "compra_id", referencedColumnName = "id")
	@JsonBackReference
	private Compra compra;
	
	//------------------------------- CONSTRUCTORES --------------------------------------------------
	
	public ItemCompra() {
		super();
	}
	
	public ItemCompra(String nombrePublicacion, Integer cantidad) {
		super();
		this.nombrePublicacion = nombrePublicacion;
		this.cantidad = cantidad;
	}
	
	public ItemCompra(Long id, String nombrePublicacion, Integer cantidad) {
		super();
		this.id = id;
		this.nombrePublicacion = nombrePublicacion;
		this.cantidad = cantidad;
	}

}
