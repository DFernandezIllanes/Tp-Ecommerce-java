package ecommerce.Compradores.proxys;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ecommerce.Compradores.models.dtos.DTORtaPublicacion;

@FeignClient(name = "vendedores")
public interface TiendaProxy {
	
	@GetMapping("/tiendas/{tiendaId}/publicaciones/{publicacionId}")
	DTORtaPublicacion publicacion(@PathVariable("tiendaId")Long tiendaId, @PathVariable("publicacionId")Long publicacionId);
	

}
