package repositories;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public interface InterfaceRepository<T> {
    Object select(EntityManager em, int id);
    void insert(EntityManager em, Object o) throws SQLException;
    void update(EntityManager em, Object o, int id);
    void delete(EntityManager em, int id);
    Object find(EntityManager em, int id);
}
