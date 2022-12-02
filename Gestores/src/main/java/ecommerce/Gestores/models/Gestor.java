package ecommerce.Gestores.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gestor")
public class Gestor {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Setter
    @Getter
    @Column(name = "apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;

    @OneToMany(mappedBy = "gestor")
    private List<ProductoBase> productosBase;

    public Gestor(){
        this.productosBase = new ArrayList<>();
    }

    public Gestor(String nombre){
        this.nombre = nombre;
        this.productosBase = new ArrayList<>();
    }

    public Gestor(String nombre, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
        this.productosBase = new ArrayList<>();
    }

    public List<ProductoBase> getProductosBase() {
        return new ArrayList<ProductoBase>(this.productosBase);
    }

    public void setProductosBase(List<ProductoBase> productosBase) {
        this.productosBase = productosBase;
    }

    public void agregarProductoBase(ProductoBase productoBase){
        this.productosBase.add(productoBase);
    }
}
