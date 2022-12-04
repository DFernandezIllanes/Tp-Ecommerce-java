package ecommerce.Vendedores.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MetodoPago")
@Getter
@Setter
public class MetodoPago {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "metodoPago")
    private String metodopago;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Vendedor vendedor;

    public MetodoPago() {}

    public boolean isActivo(){return activo;}

    public MetodoPago(String metodopago, Vendedor vendedor){
        this.metodopago = metodopago;
        this.activo = true;
        this.vendedor = vendedor;
    }
}
