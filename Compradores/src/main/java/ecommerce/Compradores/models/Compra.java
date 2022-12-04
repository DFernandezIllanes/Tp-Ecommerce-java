package ecommerce.Compradores.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.JoinColumn;

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
	private List<ItemCompra> items;	
		
	@ElementCollection
	@CollectionTable(name = "compra_metodoDePago", joinColumns = @JoinColumn(name = "compra_id"))
	@Column(name = "metodoDePago")
	//private Set<String> metodosDePago;
	private List<String> metodosDePago;
	
	@Column(name = "fecha")
	private LocalDateTime fecha;
	
	@Column(name = "tiempo_de_entrega_aproximado")
	private String tiempoDeEntregaAproximado;
	
	//------------------------------ CONSTRUCTORES -----------------------------------------------------
	
	public Compra() {
		super();
		this.items = new ArrayList<>();
		//this.metodosDePago = new LinkedHashSet<>();
		this.metodosDePago = new ArrayList<>();
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = new ArrayList<>();
		this.metodosDePago = new ArrayList<>();
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long tiendaId) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.items = new ArrayList<>();
		this.metodosDePago = new ArrayList<>();
		this.fecha = LocalDateTime.now();		
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long vendedorId,
			String nombreCompletoVendedor, List<ItemCompra> items) {
		super();
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodosDePago = new ArrayList<>();
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long id, Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor, List<ItemCompra> items) {
		super();
		this.id = id;
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodosDePago = new ArrayList<>();
		this.fecha = LocalDateTime.now();
	}
	
	public Compra(Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor, List<ItemCompra> items, List<String> metodosDePago,
			String tiempoDeEntregaAproximado) {
		super();
		this.id = id;
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodosDePago = metodosDePago;
		this.fecha = LocalDateTime.now();
		this.tiempoDeEntregaAproximado = tiempoDeEntregaAproximado;
	}

	public Compra(Long id, Long compradorId, String nombreCompletoComprador, Long tiendaId,
			String nombreCompletoVendedor, List<ItemCompra> items, List<String> metodosDePago, LocalDateTime fecha,
			String tiempoDeEntregaAproximado) {
		super();
		this.id = id;
		this.compradorId = compradorId;
		this.nombreCompletoComprador = nombreCompletoComprador;
		this.tiendaId = tiendaId;
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.items = items;
		this.metodosDePago = metodosDePago;
		this.fecha = fecha;
		this.tiempoDeEntregaAproximado = tiempoDeEntregaAproximado;
	}	
	
	//------------------------------ MÉTODOS -----------------------------------------------------

}
