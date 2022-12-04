package ecommerce.Gestores.app;

import ecommerce.Gestores.models.DTOPosiblePersonalizacion;
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
    ){
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = Optional.ofNullable(repoPosiblePersonalizacion.findByAreaDePersonalizacion(personalizacion.getAreaDePersonalizacion()));

        if(!productoBaseOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el producto");
        } else {
            ProductoBase productoBase = productoBaseOptional.get();

            if(posiblePersonalizacionOptional.isPresent() && posiblePersonalizacionOptional.get().getProductoBase().getId() == productoBaseId) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe el área de personalización");
            } else {
                PosiblePersonalizacion posiblePersonalizacion = new PosiblePersonalizacion(personalizacion.getAreaDePersonalizacion(), personalizacion.getTipoDePersonalizacion(), productoBase);
                repoPosiblePersonalizacion.save(posiblePersonalizacion);
                return ResponseEntity.status(HttpStatus.OK).body("Posible personalización añadida, id: " + posiblePersonalizacion.getId());
            }
        }
    }

    @Transactional
    @DeleteMapping("/productosbase/{productoBaseId}/personalizaciones/{personalizacionId}")
    public @ResponseBody ResponseEntity<Object> deletePosiblePersonalizacion(
            @PathVariable("productoBaseId") Long productoBaseId,
            @PathVariable("personalizacionId") Long personalizacionId
    ){
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = repoPosiblePersonalizacion.findById(personalizacionId);
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);

        if(posiblePersonalizacionOptional.isPresent() && productoBaseOptional.isPresent()){
            PosiblePersonalizacion posiblePersonalizacion = posiblePersonalizacionOptional.get();
            if(posiblePersonalizacion.isActivo()){
                posiblePersonalizacion.setActivo(false);
                return ResponseEntity.status(HttpStatus.OK).body("Posible Personalizacion borrada");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion o el producto base");
    }

    @Transactional
    @PatchMapping("/productosbase/{productoBaseId}/personalizaciones/{personalizacionId}")
    public @ResponseBody ResponseEntity<Object> actualizarPosiblePersonalizacion(
            @PathVariable("productoBaseId") Long productoBaseId,
            @PathVariable("personalizacionId") Long personalizacionId,
            @RequestBody DTOPosiblePersonalizacion actualizacionPP
    ){
        Optional<PosiblePersonalizacion> posiblePersonalizacionOptional = repoPosiblePersonalizacion.findById(personalizacionId);
        Optional<ProductoBase> productoBaseOptional = repoProductoBase.findById(productoBaseId);

        if(posiblePersonalizacionOptional.isPresent()){
            PosiblePersonalizacion posiblePersonalizacion = posiblePersonalizacionOptional.get();

            if(posiblePersonalizacion == null && !posiblePersonalizacion.isActivo()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personalizacion no encontrada");
            }

            posiblePersonalizacion.setAreaDePersonalizacion(actualizacionPP.getAreaDePersonalizacion());
            posiblePersonalizacion.setTipoDePersonalizacion(actualizacionPP.getTipoDePersonalizacion());
//            repoPosiblePersonalizacion.save(posiblePersonalizacion);
            return ResponseEntity.status(HttpStatus.OK).body("Personalizacion realizada con exito");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion");
    }

}
