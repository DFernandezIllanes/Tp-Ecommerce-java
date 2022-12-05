package ecommerce.Gestores.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOProductoBase {
    String nombre;
    String descripcion;
    Double precio;
    Integer tiempoDeFabricacionEnDias;
    Boolean activo;
}
