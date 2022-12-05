package ecommerce.Gestores.app;

import ecommerce.Gestores.models.*;
import ecommerce.Gestores.models.dtos.DTORtaPersonalizacion;
import ecommerce.Gestores.models.dtos.DTORtaVinculacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GestorVendedorController {

    @Autowired
    RepoGestor repoGestor;

    @Autowired
    RepoProductoBase repoProductoBase;

    @Autowired
    RepoPosiblePersonalizacion repoPosiblePersonalizacion;

    @GetMapping("/gestores/{gestorId}/vendedores/{vendedoresId}")
    public DTORtaVinculacion sePuedeVincular(@PathVariable("gestorId") Long gestorId, @PathVariable("vendedoresId") Long vendedoresId) {

        Optional<Gestor> gestorOptional = repoGestor.findById(gestorId);

        if (!gestorOptional.isPresent()) {
            return new DTORtaVinculacion("no existe", "no se encontro el gestor");
        } else {
            //TODO guardar el vendedor para el gestor
            return new DTORtaVinculacion("existe", "se encontro el gestor");
        }
    }

    @GetMapping("/gestores/{gestorId}/productoBase/{productoBaseId}/areaPersonalizacion/{areaPersonalizacionStr}")
    public DTORtaPersonalizacion posiblePersonalizacion(@PathVariable("gestorId") Long gestorId, @PathVariable("productoBaseId") Long productoBaseId, @PathVariable("areaPersonalizacionStr")String areaPersonalizacionStr) {
        Optional<Gestor> gestorOptional = repoGestor.findById(gestorId);
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = Optional.ofNullable(repoPosiblePersonalizacion.findByProductoBaseIdAndAreaDePersonalizacion(productoBaseId,areaPersonalizacionStr));

        if (!gestorOptional.isPresent()) {
            return new DTORtaPersonalizacion("noexiste");
        }

        if (!productoBaseOptional.isPresent() || !productoBaseOptional.get().isActivo()) {
            return new DTORtaPersonalizacion("noexiste");
        }

        ProductoBase productoBase = productoBaseOptional.get();

        if (posiblePersonalizacionOptional.isPresent()) {
            PosiblePersonalizacion personalizacion = posiblePersonalizacionOptional.get();
            if(personalizacion.isActivo()){
                return new DTORtaPersonalizacion("existe", personalizacion.getAreaDePersonalizacion(), personalizacion.getTipoDePersonalizacion(), productoBase.getPrecio());
            }
            return new DTORtaPersonalizacion("noexiste");
        }
        return new DTORtaPersonalizacion("existe", "", "", productoBase.getPrecio());
    }
}
