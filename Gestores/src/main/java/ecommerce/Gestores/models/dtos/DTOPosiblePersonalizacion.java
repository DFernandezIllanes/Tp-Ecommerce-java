package ecommerce.Gestores.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOPosiblePersonalizacion {
	
	//--------------------------- ATRIBUTOS ----------------------------------------

    private String areaDePersonalizacion;

    private String tipoDePersonalizacion;

    Boolean activo;
    
    //------------------------------ CONSTRUCTORES --------------------------------------------
    
    public DTOPosiblePersonalizacion() {
		super();
	}    

	public DTOPosiblePersonalizacion(String areaDePersonalizacion, String tipoDePersonalizacion, Boolean activo) {
		super();
		this.areaDePersonalizacion = areaDePersonalizacion;
		this.tipoDePersonalizacion = tipoDePersonalizacion;
		this.activo = activo;
	}
    
    
}
