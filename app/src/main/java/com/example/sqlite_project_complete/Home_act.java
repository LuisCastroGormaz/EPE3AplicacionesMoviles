package com.example.sqlite_project_complete;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import Models.DatabaseHelper;
import Models.Entrenador;
import Models.Equipo;
import Models.Estadio;
import Models.Jugador;
import Models.Partido;

public class Home_act extends AppCompatActivity {

    DatabaseHelper helper;
    Spinner spEquipo;
    List<Equipo> equipos = new ArrayList<>();   // equipos que muestra el Spinner

    // TextView donde se muestran los datos de cada clase
    TextView tvJugadores, tvEquipos, tvEntrenadores, tvEstadios, tvPartidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helper = new DatabaseHelper(this);

        spEquipo = findViewById(R.id.spEquipo);
        tvJugadores = findViewById(R.id.tvListaJugador);
        tvEquipos = findViewById(R.id.tvListaEquipo);
        tvEntrenadores = findViewById(R.id.tvListaEntrenador);
        tvEstadios = findViewById(R.id.tvListaEstadio);
        tvPartidos = findViewById(R.id.tvListaPartido);

        cargarSpinner();

        configurarJugador();
        configurarEquipo();
        configurarEntrenador();
        configurarEstadio();
        configurarPartido();
    }

    // ===================== JUGADOR =====================
    private void configurarJugador() {
        EditText etNombre = findViewById(R.id.etNombreJugador);
        EditText etPosicion = findViewById(R.id.etPosicion);
        EditText etNumero = findViewById(R.id.etNumero);
        EditText etId = findViewById(R.id.etIdJugador);

        // ---------- AÑADIR ----------
        findViewById(R.id.btnAddJugador).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String posicion = etPosicion.getText().toString().trim();
            int numero = leerNumero(etNumero);

            // Valida que los datos obligatorios estén completos
            if (nombre.isEmpty() || posicion.isEmpty() || etNumero.getText().toString().trim().isEmpty()) {
                toast("Completa nombre, posición y número");
                return;
            }
            if (numero < 0) {
                toast("Número de camiseta inválido");
                return;
            }
            if (equipos.isEmpty()) {
                toast("Primero añade un equipo");
                return;
            }

            Equipo equipo = (Equipo) spEquipo.getSelectedItem();
            Jugador j = new Jugador(0, nombre, posicion, numero, equipo.getId());

            if (helper.insertarJugador(j) != -1) {
                toast("Jugador añadido correctamente");
                etNombre.setText("");
                etPosicion.setText("");
                etNumero.setText("");
                mostrarJugadores();
            } else {
                toast("Error al guardar el jugador");
            }
        });

        // ---------- ELIMINAR ----------
        findViewById(R.id.btnDeleteJugador).setOnClickListener(v -> {
            int id = leerNumero(etId);
            if (id < 0) {
                toast("Escribe el ID del jugador a eliminar");
                return;
            }
            if (helper.eliminarJugador(id) > 0) {
                toast("Jugador eliminado");
                etId.setText("");
                mostrarJugadores();
            } else {
                toast("No existe un jugador con ese ID");
            }
        });

        // ---------- MOSTRAR ----------
        findViewById(R.id.btnShowJugador).setOnClickListener(v -> mostrarJugadores());
    }

    private void mostrarJugadores() {
        StringBuilder sb = new StringBuilder();
        for (Jugador j : helper.listarJugadores()) {
            sb.append("ID ").append(j.getId())
                    .append(" | ").append(j.getNombre())
                    .append(" | ").append(j.getPosicion())
                    .append(" | #").append(j.getNumero())
                    .append(" | ").append(j.getNombreEquipo())
                    .append("\n");
        }
        tvJugadores.setText(sb.length() == 0 ? "No hay jugadores registrados" : sb.toString());
    }

    // ===================== EQUIPO =====================
    private void configurarEquipo() {
        EditText etNombre = findViewById(R.id.etNombreEquipo);
        EditText etId = findViewById(R.id.etIdEquipo);

        findViewById(R.id.btnAddEquipo).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                toast("Escribe el nombre del equipo");
                return;
            }
            if (helper.insertarEquipo(new Equipo(0, nombre)) != -1) {
                toast("Equipo añadido correctamente");
                etNombre.setText("");
                cargarSpinner();     // actualiza el menú de equipos del jugador
                mostrarEquipos();
            } else {
                toast("Error al guardar el equipo");
            }
        });

        findViewById(R.id.btnDeleteEquipo).setOnClickListener(v -> {
            int id = leerNumero(etId);
            if (id < 0) {
                toast("Escribe el ID del equipo a eliminar");
                return;
            }
            if (helper.eliminarEquipo(id) > 0) {
                toast("Equipo eliminado (y sus jugadores)");
                etId.setText("");
                cargarSpinner();
                mostrarEquipos();
                mostrarJugadores();  // sus jugadores también se borraron
            } else {
                toast("No existe un equipo con ese ID");
            }
        });

        findViewById(R.id.btnShowEquipo).setOnClickListener(v -> mostrarEquipos());
    }

    private void mostrarEquipos() {
        StringBuilder sb = new StringBuilder();
        for (Equipo e : helper.listarEquipos()) {
            sb.append("ID ").append(e.getId()).append(" | ").append(e.getNombre()).append("\n");
        }
        tvEquipos.setText(sb.length() == 0 ? "No hay equipos registrados" : sb.toString());
    }

    // ===================== ENTRENADOR =====================
    private void configurarEntrenador() {
        EditText etNombre = findViewById(R.id.etNombreEntrenador);
        EditText etId = findViewById(R.id.etIdEntrenador);

        findViewById(R.id.btnAddEntrenador).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                toast("Escribe el nombre del entrenador");
                return;
            }
            if (helper.insertarEntrenador(new Entrenador(0, nombre)) != -1) {
                toast("Entrenador añadido correctamente");
                etNombre.setText("");
                mostrarEntrenadores();
            } else {
                toast("Error al guardar el entrenador");
            }
        });

        findViewById(R.id.btnDeleteEntrenador).setOnClickListener(v -> {
            int id = leerNumero(etId);
            if (id < 0) {
                toast("Escribe el ID del entrenador a eliminar");
                return;
            }
            if (helper.eliminarEntrenador(id) > 0) {
                toast("Entrenador eliminado");
                etId.setText("");
                mostrarEntrenadores();
            } else {
                toast("No existe un entrenador con ese ID");
            }
        });

        findViewById(R.id.btnShowEntrenador).setOnClickListener(v -> mostrarEntrenadores());
    }

    private void mostrarEntrenadores() {
        StringBuilder sb = new StringBuilder();
        for (Entrenador e : helper.listarEntrenadores()) {
            sb.append("ID ").append(e.getId()).append(" | ").append(e.getNombre()).append("\n");
        }
        tvEntrenadores.setText(sb.length() == 0 ? "No hay entrenadores registrados" : sb.toString());
    }

    // ===================== ESTADIO =====================
    private void configurarEstadio() {
        EditText etNombre = findViewById(R.id.etNombreEstadio);
        EditText etCiudad = findViewById(R.id.etCiudadEstadio);
        EditText etId = findViewById(R.id.etIdEstadio);

        findViewById(R.id.btnAddEstadio).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String ciudad = etCiudad.getText().toString().trim();
            if (nombre.isEmpty() || ciudad.isEmpty()) {
                toast("Completa nombre y ciudad del estadio");
                return;
            }
            if (helper.insertarEstadio(new Estadio(0, nombre, ciudad)) != -1) {
                toast("Estadio añadido correctamente");
                etNombre.setText("");
                etCiudad.setText("");
                mostrarEstadios();
            } else {
                toast("Error al guardar el estadio");
            }
        });

        findViewById(R.id.btnDeleteEstadio).setOnClickListener(v -> {
            int id = leerNumero(etId);
            if (id < 0) {
                toast("Escribe el ID del estadio a eliminar");
                return;
            }
            if (helper.eliminarEstadio(id) > 0) {
                toast("Estadio eliminado");
                etId.setText("");
                mostrarEstadios();
            } else {
                toast("No existe un estadio con ese ID");
            }
        });

        findViewById(R.id.btnShowEstadio).setOnClickListener(v -> mostrarEstadios());
    }

    private void mostrarEstadios() {
        StringBuilder sb = new StringBuilder();
        for (Estadio e : helper.listarEstadios()) {
            sb.append("ID ").append(e.getId())
                    .append(" | ").append(e.getNombre())
                    .append(" | ").append(e.getCiudad())
                    .append("\n");
        }
        tvEstadios.setText(sb.length() == 0 ? "No hay estadios registrados" : sb.toString());
    }

    // ===================== PARTIDO =====================
    private void configurarPartido() {
        EditText etFecha = findViewById(R.id.etFechaPartido);
        EditText etLocal = findViewById(R.id.etEquipoLocal);
        EditText etVisita = findViewById(R.id.etEquipoVisita);
        EditText etId = findViewById(R.id.etIdPartido);

        findViewById(R.id.btnAddPartido).setOnClickListener(v -> {
            String fecha = etFecha.getText().toString().trim();
            String local = etLocal.getText().toString().trim();
            String visita = etVisita.getText().toString().trim();
            if (fecha.isEmpty() || local.isEmpty() || visita.isEmpty()) {
                toast("Completa fecha, equipo local y equipo visita");
                return;
            }
            if (helper.insertarPartido(new Partido(0, fecha, local, visita)) != -1) {
                toast("Partido añadido correctamente");
                etFecha.setText("");
                etLocal.setText("");
                etVisita.setText("");
                mostrarPartidos();
            } else {
                toast("Error al guardar el partido");
            }
        });

        findViewById(R.id.btnDeletePartido).setOnClickListener(v -> {
            int id = leerNumero(etId);
            if (id < 0) {
                toast("Escribe el ID del partido a eliminar");
                return;
            }
            if (helper.eliminarPartido(id) > 0) {
                toast("Partido eliminado");
                etId.setText("");
                mostrarPartidos();
            } else {
                toast("No existe un partido con ese ID");
            }
        });

        findViewById(R.id.btnShowPartido).setOnClickListener(v -> mostrarPartidos());
    }

    private void mostrarPartidos() {
        StringBuilder sb = new StringBuilder();
        for (Partido p : helper.listarPartidos()) {
            sb.append("ID ").append(p.getId())
                    .append(" | ").append(p.getFecha())
                    .append(" | ").append(p.getEquipoLocal())
                    .append(" vs ").append(p.getEquipoVisita())
                    .append("\n");
        }
        tvPartidos.setText(sb.length() == 0 ? "No hay partidos registrados" : sb.toString());
    }

    // ===================== AUXILIARES =====================

    // Llena el Spinner con los equipos guardados en la base
    private void cargarSpinner() {
        equipos = helper.listarEquipos();
        ArrayAdapter<Equipo> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEquipo.setAdapter(adapter);
    }

    // Lee un número de un EditText; devuelve -1 si está vacío o es inválido
    private int leerNumero(EditText et) {
        String t = et.getText().toString().trim();
        if (t.isEmpty()) return -1;
        try {
            return Integer.parseInt(t);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void toast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}
