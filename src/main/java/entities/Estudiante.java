package entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Estudiante {

    @Id
    private int dni;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int edad;

    @Column
    private String genero;

    @Column
    private String ciudad;

    @Column
    private int lu;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<EstudianteCarrera> carreras;


    public Estudiante() {super();}

    public Estudiante(int dni, String nombre, String apellido, String genero, int edad, String ciudad, int lu) {
        super();
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.edad = edad;
        this.ciudad = ciudad;
        this.lu = lu;
        this.carreras = new ArrayList<EstudianteCarrera>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public int getDni() {
        return dni;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getNumeroLibreta() {
        return lu;
    }


    public List<EstudianteCarrera> getCarreras() {
        return carreras;
    }
}
