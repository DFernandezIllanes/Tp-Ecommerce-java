package ecommerce.Gestores.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTODatosVenta {
	
	//---------------------------- ATRIBUTOS ---------------------------------------------
	
	private String nombreCompletoVendedor;
	
	private List<String> metodosDePago;
	
	private String tiempoEstimadoDeEntrega;
	
	//---------------------------- CONSTRUCTORES ---------------------------------------------

	public DTODatosVenta() {
		super();
		this.metodosDePago = new ArrayList<>();
	}
	
	public DTODatosVenta(String tiempoEstimadoDeEntrega) {
		super();
		this.tiempoEstimadoDeEntrega = tiempoEstimadoDeEntrega;
		this.metodosDePago = new ArrayList<>();
	}
	
	public DTODatosVenta(String nombreCompletoVendedor, List<String> metodosDePago) {
		super();
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.metodosDePago = metodosDePago;
	}
	
	public DTODatosVenta(String nombreCompletoVendedor, List<String> metodosDePago, String tiempoEstimadoDeEntrega) {
		super();
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.metodosDePago = metodosDePago;
		this.tiempoEstimadoDeEntrega = tiempoEstimadoDeEntrega;
	}

}
