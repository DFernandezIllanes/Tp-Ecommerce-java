package ecommerce.Vendedores.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "personalizacion")
@Getter @Setter
public class Personalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_final_id",  referencedColumnName = "id ")
    private ProductoFinal productoFinal;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "posible_personalizacion")
    private String posiblePersonalizacion;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "nombre ")
    private String nombre;

    public Personalizacion(Double precio, String posiblePersonalizacion, String contenido, String nombre,ProductoFinal productoFinal){
        this.precio = precio;
        this.posiblePersonalizacion = posiblePersonalizacion;
        this.contenido = contenido;
        this.nombre = nombre;
        this.productoFinal = productoFinal;
    }

//    public Personalizacion(Double precio, String posiblePersonalizacion, String contenido, String nombre){
//        this.precio = precio;
//        this.posiblePersonalizacion = posiblePersonalizacion;
//        this.contenido = contenido;
//        this.nombre = nombre;
//    }

    public Personalizacion() {

    }
}
