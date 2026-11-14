package com.example.sqlite_project_complete;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Models.DatabaseHelper;

public class Home_act extends AppCompatActivity {

    EditText etNewUser, etNewPass;
    TextView tvList;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etNewUser = findViewById(R.id.etNewUser);
        etNewPass = findViewById(R.id.etNewPass);
        tvList    = findViewById(R.id.tvList);
        Button btnAdd    = findViewById(R.id.btnAdd);
        Button btnShow   = findViewById(R.id.btnShow);
        Button btnDelete = findViewById(R.id.btnDelete);

        helper = new DatabaseHelper(this);

        // ---------- AÑADIR ----------
        btnAdd.setOnClickListener(v -> {
            String user = etNewUser.getText().toString().trim();
            String pass = etNewPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("username", user);
            cv.put("password", pass);
            long r = db.insert("users", null, cv);

            if (r != -1) {
                Toast.makeText(this, "Usuario añadido", Toast.LENGTH_SHORT).show();
                etNewUser.setText("");
                etNewPass.setText("");
            } else {
                Toast.makeText(this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
            }
        });




        // ---------- MOSTRAR ----------
        btnShow.setOnClickListener(v -> {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT id, username, password FROM users", null);
            StringBuilder sb = new StringBuilder();
            while (c.moveToNext()) {
                sb.append("ID: ").append(c.getInt(0))
                        .append("  |  ").append(c.getString(1))
                        .append("  |  ").append(c.getString(2))
                        .append("\n");
            }
            c.close();
            tvList.setText(sb.length() == 0 ? "No hay usuarios" : sb.toString());
        });


        // ---------- ELIMINAR ----------
        btnDelete.setOnClickListener(v -> {
            String user = etNewUser.getText().toString().trim();
            if (user.isEmpty()) {
                Toast.makeText(this, "Escribe el usuario a eliminar", Toast.LENGTH_SHORT).show();
                return;
            }
            SQLiteDatabase db = helper.getWritableDatabase();
            int filas = db.delete("users", "username=?", new String[]{user});
            if (filas > 0) {
                Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                etNewUser.setText("");
                etNewPass.setText("");
            } else {
                Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}