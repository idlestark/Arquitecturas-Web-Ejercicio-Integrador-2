package entities;

import javax.persistence.*;

@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String ciudad;
    @Column
    private String calle;
    @Column
    private int numero;

    public Direccion(){super();}

    public Direccion(String ciudad, int numero, String calle) {
        super();
        this.ciudad = ciudad;
        this.numero = numero;
        this.calle = calle;
    }


}
