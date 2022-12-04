package ecommerce.Compradores.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carritoDeCompra")
@Getter
@Setter
public class CarritoDeCompra {
	
	//------------------------------ ATRIBUTOS -------------------------------------
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(mappedBy = "carrito")
	private Comprador comprador;
	
	@OneToMany(mappedBy = "carrito")
	private List<Item> items;
	
	//------------------------------ CONSTRUCTORES -------------------------------------
	
	public CarritoDeCompra() {
		super();
		this.items = new ArrayList<>();
	}
	
	public CarritoDeCompra(Comprador comprador) {
		super();
		this.comprador = comprador;
		this.items = new ArrayList<>();
	}

	public CarritoDeCompra(Long id, Comprador comprador) {
		super();
		this.id = id;
		this.comprador = comprador;
		this.items = new ArrayList<>();
	}

	public CarritoDeCompra(Long id, Comprador comprador, List<Item> items) {
		super();
		this.id = id;
		this.comprador = comprador;
		this.items = items;
	}
	
	//------------------------------ MÉTODOS -------------------------------------
	
	public void agregarItem(Item item) {
		item.setCarrito(this);
		this.items.add(item);
	}
	
	public void quitarItem(Item item) {
		this.items.remove(item);
	}
}
