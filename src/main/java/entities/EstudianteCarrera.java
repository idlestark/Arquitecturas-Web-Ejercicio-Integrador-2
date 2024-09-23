package entities;
import javax.persistence.*;

@Entity
public class EstudianteCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne (fetch = FetchType.EAGER)
    private Estudiante estudiante;
    @ManyToOne(fetch = FetchType.EAGER)
    private Carrera carrera;
    @Column
    private int antiguedad;
    @Column
    private boolean egresado;

    public EstudianteCarrera (Estudiante estudiante, Carrera carrera, int antiguedad, boolean egresado) {
        super();
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.antiguedad = antiguedad;
        this.egresado = egresado;
    }

    public EstudianteCarrera() {
        super();
    }

    public int getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public boolean isEgresado() {
        return egresado;
    }
}