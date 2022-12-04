package ecommerce.Compradores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import ecommerce.Compradores.models.CarritoDeCompra;


@RepositoryRestResource(path = "carritos")
public interface RepoCarritoDeCompra extends JpaRepository<CarritoDeCompra, Long>{
	
	@Override
    @RestResource(exported = false)
    void deleteById(Long id);
	
	@Override
    @RestResource(exported = false)
    void delete(CarritoDeCompra carrito);

}
