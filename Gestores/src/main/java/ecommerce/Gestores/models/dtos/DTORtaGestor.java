package ecommerce.Gestores.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DTORtaGestor {
    private Integer tiempoDeFabricacion;

    public DTORtaGestor(Integer tiempoDeFabricacion) {
        this.tiempoDeFabricacion = tiempoDeFabricacion;
    }
}
