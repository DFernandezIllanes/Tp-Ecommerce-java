package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.*;

import ecommerce.Vendedores.models.dtos.DTORtaGestor;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Tie;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RepositoryRestController
@RestController
public class TiendaController {

    @Autowired
    RepoTienda repoTienda;

    @Autowired
    RepoProductoFinal repoProductoFinal;

    @Autowired
    RepoPublicacion repoPublicacion;

    @Autowired
    RepoPersonalizacion repoPersonalizacion;

    @Autowired
    GestorProxy proxy;

    @PostMapping("tienda/{tiendaId}/newPublicacion")
    public @ResponseBody ResponseEntity<Object> crearPublicacion(
            @PathVariable("tiendaId") Long tiendaId,
            @RequestBody DTOPublicacion publicacion
    ) {

        Optional<Tienda> tiendaOptional = repoTienda.findById(tiendaId);

        if (!tiendaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la tienda");
        }

        Tienda tienda = tiendaOptional.get();

        Vendedor vendedor = tienda.getVendedor();

        Optional<ProductoFinal> productoFinalOptional = repoProductoFinal.findById(publicacion.getProductoFinalId());


        if (productoFinalOptional.isPresent()) {

            ProductoFinal productoFinal = productoFinalOptional.get();

            if (publicacion.personalizacionId != null) {
                Optional<Personalizacion> personalizacionOptional = repoPersonalizacion.findById(publicacion.personalizacionId);
                if (personalizacionOptional.isPresent()) {

//                    ProductoFinal productoFinal = productoFinalOptional.get();
                    Personalizacion personalizacion = personalizacionOptional.get();
//
                    if (!productoFinal.getVendedor().getId().equals(vendedor.getId())) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto final no pertenece al vendedor asociado a esta tienda");
                    }
                    if (!personalizacion.isActivo()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La personalizacion esta eliminada");
                    }
                    if (personalizacion.getProductoFinal().getId().equals(productoFinal.getId())) {
                        Publicacion newPublicacion = new Publicacion(tienda, productoFinal, personalizacion.getNombre(), productoFinal.getPrecio() + personalizacion.getPrecio());
                        repoPublicacion.save(newPublicacion);
                        return ResponseEntity.status(HttpStatus.OK).body("Publicacion creada con exito!, id: " + newPublicacion.getId());
                    }
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("No coinside el id del producto final con el de la personalziacion");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion");
            } else {
                if (productoFinal.getVendedor().getId().equals(vendedor.getId())) {
                    Publicacion newPublicacion2 = new Publicacion(tienda, productoFinal, publicacion.getNombre(), productoFinal.getPrecio());
                    repoPublicacion.save(newPublicacion2);
                    return ResponseEntity.status(HttpStatus.OK).body("Publicacion creada con exito!");
                }
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El producto no pertenece al vendedor asociado a esta tienda");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto final");
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto final no pertenece al vendedor asociado a esta tienda");
    }

    @GetMapping("/tiendas/{tiendaId}/publicaciones/{publicacionId}")
	public DTORtaPublicacion publicacion(@PathVariable("tiendaId")Long tiendaId,
			@PathVariable("publicacionId")Long publicacionId) {

    	Optional<Tienda> tiendaOptional = repoTienda.findById(tiendaId);
    	Optional<Publicacion> publicacionOptional = repoPublicacion.findById(publicacionId);

    	if(!tiendaOptional.isPresent() || !publicacionOptional.isPresent()) {
    		return new DTORtaPublicacion("no existe");
    	}

    	Publicacion publicacion = publicacionOptional.get();

    	if(publicacion.isActiva()) {
    		return new DTORtaPublicacion("existe", publicacion.getNombre(), publicacion.getPrecio());
    	} else {
    		return new DTORtaPublicacion("publicacion inactiva");
    	}
    }


    @Transactional
    @PatchMapping("/tienda/{tiendaId}/publicacion/{publicacionId}")
    public @ResponseBody ResponseEntity<Object> pausarPublicacion(@PathVariable("tiendaId") Long tiendaId, @PathVariable("publicacionId") Long publicacionId) {
        Optional<Tienda> tiendaOptional = repoTienda.findById(tiendaId);
        Optional<Publicacion> publicacionOptional = repoPublicacion.findById(publicacionId);

        if (!publicacionOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la publicacion");
        }

        if (!tiendaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la tienda");
        }
        Publicacion publicacion = publicacionOptional.get();
        Tienda tienda = tiendaOptional.get();

        if(!publicacion.getTienda().getId().equals(tiendaId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La publicacion no esta asociada a esa tienda");
        }

        if (publicacion.isActiva()) {
            if (publicacion.isPublicada()) {
                publicacion.setEstado(false);
                return ResponseEntity.status(HttpStatus.OK).body("Publicacion pausada");
            }else{
                publicacion.setEstado(true);
                return ResponseEntity.status(HttpStatus.OK).body("Publicacion activa");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la publicacion");
    }

    @Transactional
    @DeleteMapping("/tienda/{tiendaId}/publicacion/{publicacionId}")
    public @ResponseBody ResponseEntity<Object> deletePublicacion(@PathVariable("publicacionId") Long publicacionId) {
        Optional<Publicacion> publicacionOptional = repoPublicacion.findById(publicacionId);
        Publicacion publicacion = publicacionOptional.get();

        if (!publicacionOptional.isPresent() || !publicacion.isActiva()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la publicacion");
        }

        if (publicacion.getEstado()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar la publicacion porque no esta pausada");
        }

        publicacion.setActiva(false);
        return ResponseEntity.status(HttpStatus.OK).body("Publicacion borrada");
    }

    @Transactional
    @PatchMapping("publicaciones/{publicacionId}")
    public @ResponseBody ResponseEntity<Object> pausarPublicacion(@PathVariable("publicacionId")Long publicacionId){
        Optional<Publicacion> publicacionOptional = repoPublicacion.findById(publicacionId);
        Publicacion publicacion = publicacionOptional.get();

        if (!publicacionOptional.isPresent() || !publicacion.isActiva()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la publicacion");
        }

        publicacion.setEstado(false);
        return ResponseEntity.status(HttpStatus.OK).body("Publicacion pausada");
    }

    @Retry(name = "default", fallbackMethod = "noDisponible")
    @PostMapping("/tiendas/{tiendaId}/datospublicaciones")
	public DTODatosVenta obtenerDatosVenta(@PathVariable("tiendaId") Long tiendaId,
			@RequestBody List<Long> listaPublicacionesIds) {

    	Optional<Tienda> tiendaOptional = repoTienda.findById(tiendaId);
    	Tienda tienda = tiendaOptional.get();

    	List<Publicacion> publicaciones = tienda.getPublicaciones();
    	ArrayList<Long> productosBaseIds = new ArrayList<>();

    	Iterator<Publicacion> iteradorPublicaciones = publicaciones.iterator();
    	while(iteradorPublicaciones.hasNext()) {
    		Publicacion publicacion = iteradorPublicaciones.next();

    		for(int i=0; i<listaPublicacionesIds.size(); i++) {

    			if(publicacion.getId().equals(listaPublicacionesIds.get(i))) {

    				productosBaseIds.add(publicacion.getProductoFinal().getProductoBase());
    			}
    		}
    	}

    	DTORtaGestor rtaGestor = proxy.buscarTiempoDeFabricacion(productosBaseIds);

        Vendedor vendedor = tienda.getVendedor();

        DTODatosVenta datosVenta = new DTODatosVenta(vendedor.getNombre() + " " + vendedor.getApellido(),7);

    	return datosVenta;
    }

    public @ResponseBody ResponseEntity<Object> noDisponible(Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Estamos en mantenimiento, por favor intente mas tarde");
    }

    public @ResponseBody ResponseEntity<Object> noDisponible(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Estamos en mantenimiento, por favor intente mas tarde");
    }
}
