package ecommerce.Gestores.app;

import ecommerce.Gestores.models.PosiblePersonalizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "posiblespersonalizaciones")
public interface RepoPosiblePersonalizacion extends JpaRepository<PosiblePersonalizacion, Long> {
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(PosiblePersonalizacion posiblePersonalizacion);

    PosiblePersonalizacion findByAreaDePersonalizacion(String areaDePersonalizacion);

    PosiblePersonalizacion findByTipoDePersonalizacion(String tipoDePersonalizacion);

    PosiblePersonalizacion findByProductoBaseId(Long productoBaseId);
}
