package ecommerce.Compradores.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comprador")
@Getter
@Setter
public class Comprador {
	
	//------------------------------------ ATRIBUTOS ----------------------------------------
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "nombre", columnDefinition = "VARCHAR(50)")
	private String nombre;
	
	@Column(name = "apellido", columnDefinition = "VARCHAR(50)")
	private String apellido;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "carrito_id", referencedColumnName = "id")
	private CarritoDeCompra carrito;
	
	//------------------------------------ CONSTRUCTORES ----------------------------------------
	
	public Comprador() {
		super();
		this.carrito = new CarritoDeCompra();
	}
	
	public Comprador(String nombre, String apellido) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.carrito = new CarritoDeCompra();
	}
	
	public Comprador(Long id, String nombre, String apellido) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.carrito = new CarritoDeCompra();
	}
	
	//----------------------------------- MÉTODOS ------------------------------------------------
	
	public void cargarCarrito(Item item) {
		this.carrito.agregarItem(item);
	}
	
	public void quitarItem(Item item) {
		this.carrito.quitarItem(item);
	}

}
