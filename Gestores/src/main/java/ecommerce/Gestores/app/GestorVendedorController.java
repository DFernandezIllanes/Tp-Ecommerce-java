package ecommerce.Gestores.app;

import ecommerce.Gestores.models.Gestor;
import ecommerce.Gestores.models.PosiblePersonalizacion;
import ecommerce.Gestores.models.ProductoBase;
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

    @GetMapping("/gestores/{gestorId}/productoBase/{productoBaseId}")
    public DTORtaPersonalizacion posiblePersonalizacion(@PathVariable("gestorId") Long gestorId, @PathVariable("productoBaseId") Long productoBaseId) {
        Optional<Gestor> gestorOptional = repoGestor.findById(gestorId);
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = Optional.ofNullable(repoPosiblePersonalizacion.findByProductoBaseId(productoBaseId));

        if (!gestorOptional.isPresent()) {
            return new DTORtaPersonalizacion("noexiste");
        }

        if (!productoBaseOptional.isPresent()) {
            return new DTORtaPersonalizacion("noexiste");
        }

        if (!posiblePersonalizacionOptional.isPresent()) {
            return new DTORtaPersonalizacion("noexiste");
        }

        ProductoBase productoBase = productoBaseOptional.get();
        PosiblePersonalizacion personalizacion = posiblePersonalizacionOptional.get();

        return new DTORtaPersonalizacion("existe", personalizacion.getAreaDePersonalizacion(), personalizacion.getTipoDePersonalizacion(), productoBase.getPrecio());
    }
}
