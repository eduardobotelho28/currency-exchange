package com.example.currencyexchange;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CotacoesSalvasActivity extends AppCompatActivity {

    private ListView listViewCotacoes;
    private CotacaoSalvaAdapter adapter;
    private List<CotacaoEntity> todasCotacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotacoes_salvas);

        listViewCotacoes = findViewById(R.id.listViewCotacoes);

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<MoedaDataResumo> resumos = db.cotacaoDao().buscarResumos();

            runOnUiThread(() -> {
                CotacaoSalvaAdapter adapter = new CotacaoSalvaAdapter(this, resumos);
                listViewCotacoes.setAdapter(adapter);
            });
        }).start();

    }
}
