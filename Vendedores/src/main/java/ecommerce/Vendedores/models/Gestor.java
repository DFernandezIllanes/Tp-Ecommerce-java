package ecommerce.Vendedores.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "gestores")
@Getter
@Setter
public class Gestor {
    @Id
    private Long gestorId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private Vendedor vendedor;

    public Gestor() {}

    public Gestor(Long gestorId, Vendedor vendedor) {
        this.gestorId = gestorId;
        this.vendedor = vendedor;
    }
}
