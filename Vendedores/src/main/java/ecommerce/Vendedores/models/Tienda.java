package ecommerce.Vendedores.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
//import javax.rmi.CORBA.Tie;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tienda")
@Getter @Setter
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    //    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")

    @OneToOne(mappedBy = "tienda")
    private Vendedor vendedor;
    @OneToMany(mappedBy = "tienda")
    private List<Publicacion> publicaciones = new ArrayList<>();

    public Tienda(Vendedor vendedor){
        this.vendedor = vendedor;
        this.publicaciones = new ArrayList<>();
    }

    public Tienda() {}

    public List<Publicacion> getPublicacion(){return new ArrayList<Publicacion>(this.publicaciones);}

    public void setPublicacion(List<Publicacion> publicacion){this.publicaciones = publicacion;}

    public void agregarPublicacion(Publicacion publicacion){this.publicaciones.add(publicacion);}
}
