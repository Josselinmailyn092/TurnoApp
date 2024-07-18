package com.example.turnos_espera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class activity_main_dos extends AppCompatActivity {

    Button btn2Act1;
    TextView textViewA2;
    TextView prueb;
    Button avanceT;
    int contador;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_dos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        avanceT = findViewById(R.id.avanceTurno);
        btn2Act1 = findViewById(R.id.btn2Act1);
        textViewA2 = findViewById(R.id.textView2);
        String turn = getIntent().getStringExtra("turn1");
        String contMain1=getIntent().getStringExtra("count");
        assert contMain1 != null;
        int contComparacion=Integer.parseInt(contMain1);

        textViewA2.setText(turn);

        // Recuperar el contador de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("contador", Context.MODE_PRIVATE);
        contador = sharedPreferences.getInt("contador", 0); // valor por defecto es 0 si no existe

    }

    public int sumTurn() {
        contador++;
        return contador;
    }

    public int Turn() {
        return contador;
    }

    public void completarTurn(View v) {
        int newCount = Turn();
        String contMain1=getIntent().getStringExtra("comparacion");
        int contComparacion=Integer.parseInt(contMain1);
        // Actualizar el TextView con el nuevo valor del contador
        if(newCount<contComparacion) {
            newCount=sumTurn();
            String turn = String.format(Locale.getDefault(), "A%03d", newCount);
            textViewA2.setText(turn);
            // Incrementar el turno actual en MainActivity
            Intent intent = new Intent();
            intent.putExtra("contadorNuevo", newCount);
            setResult(RESULT_OK, intent);
            guardarTurno();
        }else{
            textViewA2.setText("No hay más turnos");

        }


    }

    public void guardarTurno() {
        int cont = Turn();
        SharedPreferences sharedPreferences = getSharedPreferences("contador", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("contador", cont);
        editor.commit();
    }

    public void CambioAct1(View v) {
        String turn = String.format(Locale.getDefault(), "A%03d", contador);
        String num = String.valueOf(contador);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("contadorNuevo", turn);
        intent.putExtra("ultimoAprobado", num);
        startActivity(intent);
        finish();
    }

    public void limpiarMainActivity(View v) {
        contador = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("contador", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("contador", contador);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("reset", true);
        startActivity(intent);
    }


}