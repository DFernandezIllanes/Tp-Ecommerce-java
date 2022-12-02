package ecommerce.Vendedores.app;

import ecommerce.Vendedores.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;

@RepositoryRestResource(path = "publicaciones")
public interface RepoPublicacion extends JpaRepository<Publicacion, Long> {
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Publicacion publicacion);

    Publicacion findByEstado(Boolean estado);

    Publicacion findByFecha(LocalDateTime fecha);
}
