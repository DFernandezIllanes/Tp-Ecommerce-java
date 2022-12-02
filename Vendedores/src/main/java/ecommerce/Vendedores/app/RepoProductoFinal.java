package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.ProductoFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
@RepositoryRestResource(path = "productoFinal")
public interface RepoProductoFinal extends JpaRepository<ProductoFinal, Long> {
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(ProductoFinal productoFinal);
}
