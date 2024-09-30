package repositories.entitiesRepository;
import DTO.EstudianteCarreraDTO;
import entities.Estudiante;
import entities.EstudianteCarrera;
import repositories.InterfaceRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.List;

public class EstudianteCarreraRepository implements InterfaceRepository<EstudianteCarrera> {

    public EstudianteCarreraRepository(){

    }

    @Override
    public Object select (EntityManager em, int id) {
        String jpql = "SELECT ec FROM EstudianteCarrera ec WHERE ec.id = :id";
        TypedQuery<EstudianteCarrera> query = em.createQuery(jpql, EstudianteCarrera.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public void insert (EntityManager em, Object est) {
        if(est instanceof EstudianteCarrera){
            em.persist(est);
        }
    }

    @Override
    public void update (EntityManager em, Object estC, int id) {
        if(estC instanceof EstudianteCarrera) {
            String jpql = "UPDATE EstudianteCarrera ec SET ec.estudiante = :estudiante, ec.carrera = :carrera, ec.fechaInscripto = :antiguedad WHERE ec.id = :id";

            EstudianteCarrera estudianteCarreraActualizado = (EstudianteCarrera) estC;

            Query query = em.createQuery(jpql);
            query.setParameter("estudiante", estudianteCarreraActualizado.getEstudiante());
            query.setParameter("carrera", estudianteCarreraActualizado.getCarrera());
            query.setParameter("antiguedad", estudianteCarreraActualizado.getFechaInscripto());
            query.setParameter("id", id);

            query.executeUpdate();
        }
    }

    @Override
    public void delete(EntityManager em, int id) {
        String jpql = "DELETE FROM EstudianteCarrera ec WHERE ec.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }


    @Override
    public Object find(EntityManager em, int id) {
        String jpql = "SELECT ec FROM EstudianteCarrera ec WHERE ec.id = :id";
        TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }


    public static List<EstudianteCarreraDTO> reporteCarreras(EntityManager em) {

        String jpql = "SELECT new DTO.EstudianteCarreraDTO(c.nombre, YEAR(ec.fechaInscripto), COUNT(ec.estudiante.id), SUM(CASE WHEN ec.egresado = TRUE THEN 1 ELSE 0 END)) " +
                "FROM EstudianteCarrera ec " +
                "JOIN ec.carrera c " +
                "JOIN ec.estudiante e " +
                "GROUP BY c.nombre, YEAR(ec.fechaInscripto)";

        TypedQuery<EstudianteCarreraDTO> query = em.createQuery(jpql, EstudianteCarreraDTO.class);
        List<EstudianteCarreraDTO> resultados = query.getResultList();

        resultados.sort(Comparator.comparing(EstudianteCarreraDTO::getAnio));

        return resultados;
    }

}
