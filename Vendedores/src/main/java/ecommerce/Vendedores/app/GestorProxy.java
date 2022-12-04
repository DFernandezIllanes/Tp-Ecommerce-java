package ecommerce.Vendedores.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gestores")
public interface GestorProxy {

    @GetMapping("/gestores/{gestorId}/vendedores/{vendedoresId}")
    DTORtaVinculacion existeGestor(@PathVariable("gestorId") Long gestorId,
    		@PathVariable("vendedoresId") Long vendedoresId);

    @GetMapping("/gestores/{gestorId}/productoBase/{productoBaseId}")
    DTORtaPersonalizacion posiblePersonalizacion(@PathVariable("gestorId")Long gestorId, 
    		@PathVariable("productoBaseId")Long productoBaseId);
}
