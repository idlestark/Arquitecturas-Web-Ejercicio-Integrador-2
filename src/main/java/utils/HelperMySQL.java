package utils;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class HelperMySQL {

    private Connection conn;

    public HelperMySQL() {

        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/MySQL_INTEGRADOR2";
        String user = "root";
        String password = "admin";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        try {
            conn = DriverManager.getConnection(uri, user, password);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //LLENADO DE TABLAS DE LA BASE DE DATOS
    public void populateDB() throws SQLException {

        System.out.println("Llenando tablas...");

        String insertEstudiante = "INSERT INTO Estudiante (dni, nombre, apellido, edad, genero, ciudad, lu) VALUES" +
                "(40111333, 'JUAN', 'BECKHAM', 22, 'MASCULINO', 'CONCORDIA', 9), " +
                "(40999222, 'MARCELO', 'PRADOS', 32, 'MASCULINO', 'BUENOS AIRES', 25), " +
                "(41123366, 'JUANA', 'PRATT', 16, 'FEMENINO', 'CÓRDOBA', 70), " +
                "(43667112, 'LIONEL', 'MESSI', 37, 'MASCULINO', 'TANDIL', 11), " +
                "(18723011, 'FELIPE', 'RAMIREZ', 16, 'FEMENINO', 'BAHÍA BLANCA', 21), " +
                "(40125333, 'FLORENCIA', 'FERNANDEZ', 16, 'FEMENINO', 'LOS ANTIGUOS', 87), " +
                "(40754401, 'FRANCO', 'SENILLOSA', 24, 'MASCULINO','SAN LUIS', 76), " +
                "(23456881, 'JULIANA', 'JURADO', 25, 'FEMENINO', 'LOBERÍA', 56), " +
                "(44377943, 'IGNACIO', 'AGÜERA', 21, 'MASCULINO', 'TANDIL', 91), " +
                "(43795577, 'IÑAKI', 'MARTINEZ', 22, 'MASCULINO', 'TANDIL', 92), " +
                "(43972532, 'LUCA', 'IBARRA', 22, 'MASCULINO', 'TANDIL', 69), " +
                "(20111555, 'NICOLAS', 'ALVAREZ', 24, 'FEMENINO', 'TRENQUE LAUQUEN', 20)";
        this.conn.prepareStatement(insertEstudiante).execute();
        this.conn.commit();

        String insertCarrera = "INSERT INTO Carrera (idCarrera, nombre) VALUES" +
                "(100, 'Ingeniería en sistemas'), " +
                "(101, 'TUDAI'), " +
                "(102, 'TUARI'), " +
                "(103, 'Profesorado de matemática'), " +
                "(104, 'Licenciatura en ciencias matemáticas'), " +
                "(105, 'Profesorado de física'), " +
                "(106, 'Doctorado en ciencias de la computación'), " +
                "(107, 'Doctorado en imágenes médicas'), " +
                "(108, 'Doctorado en matemática computacional e industrial'), " +
                "(109, 'Profesorado de informática '), " +
                "(110, 'Analista universitario en monitoreo del ambiente')";
        this.conn.prepareStatement(insertCarrera).execute();
        this.conn.commit();

        String insertEstudianteCarrera = "INSERT INTO EstudianteCarrera (estudiante_dni, carrera_idCarrera, fechaInscripto, egresado) VALUES " +
                "(40111333, 101, '2023-03-23', 0), " +
                "(40999222, 108, '2017-05-16', 1), " +
                "(41123366, 109, '2024-01-05', 0), " +
                "(40125333, 103, '2018-12-27', 1), " +
                "(18723011, 110, '2020-05-20', 1), " +
                "(43667112, 104, '2010-01-21', 0), " +
                "(23456881, 107, '2016-04-21', 1), " +
                "(40754401, 109, '2016-04-21', 1), " +
                "(20111555, 105, '2005-03-23', 0)," +
                "(44377943, 101, '2022-03-15', 0)," +
                "(43795577, 101, '2022-03-16', 0)," +
                "(43972532, 101, '2022-04-07', 0)";
        this.conn.prepareStatement(insertEstudianteCarrera).execute();
        this.conn.commit();

    }

    //CERRAR CONEXIÓN Y PREPARED STATEMENT
    private void closePsConnection(Connection conn, PreparedStatement ps) {

        if (conn != null) {
            try {
                ps.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //BORRAR TODAS LAS TABLAS DE LA BASE DE DATOS
    public void dropTables() throws SQLException {

        //FACTURA ESTUDIANTE CARRERA
        String dropEstudianteCarrera = "DROP TABLE IF EXISTS EstudianteCarrera";
        this.conn.prepareStatement(dropEstudianteCarrera).execute();
        this.conn.commit();

        //CARRERA
        String dropCarrera = "DROP TABLE IF EXISTS Carrera";
        this.conn.prepareStatement(dropCarrera).execute();
        this.conn.commit();

        //ESTUDIANTE
        String dropEstudiante = "DROP TABLE IF EXISTS Estudiante";
        this.conn.prepareStatement(dropEstudiante).execute();
        this.conn.commit();
    }

    //CERRAR LA CONEXIÓN
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
