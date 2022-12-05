package ecommerce.Gestores.app;

import ecommerce.Gestores.models.dtos.DTOPosiblePersonalizacion;
import ecommerce.Gestores.models.PosiblePersonalizacion;
import ecommerce.Gestores.models.ProductoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RepositoryRestController
public class ProductoBaseController {
    @Autowired
    RepoProductoBase repoProductoBase;

    @Autowired
    RepoPosiblePersonalizacion repoPosiblePersonalizacion;

    @PostMapping("/productosbase/{productoBaseId}/personalizaciones")
    public @ResponseBody ResponseEntity<Object> crearPosiblePersonalizacion(
            @PathVariable("productoBaseId") Long productoBaseId,
            @RequestBody DTOPosiblePersonalizacion personalizacion
    ) {
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = Optional.ofNullable(repoPosiblePersonalizacion.findByAreaDePersonalizacionAndProductoBaseId(personalizacion.getAreaDePersonalizacion(), productoBaseId));

        if (!productoBaseOptional.isPresent() || !productoBaseOptional.get().isActivo()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el producto base");
        }
        ProductoBase productoBase = productoBaseOptional.get();
        if (posiblePersonalizacionOptional.isPresent() && posiblePersonalizacionOptional.get().getProductoBase().getId() == productoBaseId) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe el área de personalización");
        }
        if (personalizacion.getAreaDePersonalizacion().isEmpty() || personalizacion.getTipoDePersonalizacion().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan datos de la personalizacion");
        }

        PosiblePersonalizacion posiblePersonalizacion = new PosiblePersonalizacion(personalizacion.getAreaDePersonalizacion(), personalizacion.getTipoDePersonalizacion(), productoBase);
        repoPosiblePersonalizacion.save(posiblePersonalizacion);
        return ResponseEntity.status(HttpStatus.OK).body("Posible personalización añadida");


    }

    @Transactional
    @DeleteMapping("/productosbase/{productoBaseId}/personalizaciones/{personalizacionId}")
    public @ResponseBody ResponseEntity<Object> deletePosiblePersonalizacion(
            @PathVariable("productoBaseId") Long productoBaseId,
            @PathVariable("personalizacionId") Long personalizacionId
    ) {
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = repoPosiblePersonalizacion.findById(personalizacionId);
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);

        if(productoBaseOptional.isPresent() || productoBaseOptional.get().isActivo()){
            if (posiblePersonalizacionOptional.isPresent()) {
                PosiblePersonalizacion posiblePersonalizacion = posiblePersonalizacionOptional.get();
                if (posiblePersonalizacion.isActivo() && posiblePersonalizacion.getProductoBase().getId().equals(productoBaseId)) {
                    posiblePersonalizacion.setActivo(false);
                    return ResponseEntity.status(HttpStatus.OK).body("Posible Personalizacion borrada");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion o el producto base");
    }

    @Transactional
    @PatchMapping("/productosbase/{productoBaseId}/personalizaciones/{personalizacionId}")
    public @ResponseBody ResponseEntity<Object> actualizarPosiblePersonalizacion(
            @PathVariable("productoBaseId") Long productoBaseId,
            @PathVariable("personalizacionId") Long personalizacionId,
            @RequestBody DTOPosiblePersonalizacion actualizacionPP
    ) {
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = repoPosiblePersonalizacion.findById(personalizacionId);
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);

        if(!productoBaseOptional.isPresent() || !productoBaseOptional.get().isActivo()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto base id: "+ productoBaseId);
        }

        if (posiblePersonalizacionOptional.isPresent() && posiblePersonalizacionOptional.get().isActivo()) {
            PosiblePersonalizacion posiblePersonalizacion = posiblePersonalizacionOptional.get();

            if (posiblePersonalizacion == null && !posiblePersonalizacion.isActivo()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personalizacion no encontrada");
            }

            posiblePersonalizacion.setAreaDePersonalizacion(actualizacionPP.getAreaDePersonalizacion());
            posiblePersonalizacion.setTipoDePersonalizacion(actualizacionPP.getTipoDePersonalizacion());
            return ResponseEntity.status(HttpStatus.OK).body("Personalizacion id: " + posiblePersonalizacion.getId() + " actualizada con exito");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion");
    }

}
