package Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "usuarios.db";
    private static final int DB_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablasFutbol(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            crearTablasFutbol(db);
        }
    }

    private void crearTablasFutbol(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE equipos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");
        db.execSQL("CREATE TABLE jugadores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, " +
                "posicion TEXT NOT NULL, numero INTEGER NOT NULL, " +
                "id_equipo INTEGER NOT NULL REFERENCES equipos(id) ON DELETE CASCADE)");
        db.execSQL("CREATE INDEX jugadores_id_equipo ON jugadores(id_equipo)");
        db.execSQL("CREATE TABLE entrenadores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)");
        db.execSQL("CREATE TABLE estadios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, ciudad TEXT NOT NULL)");
        db.execSQL("CREATE TABLE partidos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT NOT NULL, " +
                "equipo_local TEXT NOT NULL, equipo_visita TEXT NOT NULL)");
    }

    public long insertarEquipo(Equipo equipo) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", equipo.getNombre());
        return getWritableDatabase().insert("equipos", null, valores);
    }

    public int eliminarEquipo(int id) {
        return getWritableDatabase().delete("equipos", "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Equipo> listarEquipos() {
        List<Equipo> resultado = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().query("equipos",
                new String[]{"id", "nombre"}, null, null, null, null, "id")) {
            while (cursor.moveToNext()) {
                resultado.add(new Equipo(cursor.getInt(0), cursor.getString(1)));
            }
        }
        return resultado;
    }

    public long insertarJugador(Jugador jugador) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", jugador.getNombre());
        valores.put("posicion", jugador.getPosicion());
        valores.put("numero", jugador.getNumero());
        valores.put("id_equipo", jugador.getIdEquipo());
        return getWritableDatabase().insert("jugadores", null, valores);
    }

    public int eliminarJugador(int id) {
        return getWritableDatabase().delete("jugadores", "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Jugador> listarJugadores() {
        List<Jugador> resultado = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT j.id, j.nombre, j.posicion, j.numero, j.id_equipo, e.nombre " +
                        "FROM jugadores j JOIN equipos e ON j.id_equipo = e.id ORDER BY j.id", null)) {
            while (cursor.moveToNext()) {
                Jugador jugador = new Jugador(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                jugador.setNombreEquipo(cursor.getString(5));
                resultado.add(jugador);
            }
        }
        return resultado;
    }

    public long insertarEntrenador(Entrenador entrenador) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", entrenador.getNombre());
        return getWritableDatabase().insert("entrenadores", null, valores);
    }

    public int eliminarEntrenador(int id) {
        return getWritableDatabase().delete("entrenadores", "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Entrenador> listarEntrenadores() {
        List<Entrenador> resultado = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().query("entrenadores",
                new String[]{"id", "nombre"}, null, null, null, null, "id")) {
            while (cursor.moveToNext()) {
                resultado.add(new Entrenador(cursor.getInt(0), cursor.getString(1)));
            }
        }
        return resultado;
    }

    public long insertarEstadio(Estadio estadio) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", estadio.getNombre());
        valores.put("ciudad", estadio.getCiudad());
        return getWritableDatabase().insert("estadios", null, valores);
    }

    public int eliminarEstadio(int id) {
        return getWritableDatabase().delete("estadios", "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Estadio> listarEstadios() {
        List<Estadio> resultado = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().query("estadios",
                new String[]{"id", "nombre", "ciudad"}, null, null, null, null, "id")) {
            while (cursor.moveToNext()) {
                resultado.add(new Estadio(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }
        }
        return resultado;
    }

    public long insertarPartido(Partido partido) {
        ContentValues valores = new ContentValues();
        valores.put("fecha", partido.getFecha());
        valores.put("equipo_local", partido.getEquipoLocal());
        valores.put("equipo_visita", partido.getEquipoVisita());
        return getWritableDatabase().insert("partidos", null, valores);
    }

    public int eliminarPartido(int id) {
        return getWritableDatabase().delete("partidos", "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Partido> listarPartidos() {
        List<Partido> resultado = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().query("partidos",
                new String[]{"id", "fecha", "equipo_local", "equipo_visita"}, null, null, null, null, "id")) {
            while (cursor.moveToNext()) {
                resultado.add(new Partido(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }
        return resultado;
    }
}
