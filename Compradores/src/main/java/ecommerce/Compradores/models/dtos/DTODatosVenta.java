package ecommerce.Compradores.models.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTODatosVenta {
	
	//---------------------------- ATRIBUTOS ---------------------------------------------
	
	private String nombreCompletoVendedor;
	
	private String tiempoEstimadoDeEntrega;
	
	//---------------------------- CONSTRUCTORES ---------------------------------------------

	public DTODatosVenta() {
		super();
	}
	
//	public DTODatosVenta(String tiempoEstimadoDeEntrega) {
//		super();
//		this.tiempoEstimadoDeEntrega = tiempoEstimadoDeEntrega;
//	}
//
	
	public DTODatosVenta(String nombreCompletoVendedor, String tiempoEstimadoDeEntrega) {
		super();
		this.nombreCompletoVendedor = nombreCompletoVendedor;
		this.tiempoEstimadoDeEntrega = tiempoEstimadoDeEntrega;
	}
}
