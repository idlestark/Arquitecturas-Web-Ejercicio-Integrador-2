package entities;

import javax.persistence.Embeddable;

@Embeddable
public class EstudianteCarreraPK implements java.io.Serializable {

    private static final long serialVersionUID = -2173029278696333769L;

    private int dni;

    private int idCarrera;

    public EstudianteCarreraPK() {
        super();
    }

    public EstudianteCarreraPK(int dni, int idCarrera) {
        this.dni = dni;
        this.idCarrera = idCarrera;
    }


    public int getDni() {
        return dni;
    }

    public int getIdCarrera() {
        return idCarrera;
    }
}