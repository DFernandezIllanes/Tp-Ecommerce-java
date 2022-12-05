package ecommerce.Compradores.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "compra")
@Getter
@Setter
public class Compra {
	
	//------------------------------ ATRIBUTOS -----------------------------------------------------
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "comprador_id")
	private Long compradorId;
	
	@Column(name = "nombre_completo_comprador")
	private String nombreCompletoComprador;
	
	@Column(name = "vendedor_id")
	private Long tiendaId;
	

	@Column(name = "nombre_completo_vendedor")
	private String nombreCompletoVendedor;
	
	@OneToMany(mappedBy = "compra")
	private List<Item> items;	
	
//	@ElementCollection
//	@CollectionTable(name = "compra_metodoDePago", joinColumns = @JoinColumn(name = "compra_id"))
	@Column(name = "metodoDePago")
	private String metodoDePago;
	
	@Column(name = "fecha")
	private LocalDateTime fecha;
	
	
	@Column(name = "tiempo_de_entrega_aproximado")
	private String tiempoDeEntregaAproximado;
	
	//------------------------------ CONSTRUCTORES -----------------------------------------------------
	
	public Compra() {
		super();
		this.items = new ArrayList<>();
		//this.metodosDePago = new LinkedHashSet<>();
//		this.metodosDePago = new ArrayList<>();
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor, String metodoDePago) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = new ArrayList<>();
		this.metodoDePago = metodoDePago;
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long tiendaId, String metodoDePago) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.items = new ArrayList<>();
		this.metodoDePago = metodoDePago;
		this.fecha = LocalDateTime.now();		
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long vendedorId,
			String nombreCompletoVendedor, List<Item> items, String metodoDePago) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodoDePago = metodoDePago;
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long id, Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor, List<Item> items, String metodoDePago) {
		super();
		this.id = id;
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodoDePago = metodoDePago;
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor, List<Item> items, String metodoDePago,
			String tiempoDeEntregaAproximado) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodoDePago = metodoDePago;
		this.fecha = LocalDateTime.now();
		this.tiempoDeEntregaAproximado = tiempoDeEntregaAproximado;
	}

	public Compra(Long id, Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor, List<Item> items, String metodoDePago, LocalDateTime fecha,
			String tiempoDeEntregaAproximado) {
		super();
		this.id = id;
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodoDePago = metodoDePago;
		this.fecha = fecha;
		this.tiempoDeEntregaAproximado = tiempoDeEntregaAproximado;
	}	
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long tiendaId,String metodoDePag, List<Item> items ) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.items = items;
		this.fecha = LocalDateTime.now();
		this.metodoDePago = metodoDePago;
	}
	
	//------------------------------ MÉTODOS -----------------------------------------------------
	
	public void agregarListaDeItems(List<Item> items) {
		
		Iterator<Item> iteradorItems = items.iterator();
		
		while(iteradorItems.hasNext()) {
			Item item = iteradorItems.next();
			item.setCompra(this);
		}
		
		this.items = items;
	}

}
