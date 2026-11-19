package Models;

public class Partido {

    private int id;
    private String fecha;
    private String equipoLocal;
    private String equipoVisita;

    public Partido(int id, String fecha, String equipoLocal, String equipoVisita) {
        this.id = id;
        this.fecha = fecha;
        this.equipoLocal = equipoLocal;
        this.equipoVisita = equipoVisita;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEquipoLocal() { return equipoLocal; }
    public void setEquipoLocal(String equipoLocal) { this.equipoLocal = equipoLocal; }

    public String getEquipoVisita() { return equipoVisita; }
    public void setEquipoVisita(String equipoVisita) { this.equipoVisita = equipoVisita; }
}