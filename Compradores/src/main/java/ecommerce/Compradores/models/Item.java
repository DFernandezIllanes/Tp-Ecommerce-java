package ecommerce.Compradores.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {
	
	//--------------------------- ATRIBUTOS ---------------------------------
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "publicacionId")
	private Long publicacionId;
	
//	@Column(name = "tiendaId")
//	private Long tiendaId;
	
	@Column(name = "precio")
	private Double precio;
	
	@ManyToOne
	@JoinColumn(name = "carritoId", referencedColumnName = "id")
	private CarritoDeCompra carrito;	
	
	//--------------------------- CONSTRUCTORES ---------------------------------
	
	public Item() {
		super();
	}
	
	public Item(Integer cantidad, Long publicacionId, Double precio) {
		super();
		this.cantidad = cantidad;
		this.publicacionId = publicacionId;
		this.precio = precio;
	}
	
//	public Item(Integer cantidad, Long publicacionId, Long tiendaId, Double precio) {
//		super();
//		this.cantidad = cantidad;
//		this.publicacionId = publicacionId;
//		this.tiendaId = tiendaId;
//	}
	
	
	public Item(Integer cantidad, Long publicacionId, Double precio, CarritoDeCompra carrito) {
		super();
		this.cantidad = cantidad;
		this.publicacionId = publicacionId;
		this.precio = precio;
		this.carrito = carrito;
	}
	
//	public Item(Integer cantidad, Long publicacionId, Long tiendaId, Double precio, CarritoDeCompra carrito) {
//		super();
//		this.cantidad = cantidad;
//		this.publicacionId = publicacionId;
//		this.tiendaId = tiendaId;
//		this.precio = precio;
//		this.carrito = carrito;
//	}
	
	public Item(Long id, Integer cantidad, Long publicacionId, Double precio, CarritoDeCompra carrito) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.publicacionId = publicacionId;
		this.precio = precio;
		this.carrito = carrito;
	}
	
//	public Item(Long id, Integer cantidad, Long publicacionId, Long tiendaId, Double precio, CarritoDeCompra carrito) {
//		super();
//		this.id = id;
//		this.cantidad = cantidad;
//		this.publicacionId = publicacionId;
//		this.tiendaId = tiendaId;
//		this.precio = precio;
//		this.carrito = carrito;
//	}
	
	
}
