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
    @ManyToOne
    private Direccion ciudad;
    @Column
    private int numeroLibreta;
    @ManyToMany (fetch = FetchType.LAZY, mappedBy = "estudiantes")
    private List<Carrera> carreras;
    @Column
    private int antiguedad;


    public Estudiante() {super();}

    public Estudiante(int dni, String nombre, String apellido, String genero, int edad, Direccion ciudad, int numeroLibreta) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.edad = edad;
        this.dni = dni;
        this.ciudad = ciudad;
        this.numeroLibreta = numeroLibreta;
        this.carreras = new ArrayList<>();
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

    public Direccion getCiudad() {
        return ciudad;
    }

    public int getNumeroLibreta() {
        return numeroLibreta;
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }
}
