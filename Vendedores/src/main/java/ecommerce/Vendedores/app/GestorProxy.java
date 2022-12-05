package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.dtos.DTORtaGestor;
import ecommerce.Vendedores.models.dtos.DTORtaPersonalizacion;
import ecommerce.Vendedores.models.dtos.DTORtaVinculacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gestores")
public interface GestorProxy {

    @GetMapping("/gestores/{gestorId}/vendedores/{vendedoresId}")
    DTORtaVinculacion existeGestor(@PathVariable("gestorId") Long gestorId, @PathVariable("vendedoresId") Long vendedoresId);

    @GetMapping("/gestores/{gestorId}/productoBase/{productoBaseId}/areaPersonalizacion/{areaPersonalizacionStr}")
    DTORtaPersonalizacion posiblePersonalizacion(@PathVariable("gestorId")Long gestorId, @PathVariable("productoBaseId")Long productoBaseId, @PathVariable("areaPersonalizacionStr")String areaPersonalizacionStr);
//    @GetMapping("/gestores/{gestorId}/productoBase/{productoBaseId}")
//    DTORtaPersonalizacion posiblePersonalizacion(@PathVariable("gestorId")Long gestorId,
//    		@PathVariable("productoBaseId")Long productoBaseId);

    @PostMapping("/gestores/productos/")
    DTORtaGestor buscarTiempoDeFabricacion(@RequestBody List<Long > productosBaseIds);

}
