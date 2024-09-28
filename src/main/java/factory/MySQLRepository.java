package factory;
import repositories.entitiesRepository.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLRepository extends AbstractFactory {

    private static MySQLRepository instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/MySQL_INTEGRADOR2";
    public static Connection conn;

    private MySQLRepository(){}

    //SINGLETON
    public static synchronized MySQLRepository getInstance() {
        if(instance == null){
            instance = new MySQLRepository();
        }
        return instance;
    }

    public static Connection createConnection() {
        if (conn != null) {
            return conn;
        }
        String driver = DRIVER;
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 NoSuchMethodException | SecurityException | ClassNotFoundException |
                 InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(){
        try{
            conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EstudianteRepository getEstudianteRepository() {
        return new EstudianteRepository();
    }

    @Override
    public CarreraRepository getCarreraRepository() {
        return new CarreraRepository();
    }

    @Override
    public EstudianteCarreraRepository getEstudianteCarreraRepository() {
        return new EstudianteCarreraRepository();
    }
}
