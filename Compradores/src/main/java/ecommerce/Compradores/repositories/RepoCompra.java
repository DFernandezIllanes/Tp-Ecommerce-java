package ecommerce.Compradores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import ecommerce.Compradores.models.Compra;

@RepositoryRestResource(path = "compras")
public interface RepoCompra extends JpaRepository<Compra, Long>{
	
	@Override
	@RestResource
	void deleteById(Long id);
	
	@Override
	@RestResource
	void delete(Compra compra);

}
