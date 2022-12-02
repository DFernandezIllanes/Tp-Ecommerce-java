package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.ProductoFinal;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
