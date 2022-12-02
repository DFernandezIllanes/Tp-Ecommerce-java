package ecommerce.Gestores.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "posiblePersonalizacion")
//@NoArgsConstructor
//@AllArgsConstructor
public class PosiblePersonalizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Column(name = "areaDePersonalizacion", columnDefinition = "VARCHAR(100)")
    @Getter @Setter
    private String areaDePersonalizacion;

    @Column(name = "tipoDePersonalizacion", columnDefinition = "VARCHAR(100)")
    @Getter @Setter
    private String tipoDePersonalizacion;

    @Column(name = "activo")
    @Getter @Setter
    private Boolean activo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "productoBaseId", referencedColumnName = "id")
    @Getter @Setter
    private ProductoBase productoBase;

    public boolean isActivo(){
        return activo;
    }
    public PosiblePersonalizacion(String areaDePersonalizacion, String tipoDePersonalizacion, ProductoBase productoBase){
        this.areaDePersonalizacion = areaDePersonalizacion;
        this.tipoDePersonalizacion = tipoDePersonalizacion;
        this.productoBase = productoBase;
        this.activo = true;
    }

    public PosiblePersonalizacion() {}
}
