package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.*;
import ecommerce.Vendedores.models.dtos.*;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    RepoMetodoPago repoMetodoPago;

    @PostMapping("/newVendedor")
    public @ResponseBody ResponseEntity<Object> crearVendedor(@RequestBody DTOVendedor vendedor) {
        Vendedor newVendedor = new Vendedor(vendedor.getNombre(), vendedor.getApellido());

        if (newVendedor.getNombre().isEmpty() || newVendedor.getApellido().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan datos del vendedor");
        }
        repoVendedor.save(newVendedor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Vendedor Creado con id: " + newVendedor.getId());
    }

    @Retry(name = "default", fallbackMethod = "noDisponible")
    @PostMapping("/vendedor/{vendedorId}/gestor/{gestorId}")
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

    @Retry(name = "default", fallbackMethod = "noDisponible")
    @PostMapping("/vendedor/{vendedorId}/gestor/{gestorId}/procutoBase/{productoBaseId}")
    public @ResponseBody ResponseEntity<Object> crearProdFinal(
            @PathVariable("vendedorId") Long vendedorId,
            @PathVariable("gestorId") Long gestorId,
            @PathVariable("productoBaseId") Long productoBaseId){
        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);
        Optional<Gestor> gestorOptional = repoGestor.findById(gestorId);
        //Optional<Tienda> tiendaOptional = Optional.ofNullable(repoTienda.findByVendedorId(vendedorId));

        if (!vendedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }

        if (gestorOptional.isPresent()) {
            Gestor gestor = gestorOptional.get();
            if (gestor.getVendedor().getId().equals(vendedorId)) {
                DTORtaPersonalizacion res = proxy.posiblePersonalizacion(gestorId, productoBaseId, "sinDato");

                if (res.getStatus().equals("existe")) {
                    Vendedor vendedor = vendedorOptional.get();
                    ProductoFinal newproductoFinal = new ProductoFinal(vendedor, productoBaseId, res.getPrecioBase());
                    repoProductoFinal.save(newproductoFinal);

                    return ResponseEntity.status(HttpStatus.OK).body("Producto final con id: " + newproductoFinal.getId() + ", creado con exito!");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el producto base");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El vendedor y el gestor no estan vinculados");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el gestor");
    }

    @Retry(name = "default", fallbackMethod = "noDisponible")
    @PostMapping("/vendedor/{vendedorId}/gestor/{gestorId}/productoFinal/{productoFinalId}")
    public @ResponseBody ResponseEntity<Object> agregarPersonalizacionAProdFianl(
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

                if(!productoFinal.getVendedor().getId().equals(vendedorId)){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El producto final id: " + productoFinalId + " no corresponde al vendedor id: " + vendedorId);
                }

                Long productoBaseId = productoFinal.getProductoBase();

                DTORtaPersonalizacion res = proxy.posiblePersonalizacion(gestorId, productoBaseId, personalizacion.getAreaPersonalizacion());

                if (res.getStatus().equals("existe")) {
                    Optional<Personalizacion> personalizacionOptional = Optional.ofNullable(repoPersonalizacion.findByNombreAndProductoFinalId(personalizacion.getNombre(),productoFinalId));

                    if(personalizacionOptional.isPresent() || personalizacionOptional.get().getProductoFinal().equals(productoFinalId)){
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe una personalizacion con ese nombre");
                    }

                    Personalizacion newPersonalizacion = new Personalizacion(personalizacion.getPrecio(), res.getAreaPersonalizacion() + "-" + res.getTipoPersonalizacion(), personalizacion.getContenido(), personalizacion.getNombre(), productoFinal);
                    repoPersonalizacion.save(newPersonalizacion);

                    return ResponseEntity.status(HttpStatus.OK).body("Personalizacion creada con exito, id: " + newPersonalizacion.getId());
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
    @DeleteMapping("/vendedor/{vendedorId}/personalizacion/{personalizacionId}")
    public @ResponseBody ResponseEntity<Object> eliminarPersonalizacion(
            @PathVariable("vendedorId") Long vendedorId,
            @PathVariable("personalizacionId") Long personalizacionId
    ){
        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);
        Optional<Personalizacion> personalizacionOptional = repoPersonalizacion.findById(personalizacionId);
        if (!vendedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }
        if (!personalizacionOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion");
        }

        Vendedor vendedor = vendedorOptional.get();
        Personalizacion personalizacion = personalizacionOptional.get();

        if(personalizacion.getProductoFinal().getVendedor().getId().equals(vendedorId)){
            if (personalizacion.isActivo()){
                personalizacion.setActivo(false);
                return ResponseEntity.status(HttpStatus.OK).body("Personalizacion con id: " + personalizacionId + " eliminada");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la personalizacion");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontro la personalizacion id: " + personalizacionId + " para el vendedor id: " + vendedorId);

    }

    @Transactional
    @DeleteMapping("/vendedor/{vendedorId}/productoFinal/{productoFinalId}")
    public @ResponseBody ResponseEntity<Object> deleteProductoFianal(
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

    @PostMapping("/vendedor/{vendedorId}/metodoPago")
    public @ResponseBody ResponseEntity<Object> agregarMediosDePago(
            @PathVariable("vendedorId")Long vendedorId,
            @RequestBody ArrayList<DTOMedioPago> metodoPago
            ){
        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);

        if(!vendedorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }
        Vendedor vendedor = vendedorOptional.get();

        List<Long> metodosIds = new ArrayList<>();
        ArrayList<String> medioPagoErroneos = new ArrayList<>(metodoPago.size());
        Boolean todosOk = true;
        String finalMessage = "Estos metodos de pago son erroneos: [";
        String agregadosMessage = "Estos metodos de pago fueron agregados: [";

        for (int i = 0; i < metodoPago.size(); i++){
            DTOMedioPago newMedioPago = metodoPago.get(i);
            Optional<MetodoPago> metodoPagoOptional = Optional.ofNullable(repoMetodoPago.findByMetodopagoAndVendedorId(newMedioPago.getMedioPago(), vendedorId));
            if(metodoPagoOptional.isPresent() || newMedioPago.getMedioPago().isEmpty()){
                todosOk = false;
                if(!newMedioPago.getMedioPago().isEmpty()){
                    finalMessage += newMedioPago.getMedioPago() + ", ";
                }else{
                    finalMessage += "posicion en arr: " + i + ", ";
                }
            }
        }

        if(!todosOk){
            finalMessage += "], por lo tanto no se agrego ningun metodo de pago";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(finalMessage);
        }
        for (int i = 0; i < metodoPago.size(); i++) {
            DTOMedioPago posMetodoPago = metodoPago.get(i);
            MetodoPago newMetodoPago = new MetodoPago(posMetodoPago.getMedioPago(), vendedor);
            repoMetodoPago.save(newMetodoPago);
            agregadosMessage += newMetodoPago.getMetodopago() + ", ";
        }
        agregadosMessage += " ] ";
        return ResponseEntity.status(HttpStatus.OK).body(agregadosMessage);
    }

    @Transactional
    @DeleteMapping("/vendedor/{vendedorId}/metodoPago/{metodoPagoId}")
    public @ResponseBody ResponseEntity<Object> eliminarMetodoPago(
            @PathVariable("vendedorId") Long vendedorId,
            @PathVariable("metodoPagoId") Long metodoPagoId
    ){
        Optional<Vendedor> vendedorOptional = repoVendedor.findById(vendedorId);
        Optional<MetodoPago> metodoPagoOptional = repoMetodoPago.findById(metodoPagoId);

        if(!vendedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el vendedor");
        }

        if(!metodoPagoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el metodo de pago");
        }

        MetodoPago metodoPago = metodoPagoOptional.get();
        Vendedor vendedor = vendedorOptional.get();

        if(metodoPago.getVendedor().getId().equals(vendedorId)){
            if(metodoPago.isActivo()){
                metodoPago.setActivo(false);
                return ResponseEntity.status(HttpStatus.OK).body("Metodo de pago borrado");
            }
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el metodo de pago");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el metodo de pago");

    }

    public @ResponseBody ResponseEntity<Object> noDisponible(Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Estamos en mantenimiento, por favor intente mas tarde");
    }

    public @ResponseBody ResponseEntity<Object> noDisponible(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Estamos en mantenimiento, por favor intente mas tarde");
    }
}
