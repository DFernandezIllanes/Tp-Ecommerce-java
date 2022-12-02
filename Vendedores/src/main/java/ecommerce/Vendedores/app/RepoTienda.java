package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "tienda")
public interface RepoTienda extends JpaRepository<Tienda, Long> {
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Tienda tienda);

    Tienda findByVendedorId(Long id);
}
