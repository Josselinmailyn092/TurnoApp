package com.example.turnos_espera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private Queue<String> queue;
    private EditText nameEditText;
    private TextView currentTurnTextView;
    private TextView queueListTextView;
    private Button addTurnButton, newTurnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la cola
        queue = new LinkedList<>();

        // Inicializar las vistas
        nameEditText = findViewById(R.id.nombre);
        currentTurnTextView = findViewById(R.id.turno_actual);
        queueListTextView = findViewById(R.id.queueList);
        addTurnButton = findViewById(R.id.generar_turno);
        newTurnButton = findViewById(R.id.nuevoTurno);

        // Configurar los botones
        addTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                if (!name.isEmpty()) {
                    queue.add(name);
                    updateQueue();
                    Toast.makeText(MainActivity.this, "Turno generado: " + name, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!queue.isEmpty()) {
                    String currentTurn = queue.poll();
                    currentTurnTextView.setText(currentTurn);
                    updateQueue();
                } else {
                    Toast.makeText(MainActivity.this, "No hay turnos en la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateQueue() {
        StringBuilder builder = new StringBuilder();
        for (String name : queue) {
            builder.append(name).append("\n");
        }
        queueListTextView.setText(builder.toString());
    }
}
