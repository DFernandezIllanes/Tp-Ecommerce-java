package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "metodoPago")
public interface RepoMetodoPago extends JpaRepository<MetodoPago, Long> {
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(MetodoPago metodoPago);

    MetodoPago findByMetodopago(String metodoPago);
}
