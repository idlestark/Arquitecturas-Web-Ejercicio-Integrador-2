package org.example;
import DTO.CarreraDTO;
import DTO.EstudianteDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.EstudianteCarrera;
import entities.EstudianteCarreraPK;
import factory.AbstractFactory;
import repositories.entitiesRepository.*;
import utils.HelperMySQL;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        //CREACIÓN DEL HELPER
        HelperMySQL helperMySQL = new HelperMySQL();
        helperMySQL.dropTables();

        //CREACIÓN DEL ENTITY MANAGER
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //LLENADO DE LAS TABLAS
        helperMySQL.populateDB();

        //CREACIÓN DE LOS REPOSITORIES
        AbstractFactory factory = AbstractFactory.getRepositoryFactory(1);
        EstudianteRepository estudianteRep = factory.getEstudianteRepository();
        CarreraRepository carreraRep = factory.getCarreraRepository();
        EstudianteCarreraRepository estudianteCarreraRep = factory.getEstudianteCarreraRepository();

        //SERVICIOS...

        //EJERCICIO 2.A) (DAR DE ALTA UN ESTUDIANTE)
        Estudiante estudiante = new Estudiante(17007007, "JAMES", "BOND", "MASCULINO", 52, "AZUL", 99);
        estudianteRep.insert(em, estudiante);
        System.out.println("/////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////");

        //EJERCICIO 2.B (MATRICULAR UN ESTUDIANTE EN UNA CARRERA)
        Carrera carrera = (Carrera) carreraRep.select(em, 101);
        EstudianteCarrera inscripcion = new EstudianteCarrera(new EstudianteCarreraPK(17007007, 101), estudiante, carrera, Date.valueOf("2020-01-01"), false);
        estudianteCarreraRep.insert(em, inscripcion);
        System.out.println("/////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////");

        //EJERCICIO 2.C (RECUPERAR TODOS LOS ESTUDIANTES Y ESPECIFICAR ALGUN CIRITERIO DE ORDENAMIENTO SIMPLE)
        List<EstudianteDTO> estudiantes = estudianteRep.obtenerEstudiantes(em, "APELLIDO", "DESC");
        System.out.println(" 2 - c - Lista completa de estudiantes ordenados: ");
        for (EstudianteDTO estudianteDTO : estudiantes) {
            System.out.println(estudianteDTO);
        }
        System.out.println("/////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////");

        //EJERCICIO 2.D (RECUPERAR UN ESTUDIANTE EN BASE A SU NUMERO DE LIBRETA UNIVERSITARIA)
        EstudianteDTO estu = new EstudianteDTO();
        int lu = 20;
        estu = estudianteRep.obtenerEstudianteLu(em, lu);
        System.out.println(" 2 - D - Estudiante con la libreta " + lu + ": ");
        System.out.println(estu);
        System.out.println("/////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////");

        //EJERCICIO 2.E (RECUPERAR TODOS LOS ESTUDIANTES, EN BASE A SU GENERO)
        System.out.println(" 2 - E - Lista de estudiantes por genero");
        List<EstudianteDTO> estudiantesGenero = estudianteRep.obtenerEstudiantesPorGenero(em, "MASCULINO");
        for (EstudianteDTO estudianteG : estudiantesGenero) {
            System.out.println(estudianteG);
            //fin
        }
        System.out.println("/////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////");


        //EJERCICIO 2.F (RECUPERAR LAS CARRERAS CON ESTUDIANTES INSCRIPTOS, Y ORDENAR POR CANTIDAD DE INSCRIPTOS)
        System.out.println("Carreras por estudiantes inscriptos");
        for(CarreraDTO c: carreraRep.obtenerCarrerasPorEstudiantesInscriptos(em)) {
            System.out.println(c);
        }
        System.out.println("/////////////////////////////////////////");
        System.out.println("/////////////////////////////////////////");
        //Ejercicio 2.G (RECUPERAR ESTUDIANTES DE UNA DETERMINADA CARRERA, FILTRADO POR CIUDAD DE RECIDENCIA)
        System.out.println("Estudiantes Ordenados por Carrera y Ciudad: ");

        for(EstudianteDTO e: estudianteRep.obtenerEstudiantePorCarreraYCiudad(em, "TUDAI", "Tandil")) {
            System.out.println(e);
        }

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        //EJERCICIO 3

        System.out.println("Generar Reporte: ");
        System.out.println(estudianteCarreraRep.reporteCarreras(em));

        em.getTransaction().commit();
        em.close();
        emf.close();

    }

}

