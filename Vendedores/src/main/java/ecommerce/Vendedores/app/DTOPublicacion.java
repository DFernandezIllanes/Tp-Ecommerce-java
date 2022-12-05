package ecommerce.Vendedores.app;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DTOPublicacion {
    Long productoFinalId;
    Long personalizacionId;

    String nombre;

    public DTOPublicacion(){}

    public DTOPublicacion(Long productoFinalId, String nombre){

        this.productoFinalId = productoFinalId;
        this.nombre = nombre;
    }

    public DTOPublicacion(Long productoFinalId, Long personalizacionId){
        this.productoFinalId = productoFinalId;
        this.personalizacionId = personalizacionId;
    }
}
