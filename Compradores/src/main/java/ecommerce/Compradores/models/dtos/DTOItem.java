package ecommerce.Compradores.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOItem {	
	
	//----------------------- ATRIBUTOS --------------------------------
	
	private Integer cantidad;	
	
	private Long publicacionId;
	
	private Long tiendaId;
	
	//----------------------- CONSTRUCTORES ----------------------------
	
	public DTOItem() {
		super();
	}

	public DTOItem(Integer cantidad, Long publicacionId, Long tiendaId) {
		super();
		this.cantidad = cantidad;
		this.publicacionId = publicacionId;
		this.tiendaId = tiendaId;
	}
}
