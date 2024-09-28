package DTO;

public class CarreraDTO {
    private String nombre;
    private Long cantidad;

    public CarreraDTO() {
    }

    public CarreraDTO(String nombre, Long cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "CarreraDto{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                '}' + "\n";
    }
}