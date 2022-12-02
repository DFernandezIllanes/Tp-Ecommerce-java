package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "vendedores")
public interface RepoVendedor extends JpaRepository<Vendedor, Long> {

    @Override
    @RestResource(exported = false)
    void deleteById(Long id);

    Vendedor findByNombre(String nombre);

    Vendedor findByApellido(String apellido);

}
