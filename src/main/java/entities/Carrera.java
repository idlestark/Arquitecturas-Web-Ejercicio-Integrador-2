package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrera {

    @Id
    private int idCarrera;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EstudianteCarrera> estudiantes;

    public Carrera() {super();}

    public Carrera (int idCarrera, String nombre) {
        super();
        this.idCarrera = idCarrera;
        this.nombre = nombre;
        this.estudiantes = new ArrayList<EstudianteCarrera>();
    }

    public int getIdCarrera() {return this.idCarrera;}

    public String getNombre() {return this.nombre;}

    public List<EstudianteCarrera> getEstudiantes() {return this.estudiantes;}

}
