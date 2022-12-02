package ecommerce.Vendedores.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productoFinal")
@Getter @Setter
public class ProductoFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Vendedor vendedor;

    @Column(name = "productoBase_id")
    private Long productoBase;

    @Column(name = "precio_base")
    private Double precio;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "productoFinal")
    private List<Personalizacion> personalizacion;
    @OneToMany(mappedBy = "productoFinal")
    private List<Publicacion> publicacion;

    public Boolean isActivo(){return activo;}

    public ProductoFinal() {}

    public ProductoFinal(Vendedor vendedor, Long ProductoBase, Double precio){
        this.vendedor = vendedor;
        this.productoBase = ProductoBase;
        this.precio = precio;
        this.publicacion = new ArrayList<>();
        this.activo = true;
    }

}
