package Models;

// Modelo: representa a un jugador de la cancha
public class Jugador {

    // Atributos privados (encapsulamiento)
    private int id;
    private String nombre;
    private String posicion;
    private int numero;          // número de camiseta
    private int idEquipo;        // llave foránea: equipo al que pertenece
    private String nombreEquipo; // solo para mostrar (viene del JOIN con equipos)

    // Constructor
    public Jugador(int id, String nombre, String posicion, int numero, int idEquipo) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.numero = numero;
        this.idEquipo = idEquipo;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
}
