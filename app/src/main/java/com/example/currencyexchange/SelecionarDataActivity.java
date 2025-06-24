package com.example.currencyexchange;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import java.util.Calendar;

public class SelecionarDataActivity extends AppCompatActivity {

    private TextView textMoedaSelecionada;
    private EditText inputData;
    private String simboloMoeda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_data);

        textMoedaSelecionada = findViewById(R.id.textMoedaSelecionada);
        inputData = findViewById(R.id.inputData);

        simboloMoeda = getIntent().getStringExtra("simbolo_moeda");

        textMoedaSelecionada.setText("Moeda: " + simboloMoeda);

        inputData.setOnClickListener(v -> showDatePicker());

        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> {
            String dataSelecionada = inputData.getText().toString();

            if (dataSelecionada.isEmpty()) {
                Toast.makeText(this, "Selecione uma data", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, CambioActivity.class);
            intent.putExtra("simbolo_moeda", simboloMoeda);
            intent.putExtra("data_cotacao", dataSelecionada);
            startActivity(intent);
        });

    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, y, m, d) -> {
            String dataFormatada = String.format("%04d-%02d-%02d", y, m + 1, d);
            inputData.setText(dataFormatada);
        }, year, month, day).show();
    }
}

