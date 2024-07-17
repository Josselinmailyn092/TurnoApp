package com.example.turnos_espera;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private EditText numCedula;
    private TextView tvTurnoActual;
    private TextView tvQueueList;
    private Button btnGenerarTurno;
    private Button btnNuevoTurno;
    private Button btnMostrarEstado;

    private Queue<String> cola;
    private int turnoCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        numCedula = findViewById(R.id.cedula);
        tvTurnoActual = findViewById(R.id.turno_actual);
        tvQueueList = findViewById(R.id.queueList);
        btnGenerarTurno = findViewById(R.id.generar_turno);
        btnNuevoTurno = findViewById(R.id.nuevoTurno);
        btnMostrarEstado = findViewById(R.id.mostrar_estado);

        cola = new LinkedList<>();

        btnGenerarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarTurno();
            }
        });

        btnNuevoTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoTurno();
            }
        });

        btnMostrarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarEstado();
            }
        });
    }

    private void generarTurno() {
        String cedula = numCedula.getText().toString().trim();

        if (TextUtils.isEmpty(cedula) || cedula.length() != 10) {
            Toast.makeText(this, "Ingrese un número de cédula válido de 10 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        turnoCount++;
        String turno = String.format(Locale.getDefault(), "A%03d", turnoCount);

        tvTurnoActual.setText(turno);
        cola.add(turno);
        //actualizarListaEspera();
    }

    private void nuevoTurno() {
        numCedula.setText("");
        tvTurnoActual.setText("A001");
        tvQueueList.setText("");
        cola.clear();
        turnoCount = 0;
    }

    private void mostrarEstado() {
        if (cola.isEmpty()) {
            Toast.makeText(this, "La cola está vacía.", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder estado = new StringBuilder("Estado de la cola:\n");
            for (String turno : cola) {
                estado.append(turno).append("\n");
            }
            Toast.makeText(this, estado.toString(), Toast.LENGTH_LONG).show();
        }
    }
/*
    private void actualizarListaEspera() {
        StringBuilder lista = new StringBuilder();
        for (String turno : cola) {
            lista.append(turno).append("\n");
        }
        tvQueueList.setText(lista.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_siguiente_elemento:
                verSiguienteElemento();
                return true;
            case R.id.action_vaciar_cola:
                vaciarCola();
                return true;
            case R.id.action_salir:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void verSiguienteElemento() {
        if (cola.isEmpty()) {
            Toast.makeText(this, "La cola está vacía.", Toast.LENGTH_SHORT).show();
        } else {
            String siguiente = cola.peek();
            Toast.makeText(this, "Siguiente elemento: " + siguiente, Toast.LENGTH_SHORT).show();
        }
    }

    private void vaciarCola() {
        cola.clear();
        actualizarListaEspera();
        Toast.makeText(this, "La cola ha sido vaciada.", Toast.LENGTH_SHORT).show();
    }

 */
}
