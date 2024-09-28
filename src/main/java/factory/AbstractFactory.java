package factory;

import repositories.entitiesRepository.*;

import javax.persistence.Column;

public abstract class AbstractFactory {

    public static final int MYSQL_JDBC = 1;
    public abstract EstudianteRepository getEstudianteRepository();
    public abstract CarreraRepository getCarreraRepository();
    public abstract EstudianteCarreraRepository getEstudianteCarreraRepository();

    public static AbstractFactory getRepositoryFactory(int whichFactory){
        switch(whichFactory){
            case MYSQL_JDBC : {
                return MySQLRepository.getInstance();
            }
            default: {
                return null;
            }
        }
    }
}
