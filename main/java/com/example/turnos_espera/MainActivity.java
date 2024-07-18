package com.example.turnos_espera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText numCedula;
    private TextView tvTurnoActual;

    private TextView numTurn;
    private TextView ultimTurn;

    private Queue<String> cola;
    private int turnoCount = 0;
    public int turnoAtencion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numCedula = findViewById(R.id.cedula);
        numTurn = findViewById(R.id.numT);
        tvTurnoActual = findViewById(R.id.turno_actual);
        tvTurnoActual.setText(String.valueOf(turnActual()));
        ultimTurn=findViewById(R.id.ultimoTurn);
        // Inicializar cola y turnoCount desde SharedPreferences
        cola = loadQueue();
        turnoCount = loadTurnoCount();
        ultimTurn.setText(String.format(Locale.getDefault(), "A%03d",loadTurnoCount()));

        if (getIntent().getBooleanExtra("reset", false)) {
            limpiarTurnos();
        }
    }

    public String generarTurno(View v) {
        String cedula = numCedula.getText().toString().trim();

        if (TextUtils.isEmpty(cedula) || cedula.length() != 10) {
            Toast.makeText(this, "Ingrese un número de cédula válido de 10 dígitos", Toast.LENGTH_SHORT).show();
            return null;
        }

        turnoCount++;
        guardarTurnoCount(turnoCount);

        String turn = String.format(Locale.getDefault(), "A%03d", turnoCount);
        numTurn.setText(turn);
        ultimTurn.setText(String.format(Locale.getDefault(), "A%03d", turnoCount));

        cola.add(turn);
        guardarCola();
        return turn;
    }

    public String obtenerTurnoActual() {
        if (turnoAtencion== 0) {
            return null;
        }
        return String.format(Locale.getDefault(), "A%03d", turnoAtencion);
    }
    public String turnActual() {
        Intent intent = getIntent();

        String turno = intent.getStringExtra("contadorNuevo");
        if (turno == null) {
            // Obtener el valor del contador de SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("contador", Context.MODE_PRIVATE);
            int contadorGuardado = sharedPreferences.getInt("contador", 0);
            // Formatear el turno
            turno = String.format(Locale.getDefault(), "A%03d", contadorGuardado);
        }
        return turno;
    }

    public void cambioMain(View v) {
        String turno = obtenerTurnoActual();

        Intent intent = new Intent(this, activity_main_dos.class);
        intent.putExtra("turn1", turno); // Pasar el turno actual
        intent.putExtra("count", String.valueOf(turnoAtencion)); // Pasar el contador
        intent.putExtra("comparacion",String.valueOf(loadTurnoCount()));
        startActivity(intent);

    }
    public void limpiarTurnos() {
        // Restablecer variables en memoria
        turnoCount = 0;
        turnoAtencion = 0;
        cola.clear();

        // Restablecer SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("TurnosPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Actualizar la interfaz de usuario
        numTurn.setText("");
        tvTurnoActual.setText("");

        Toast.makeText(this, "Todos los valores han sido restablecidos", Toast.LENGTH_SHORT).show();
    }

    private void guardarTurnoCount(int count) {
        SharedPreferences sharedPreferences = getSharedPreferences("TurnosPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("turnoCount", count);
        editor.apply();
    }

    private int loadTurnoCount() {
        SharedPreferences sharedPreferences = getSharedPreferences("TurnosPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("turnoCount", 0);
    }

    private void guardarCola() {
        SharedPreferences sharedPreferences = getSharedPreferences("TurnosPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(cola);
        editor.putStringSet("cola", set);
        editor.apply();
    }

    private Queue<String> loadQueue() {
        SharedPreferences sharedPreferences = getSharedPreferences("TurnosPrefs", MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("cola", new HashSet<>());
        return new LinkedList<>(set);
    }
}