package ecommerce.Compradores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import ecommerce.Compradores.models.Comprador;

@RepositoryRestResource(path = "compradores")
public interface RepoComprador extends JpaRepository<Comprador, Long>{
	
	@Override
    @RestResource(exported = false)
    void deleteById(Long id);
	
	@Override
    @RestResource(exported = false)
    void delete(Comprador comprador);

    Comprador findByNombre(String nombre);

    Comprador findByApellido(String apellido);

}
