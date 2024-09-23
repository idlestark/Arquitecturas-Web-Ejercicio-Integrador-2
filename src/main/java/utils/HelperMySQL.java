package utils;
//ENTIDADES
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class HelperMySQL {

    private Connection conn;

    public HelperMySQL() {

        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/MySQL_INTEGRADOR2";
        String user = "root";
        String password = "admin";

        try{
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        try{
            conn = DriverManager.getConnection(uri, user, password);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //CERRAR LA CONEXIÓN
    public void closeConnection(){
        if(conn != null){
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //CREACIÓN DE TODAS LAS TABLAS
    public void createTables() throws SQLException {
        //ESTUDIANTE
        String tablaEstudiante = "CREATE TABLE IF NOT EXISTS Estudiante (dni INT, nombre VARCHAR(50), apellido VARCHAR(50), edad INT, genero VARCHAR(50), idCiudad INT, libreta INT," +
                                "CONSTRAINT dni PRIMARY KEY (dni))";

        this.conn.prepareStatement(tablaEstudiante).execute();
        this.conn.commit();

        //CARRERA
        String tablaCarrera = "CREATE TABLE IF NOT EXISTS Carrera (idCarrera INT NOT NULL, nombre VARCHAR(50)," +
                            "CONSTRAINT idCarrera PRIMARY KEY (idCarrera))";
        this.conn.prepareStatement(tablaCarrera).execute();
        this.conn.commit();

    }

    //LLENADO DE TABLAS DE LA BASE DE DATOS
    public void populateDB() throws IOException {
        //CLIENTE terminar...
        String query = "INSERT ";

        //FACTURAS OREADAS
        for(CSVRecord row: getData("facturas.CSV")){
            if(row.size() >= 4){
                String idFactura = row.get(0);
                String idCliente = row.get(1);
                if(!idFactura.isEmpty() && !idCliente.isEmpty()){
                    try{
                        int idFacturaInt = Integer.parseInt(idFactura);
                        int idClienteInt = Integer.parseInt(idCliente);
                        Factura factura = new Factura(idFacturaInt, idClienteInt);
                        insertFactura(factura, conn);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        System.out.println("Facturas insertadas con éxito)");
        //FACTURAS PRODUCTO
        for (CSVRecord row: getData("facturas-productos.CSV")){
            if(row.size() >= 4){
                String idFactura = row.get(0);
                String idProducto = row.get(1);
                String cantidad = row.get(2);
                if(!idFactura.isEmpty() && !idProducto.isEmpty() && !cantidad.isEmpty()){
                    try{
                        int idFacturaInt = Integer.parseInt(idFactura);
                        int idClienteInt = Integer.parseInt(idProducto);
                        int cantidadInt = Integer.parseInt(cantidad);
                        FacturaProducto fp = new FacturaProducto(idFacturaInt, idClienteInt, cantidadInt);
                        insertFacturaProducto(fp, conn);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        System.out.println("Facturas-productos insertados con éxito");
        //PRODUCTOS
        for(CSVRecord row: getData("productos.CSV")){
            if(row.size() >= 4){
                String idFactura = row.get(0);
                String nombre = row.get(1);
                String valor = row.get(2);
                if(!idFactura.isEmpty() && !nombre.isEmpty() && valor.isEmpty()){
                    try{
                        int idFacturaInt = Integer.parseInt(idFactura);
                        float valorInt = Float.parseFloat(valor);
                        Producto producto = new Producto(idFacturaInt, nombre, valorInt);
                        insertProducto(producto, conn);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        System.out.println("Productos insertados con éxito");
    }


    //INSERTAR CLIENTE A LA BASE DE DATOS
    private int insertCliente(Cliente c, Connection conn)throws Exception{
        String query = "INSERT INTO cliente(idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, c.getIdCliente());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getEmail());
            if(ps.executeUpdate() == 0){
                throw new Exception("Error en la inserción");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closePsConnection(conn, ps);
        }
        return 0;
    }


    //INSERTAR FACTURA A LA BASE DE DATOS
    private int insertFactura(Factura f, Connection conn)throws Exception{
        String query = "INSERT INTO factura(idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, f.getIdFactura());
            ps.setInt(2, f.getIdCliente());
            if(ps.executeUpdate() == 0){
                throw new Exception("Error en la inserción");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closePsConnection(conn, ps);
        }
        return 0;
    }


    //INSERTAR FACTURA-PRODUCTO A LA BASE DE DATOS
    private int insertFacturaProducto(FacturaProducto fp, Connection conn)throws Exception{
        String query = "INSERT INTO factura-producto(idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, fp.getIdFactura());
            ps.setInt(2, fp.getIdProducto());
            ps.setInt(3, fp.getCantidad());
            if(ps.executeUpdate() == 0){
                throw new Exception("Error en la inserción");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closePsConnection(conn, ps);
        }
        return 0;
    }


    //INSERTAR PRODUCTO A LA BASE DE DATOS
    private int insertProducto(Producto p, Connection conn)throws Exception{
        String query = "INSERT INTO Producto(idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, p.getIdProducto());
            ps.setString(2, p.getNombre());
            ps.setFloat(3, p.getValor());
            if(ps.executeUpdate() == 0){
                throw new Exception("Error en la inserción");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closePsConnection(conn, ps);
        }
        return 0;
    }


    //CERRAR CONEXIÓN Y PREPARED STATEMENT
    private void closePsConnection(Connection conn, PreparedStatement ps){
        if(conn != null){
            try{
                ps.close();
                conn.commit();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //BORRAR TODAS LAS TABLAS DE LA BASE DE DATOS
    public void dropTables() throws SQLException {
        //CLIENTE
        String dropCliente = "DROP TABLE IF EXISTS Cliente";
        this.conn.prepareStatement(dropCliente).execute();
        this.conn.commit();
        //FACTURA
        String dropFactura = "DROP TABLE IF EXISTS Factura";
        this.conn.prepareStatement(dropFactura).execute();
        this.conn.commit();
        //FACTURA PRODUCTO
        String dropFacturaP = "DROP TABLE IF EXISTS Factura_producto";
        this.conn.prepareStatement(dropFacturaP).execute();
        this.conn.commit();
        //PRODUCTO
        String dropProducto = "DROP TABLE IF EXISTS Producto";
        this.conn.prepareStatement(dropProducto).execute();
        this.conn.commit();
    }

}
