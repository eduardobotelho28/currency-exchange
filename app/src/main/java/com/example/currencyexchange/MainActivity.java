package com.example.currencyexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listViewMoedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewMoedas = findViewById(R.id.listViewMoedas);

        carregarMoedas();

        Button btnVerCotacoesSalvas = findViewById(R.id.btnVerCotacoesSalvas);
        btnVerCotacoesSalvas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CotacoesSalvasActivity.class);
            startActivity(intent);
        });

    }

    private void carregarMoedas() {
        Call<List<Moeda>> call = RetrofitClient.getInstance().getMyApi().getMoedasDisponiveis();
        call.enqueue(new Callback<List<Moeda>>() {
            @Override
            public void onResponse(Call<List<Moeda>> call, Response<List<Moeda>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Moeda> moedas = response.body();
                    MoedaAdapter adapter = new MoedaAdapter(MainActivity.this, moedas);
                    listViewMoedas.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Erro na resposta da API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Moeda>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
