package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@RepositoryRestController
@RestController
public class VendedorController {

    @Autowired
    RepoVendedor repoVendedor;

    @Autowired
    RepoGestor repoGestor;

    @Autowired
    RepoTienda repoTienda;

    @Autowired
    RepoProductoFinal repoProductoFinal;

    @Autowired
    RepoPersonalizacion repoPersonalizacion;
    @Autowired
    GestorProxy proxy;

    @PostMapping("/vendedores/vendedor")
    public @ResponseBody ResponseEntity<Object> crearVendedor(@RequestBody DTOVendedor vendedor) {
        Vendedor newVendedor = new Vendedor(vendedor.getNombre(), vendedor.getApellido());

        if (newVendedor.getNombre().isEmpty() || newVendedor.getApellido().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan datos del vendedor");
        }
        repoVendedor.save(newVendedor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Vendedor Creado");
    }


    @PostMapping("/vendedores/{vendedorId}/gestor/{gestorId}")
    public @ResponseBody ResponseEntity<Object> vincularGestor2(@PathVariable("vendedorId") Long vendedorId, @PathVariable("gestorId") Long gestorId) {

        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);
        Optional<Gestor> gestorOptional = repoGestor.findById(gestorId);

        if (!vendedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }

        DTORtaVinculacion res = proxy.existeGestor(gestorId, vendedorId);

        if (res.getStatus().equals("existe")) {
            if (gestorOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El gestor ya se encuentra vinculado");
            }
            Vendedor vendedor = vendedorOptional.get();
            Gestor newGestor = new Gestor(gestorId, vendedor);
            repoGestor.save(newGestor);
            return ResponseEntity.status(HttpStatus.OK).body(res.getDetalle() + " y se vinculo el gestor con el vendedor");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res.getDetalle());
        }
    }


    @PostMapping("/vendedores/{vendedorId}/gestor/{gestorId}/procutoBase/{productoBaseId}")
    public @ResponseBody ResponseEntity<Object> crearProdFinal(
            @PathVariable("vendedorId") Long vendedorId,
            @PathVariable("gestorId") Long gestorId,
            @PathVariable("productoBaseId") Long productoBaseId,
            @RequestBody DTOProductoFinal productoFinal) {
        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);
        Optional<Gestor> gestorOptional = repoGestor.findById(gestorId);
        Optional<Tienda> tiendaOptional = Optional.ofNullable(repoTienda.findByVendedorId(vendedorId));

        if (!vendedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }

        if (gestorOptional.isPresent()) {
            Gestor gestor = gestorOptional.get();
            if (gestor.getVendedor().getId().equals(vendedorId)) {
                DTORtaPersonalizacion res = proxy.posiblePersonalizacion(gestorId, productoBaseId);

                if (res.getStatus().equals("existe")) {
                    Vendedor vendedor = vendedorOptional.get();
                    ProductoFinal newproductoFinal = new ProductoFinal(vendedor, productoBaseId, productoFinal.getPrecio());
                    repoProductoFinal.save(newproductoFinal);

                    return ResponseEntity.status(HttpStatus.OK).body("Producto final creado con exito!");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto base");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El vendedor y el gestor no estan vinculados");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el gestor");
    }

    @PostMapping("/vendedores/{vendedorId}/gestor/{gestorId}/productoFinal/{productoFinalId}")
    public @ResponseBody ResponseEntity<Object> agregarProdFianl(
            @PathVariable("vendedorId") Long vendedorId,
            @PathVariable("gestorId") Long gestorId,
            @PathVariable("productoFinalId") Long productoFinalId,
            @RequestBody DTOPersonalizacion personalizacion
    ) {
        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);
        Optional<Gestor> gestorOptional = repoGestor.findById(gestorId);
        Optional<ProductoFinal> productoFinalOptional = repoProductoFinal.findById(productoFinalId);

        if (!vendedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }

        if (!productoFinalOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto final");
        }

        if (gestorOptional.isPresent()) {
            Gestor gestor = gestorOptional.get();
            if (gestor.getVendedor().getId().equals(vendedorId)) {
                ProductoFinal productoFinal = productoFinalOptional.get();

                Long productoBaseId = productoFinal.getProductoBase();

                DTORtaPersonalizacion res = proxy.posiblePersonalizacion(gestorId, productoBaseId);

                if (res.getStatus().equals("existe")) {
                    Optional<Personalizacion> personalizacionOptional = Optional.ofNullable(repoPersonalizacion.findByNombre(personalizacion.getNombre()));

                    if(personalizacionOptional.isPresent()){
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe una personalizacion con ese nombre");
                    }

                    Personalizacion newPersonalizacion = new Personalizacion(personalizacion.getPrecio(), res.getAreaPersonalizacion() + "-" + res.getTipoPersonalizacion(), personalizacion.getContenido(), personalizacion.getNombre(), productoFinal);
                    repoPersonalizacion.save(newPersonalizacion);

                    return ResponseEntity.status(HttpStatus.OK).body("Personalizacion creada con exito");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto base");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El vendedor y el gestor no estan vinculados");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el gestor");

        }
    }

    @Transactional
    @DeleteMapping("/vendedores/{vendedorId}/productoFinal/{productoFinalId}")
    public @ResponseBody ResponseEntity<Object> deleteProductoFianl(
            @PathVariable("vendedorId") Long vendedorId,
            @PathVariable("productoFinalId") Long productoFinalId
    ){
        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);
        Optional<ProductoFinal> productoFinalOptional = repoProductoFinal.findById(productoFinalId);

        if(!vendedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }

        if(productoFinalOptional.isPresent()){
            ProductoFinal productoFinal = productoFinalOptional.get();

            if(!productoFinal.getVendedor().getId().equals(vendedorId)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontro el producto final para ese vendedor");
            }

            if(productoFinal.isActivo()){
                productoFinal.setActivo(false);
                return ResponseEntity.status(HttpStatus.OK).body("producto final borrado");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el producto final");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto final");
    }
}
