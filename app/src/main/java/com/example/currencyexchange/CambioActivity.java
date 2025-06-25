package com.example.currencyexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioActivity extends AppCompatActivity {

    private TextView textResumo;
    private LinearLayout blocoCotacoes;
    private Button btnParidadeDolar, btnSalvarCotacao;
    private List<Cotacao> cotacoesAtuais; // nova variável global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio);

        textResumo = findViewById(R.id.textResumo);
        blocoCotacoes = findViewById(R.id.blocoCotacoes);
        btnParidadeDolar = findViewById(R.id.btnParidadeDolar);
        btnSalvarCotacao = findViewById(R.id.btnSalvarCotacao);

        String moeda = getIntent().getStringExtra("simbolo_moeda");
        String data = getIntent().getStringExtra("data_cotacao");

        textResumo.setText("Cotação da moeda " + moeda + " em " + data);

        carregarCotacao(moeda, data);

        btnParidadeDolar.setOnClickListener(v -> {
            Intent intent = new Intent(CambioActivity.this, ParidadeDolarActivity.class);
            intent.putExtra("data_cotacao", data);
            intent.putExtra("moeda", moeda);
            startActivity(intent);
        });


        btnSalvarCotacao.setOnClickListener(v -> {
            if (cotacoesAtuais != null && !cotacoesAtuais.isEmpty()) {
                List<CotacaoEntity> listaSalvar = new ArrayList<>();
                for (Cotacao cot : cotacoesAtuais) {
                    listaSalvar.add(new CotacaoEntity(
                            moeda,
                            data,
                            cot.getTipo_boletim(),
                            cot.getCotacao_compra(),
                            cot.getCotacao_venda()
                    ));
                }

                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                    db.cotacaoDao().inserirTodas(listaSalvar);

                    runOnUiThread(() -> Toast.makeText(this, "Cotação salva com sucesso!", Toast.LENGTH_SHORT).show());
                }).start();
            } else {
                Toast.makeText(this, "Nenhuma cotação para salvar.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void carregarCotacao(String moeda, String data) {
        Call<CotacaoResponse> call = RetrofitClient.getInstance().getMyApi()
                .getCotacaoPorData(moeda, data);

        call.enqueue(new Callback<CotacaoResponse>() {
            @Override
            public void onResponse(Call<CotacaoResponse> call, Response<CotacaoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cotacoesAtuais = response.body().getCotacoes();
                    exibirBlocos(cotacoesAtuais);
                }
                else {
                    Log.e("API_ERROR", "Código: " + response.code());
                    try {
                        Log.e("API_ERROR", "Erro: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CambioActivity.this, "Erro ao obter cotação", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CotacaoResponse> call, Throwable t) {
                Toast.makeText(CambioActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exibirBlocos(List<Cotacao> cotacoes) {
        blocoCotacoes.removeAllViews();

        for (Cotacao cot : cotacoes) {
            TextView tv = new TextView(this);
            tv.setText(
                    cot.getTipo_boletim() + ":\n" +
                            "Compra: " + cot.getCotacao_compra() + "\n" +
                            "Venda: " + cot.getCotacao_venda() + "\n"
            );
            tv.setPadding(0, 16, 0, 16);
            tv.setTextSize(16);
            blocoCotacoes.addView(tv);
        }
    }
}

