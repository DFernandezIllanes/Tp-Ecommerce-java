package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.Personalizacion;
import ecommerce.Vendedores.models.ProductoFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "personalizacion")
public interface RepoPersonalizacion extends JpaRepository<Personalizacion, Long> {
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Personalizacion personalizacion);

    Personalizacion findByNombreAndProductoFinalId(String nombre, Long prodFinalId);
}
