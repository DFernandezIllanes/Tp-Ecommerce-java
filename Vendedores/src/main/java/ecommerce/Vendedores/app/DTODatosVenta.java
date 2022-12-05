package ecommerce.Vendedores.app;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTODatosVenta {

	//---------------------------- ATRIBUTOS ---------------------------------------------
	
	private String nombreCompletoVendedor;
	
	private Integer tiempoEstimadoDeEntrega;
	
	//---------------------------- CONSTRUCTORES ---------------------------------------------

	public DTODatosVenta() {
		super();
	}
	
	public DTODatosVenta(Integer tiempoEstimadoDeEntrega) {
		super();
		this.tiempoEstimadoDeEntrega = tiempoEstimadoDeEntrega;
	}
	
	public DTODatosVenta(String nombreCompletoVendedor, Integer tiempoEstimadoDeEntrega) {
		super();
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.tiempoEstimadoDeEntrega = tiempoEstimadoDeEntrega;
	}

}
