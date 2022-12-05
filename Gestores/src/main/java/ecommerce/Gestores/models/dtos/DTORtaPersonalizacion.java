package ecommerce.Gestores.models.dtos;

import lombok.Getter;
import lombok.Setter;

public class DTORtaPersonalizacion {
    @Getter
    @Setter
    String status;

    @Getter @Setter
    String areaPersonalizacion;

    @Getter @Setter
    String tipoPersonalizacion;
    @Getter @Setter
    Double precioBase;

    public DTORtaPersonalizacion(String status, String areaPersonalizacion, String tipoPersonalizacion, Double precioBase){
        this.status = status;
        this.areaPersonalizacion = areaPersonalizacion;
        this.tipoPersonalizacion = tipoPersonalizacion;
        this.precioBase = precioBase;
    }

    public DTORtaPersonalizacion(String status){
        this.status = status;
    }
}
