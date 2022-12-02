package ecommerce.Vendedores.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacion")
@Getter
@Setter
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false )
    @JoinColumn(name = "tienda_id", referencedColumnName = "id")
    private Tienda tienda;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "activa")
    private Boolean activa;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private Double precio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_final_id", referencedColumnName = "id ")
    private ProductoFinal productoFinal;

    public boolean isPublicada(){return estado;}

    public boolean isActiva(){return activa;}

    public Publicacion(){}


    public Publicacion(Tienda tienda, ProductoFinal productoFinal, String nombre, Double precio){
        this.tienda = tienda;
        this.fecha = LocalDateTime.now();
        this.nombre = nombre;
        this.precio = precio;
        this.estado = true;
        this.activa = true;
        this.productoFinal = productoFinal;
    }
    //TODO ver como hacer esta parte
//    public List<ProductoFinal> getProductoFinal(){
//        return (List<ProductoFinal>) new ProductoFinal(this.productoFinal);
//    }
//
//    public void setProdictoFinal(List<ProductoFinal> prodictoFinal){
//        this.productoFinal = (ProductoFinal) prodictoFinal;
//    }
}