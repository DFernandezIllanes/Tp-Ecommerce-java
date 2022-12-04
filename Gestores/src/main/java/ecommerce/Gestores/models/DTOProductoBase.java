package ecommerce.Gestores.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOProductoBase {
    String nombre;
    String descripcion;
    Double precio;
    String tiempoDeFabricacionEnDias;
    Boolean activo;
}
