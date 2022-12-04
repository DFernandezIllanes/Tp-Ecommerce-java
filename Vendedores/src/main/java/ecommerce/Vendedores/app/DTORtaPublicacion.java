package ecommerce.Vendedores.app;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTORtaPublicacion {
	
	//--------------------------- ATRIBUTOS -----------------------------------
	
	String status;
	
	String nombre;
	
	Double precio;
	
	//-------------------------- CONSTRUCTURORES ----------------------------------
	
	public DTORtaPublicacion() {
		super();
	}
	
	public DTORtaPublicacion(String status) {
		super();
		this.status = status;
	}

	public DTORtaPublicacion(String status, String nombre, Double precio) {
		super();
		this.status = status;
		this.nombre = nombre;
		this.precio = precio;
	}
	
	
	
	
	
	

}
