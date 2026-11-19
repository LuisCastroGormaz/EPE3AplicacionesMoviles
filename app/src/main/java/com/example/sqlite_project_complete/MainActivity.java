package com.example.sqlite_project_complete;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String USUARIO_DEMO = "demo";
    private static final String CLAVE_DEMO = "demo123";

    // ---------- Carrusel sencillo ----------
    // Las 3 imágenes están en res/drawable (img1.png, img2.png, img3.png)
    private final int[] imagenes = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    private int indice = 0;          // imagen que se está mostrando
    private ImageView ivLogo;

    // Handler: programa una tarea para ejecutarla después de un tiempo
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Tarea: pasa a la siguiente imagen y se repite cada 3 segundos
    private final Runnable carrusel = new Runnable() {
        @Override
        public void run() {
            indice = (indice + 1) % imagenes.length;   // 0 -> 1 -> 2 -> 0 ...
            ivLogo.setImageResource(imagenes[indice]);
            handler.postDelayed(this, 3000);           // 3000 ms = 3 segundos
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etUser = findViewById(R.id.etUser);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        ivLogo = findViewById(R.id.ivLogo);

        // Muestra la primera imagen
        ivLogo.setImageResource(imagenes[0]);

        btnLogin.setOnClickListener(v -> {

            String user = etUser.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, R.string.completar_credenciales, Toast.LENGTH_SHORT).show();
                return;
            }

            if (USUARIO_DEMO.equals(user) && CLAVE_DEMO.equals(pass)) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Home_act.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Inicia el carrusel cuando la pantalla se ve
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(carrusel, 3000);
    }

    // Detiene el carrusel cuando la pantalla deja de verse
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(carrusel);
    }
}
