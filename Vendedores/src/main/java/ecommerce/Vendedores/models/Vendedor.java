package ecommerce.Vendedores.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendedor")
@Getter
@Setter
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;

    @Column(name = "apellido", columnDefinition = "VARCHAR(50)")
    private String apellido;

    @OneToMany(mappedBy = "vendedor")
    private List<Gestor> gestores = new ArrayList<>();

    @OneToMany(mappedBy = "vendedor")
    private List<MetodoPago> metodoPago = new ArrayList<>();

    @OneToMany(mappedBy = "vendedor")
    private List<ProductoFinal> productosFinales = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tienda_id", referencedColumnName = "id")
    private Tienda tienda;

    public Vendedor() {}

    public Vendedor(String nombre, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
        this.tienda = new Tienda();
        this.productosFinales = new ArrayList<>();
        this.metodoPago = new ArrayList<>();
    }

    public List<Gestor> getGestores(){return new ArrayList<Gestor>(this.gestores);}

    public void setGestor(List<Gestor> gestor){this.gestores = gestor;}

    public void agregarGestor(Gestor gestor){this.gestores.add(gestor);}

    public List<ProductoFinal> getProductosFinales(){return new ArrayList<ProductoFinal>(this.productosFinales);}

    public void setProductosFinales(List<ProductoFinal> productosFinales){this.productosFinales = productosFinales;}

    public void agregarProductosFinales(ProductoFinal productoFinal){this.productosFinales.add(productoFinal);}

    public List<MetodoPago> getMetodoPago(){return new ArrayList<MetodoPago>(this.metodoPago);}

    public void setMetodoPago(List<MetodoPago> metodoPagos){this.metodoPago = metodoPagos;}

    public void agregarMetodoPago(MetodoPago metodoPago){this.metodoPago.add(metodoPago);}
}
