package ecommerce.Compradores.proxys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ecommerce.Compradores.models.dtos.DTODatosVenta;
import ecommerce.Compradores.models.dtos.DTORtaPublicacion;

@FeignClient(name = "vendedores")
public interface TiendaProxy {
	
	@GetMapping("/tiendas/{tiendaId}/publicaciones/{publicacionId}")
	DTORtaPublicacion publicacion(@PathVariable("tiendaId")Long tiendaId, @PathVariable("publicacionId")Long publicacionId);
	
	@PostMapping("/tiendas/{tiendaId}/datospublicaciones")
	DTODatosVenta obtenerDatosVenta(@PathVariable("tiendaId") Long tiendaId, 
			@RequestBody List<Long> listaPublicacionesIds);
}
