package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "gestor")
public interface RepoGestor extends JpaRepository<Gestor, Long> {
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Gestor gestor);
}
