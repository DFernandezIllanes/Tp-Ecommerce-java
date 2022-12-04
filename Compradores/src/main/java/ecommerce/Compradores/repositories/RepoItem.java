package ecommerce.Compradores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import ecommerce.Compradores.models.Item;

@RepositoryRestResource(path = "items")
public interface RepoItem extends JpaRepository<Item, Long>{
	
	@Override
    @RestResource(exported = false)
    void deleteById(Long id);
	
	@Override
    @RestResource(exported = false)
    void delete(Item item);
}
