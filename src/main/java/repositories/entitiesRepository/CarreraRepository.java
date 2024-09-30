package repositories.entitiesRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import DTO.CarreraDTO;
import entities.Carrera;
import repositories.InterfaceRepository;
import java.util.List;

public class CarreraRepository implements InterfaceRepository<CarreraRepository> {

    public CarreraRepository() {

    }

    @Override
    public Object select (EntityManager em, int idCarrera) {
        String jpql = "SELECT c FROM Carrera c WHERE c.idCarrera = :idCarrera";
        TypedQuery<Carrera> query = em.createQuery(jpql, Carrera.class);
        query.setParameter("idCarrera", idCarrera);

        return query.getSingleResult();
    }

    @Override
    public void insert (EntityManager em, Object est) {
        if (est instanceof Carrera){
            em.persist(est);
        }
    }

    @Override
    public void update (EntityManager em, Object carrera, int idCarrera) {

        if (carrera instanceof Carrera) {
            String jpql = "UPDATE Carrera c " +
                        "SET c.idCarrera = :idCarrera, c.nombre = :nombre " +
                        "WHERE c.idCarrera = :idCarrera";

            Carrera carreraActualizada = (Carrera) carrera;

            Query query = em.createQuery(jpql);
            query.setParameter("idCarrera", carreraActualizada.getIdCarrera());
            query.setParameter("nombre", carreraActualizada.getNombre());

            query.executeUpdate();
        }
    }

    @Override
    public void delete (EntityManager em, int idCarrera) {
        String jpql = "DELETE FROM Carrera c WHERE c.idCarrera = :idCarrera";
        Query query = em.createQuery(jpql);
        query.setParameter("idCarrera", idCarrera);
        query.executeUpdate();
    }

    @Override
    public Object find (EntityManager em, int idCarrera) {
        String jpql = "SELECT c FROM Carrera c WHERE c.idCarrera = :idCarrera";
        TypedQuery<Carrera> query = em.createQuery(jpql, Carrera.class);
        query.setParameter("idCarrera", idCarrera);
        return query.getSingleResult();
    }


    public static List<CarreraDTO> obtenerCarrerasPorEstudiantesInscriptos(EntityManager em) {
        String jpql = "SELECT new DTO.CarreraDTO(c.nombre, COUNT(ec)) " +
                "FROM Carrera c " +
                "JOIN c.estudiantes ec " +
                "WHERE ec.estudiante IS NOT NULL " +
                "GROUP BY c.nombre " +
                "ORDER BY COUNT(ec.estudiante.dni) DESC";
        TypedQuery<CarreraDTO> query = em.createQuery(jpql, CarreraDTO.class);
        return query.getResultList();
    }

}
