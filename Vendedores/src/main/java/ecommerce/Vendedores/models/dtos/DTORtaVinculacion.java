package ecommerce.Vendedores.models.dtos;

import lombok.Getter;
import lombok.Setter;

public class DTORtaVinculacion {
    @Getter @Setter
    private String status;

    @Getter @Setter
    private String detalle;


//    public DTORtaVinculacion(){}
//
    public DTORtaVinculacion(String status, String detalle){
        this.status = status;
        this.detalle = detalle;
    }

}
