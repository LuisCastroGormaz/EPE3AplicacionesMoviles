package Models;

// Modelo: representa a un equipo de fútbol
public class Equipo {

    private int id;
    private String nombre;

    public Equipo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // El Spinner usa toString() para mostrar cada equipo
    @Override
    public String toString() {
        return nombre;
    }
}