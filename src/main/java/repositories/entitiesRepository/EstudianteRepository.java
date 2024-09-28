package repositories.entitiesRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import entities.Estudiante;
import repositories.InterfaceRepository;
import DTO.EstudianteDTO;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepository implements InterfaceRepository<EstudianteRepository> {

    public EstudianteRepository() {

    }


    @Override
    public Object select (EntityManager em, int dni) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.dni = :dni";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("dni", dni);
        return query.getSingleResult();
    }

    // 2-A
    @Override
    public void insert (EntityManager em, Object est) {
        if(est instanceof Estudiante){
            em.persist(est);
        }
    }

    @Override
    public void update (EntityManager em, Object est, int dni) {
        if(est instanceof Estudiante) {
            String jpql = "UPDATE Estudiante e SET e.nombre = :nombre, e.apellido = :apellido, e.edad = :edad, " +
                    "e.genero = :genero, e.ciudad = :ciudad, e.lu = :numeroLibreta " +
                    "WHERE e.dni = :dni";

            Estudiante estudianteActualizado = (Estudiante) est;

            Query query = em.createQuery(jpql);

            query.setParameter("nombre", estudianteActualizado.getNombre());
            query.setParameter("apellido", estudianteActualizado.getApellido());
            query.setParameter("edad", estudianteActualizado.getEdad());
            query.setParameter("genero", estudianteActualizado.getGenero());
            query.setParameter("ciudad", estudianteActualizado.getCiudad());
            query.setParameter("dni", estudianteActualizado.getDni());

            query.executeUpdate();
        }
    }

    @Override
    public void delete (EntityManager em, int dni) {
        String jpql = "DELETE FROM Estudiante e WHERE e.dni = :dni";
        Query query = em.createQuery(jpql);
        query.setParameter("dni", dni);
        query.executeUpdate();
    }

    @Override
    public Object find (EntityManager em, int dni) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.dni = :dni";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("dni", dni);
        return query.getSingleResult();
    }

    public static List<EstudianteDTO> obtenerEstudiantes (EntityManager em, String criterioIngresado, String ordenIngresado) {

        String criterioBusqueda = "e.dni";
        String ordenBusqueda = "ASC";
        
        if (ordenIngresado.toUpperCase() == "DESC")  ordenBusqueda = "DESC";

        switch (criterioIngresado.toUpperCase()) {

            case "NOMBRE":
                criterioBusqueda = "e.nombre";
                break;

            case "APELLIDO":
                criterioBusqueda = "e.apellido";
                break;

            case "EDAD":
                criterioBusqueda = "e.edad";
                break;

            case "GENERO":
                criterioBusqueda = "e.genero";
                break;

            case "CIUDAD":
                criterioBusqueda = "e.ciudad";
                break;

            case "LU":
                criterioBusqueda = "e.lu";
                break;
                

        }

        String jpql = "SELECT new DTO.EstudianteDTO(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu) " +
                "FROM Estudiante e "+
                "ORDER BY " +  criterioBusqueda  +" " + ordenIngresado;
        TypedQuery<EstudianteDTO> query = em.createQuery(jpql, EstudianteDTO.class);
        return query.getResultList();
    }

    public static EstudianteDTO obtenerEstudianteLu (EntityManager em, int nLibreta){
        String jpql = "SELECT new DTO.EstudianteDTO(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu) " +
                "FROM Estudiante e " +
                "WHERE e.lu = :numeroLibreta";
        TypedQuery<EstudianteDTO> query = em.createQuery(jpql, EstudianteDTO.class);
        query.setParameter("numeroLibreta", nLibreta);
        return query.getSingleResult();
    }

    public static List<EstudianteDTO> obtenerEstudiantesPorGenero (EntityManager em, String genero){
        String jpql = "SELECT new DTO.EstudianteDTO(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu) " +
                "FROM Estudiante e "+
                "WHERE e.genero = :genero";
        TypedQuery<EstudianteDTO> query = em.createQuery(jpql, EstudianteDTO.class);
        query.setParameter("genero", genero);
        return query.getResultList();
    }

    public static List<EstudianteDTO> obtenerEstudiantePorCarreraYCiudad (EntityManager em, String nombre, String ciudad){
        String jpql = "SELECT new DTO.EstudianteDTO(e.nombre, e.apellido, e.edad, e.genero, e.dni, e.ciudad, e.lu) " +
                "FROM EstudianteCarrera ec " +
                "JOIN ec.estudiante e " +
                "JOIN ec.carrera c " +
                "WHERE c.nombre = :nombre AND e.ciudad = :ciudad";
        TypedQuery<EstudianteDTO> query = em.createQuery(jpql, EstudianteDTO.class);
        query.setParameter("nombre", nombre);
        query.setParameter("ciudad", ciudad);
        return query.getResultList();
    }
}