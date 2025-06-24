package com.example.currencyexchange;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParidadeDolarActivity extends AppCompatActivity {

    private LinearLayout blocoParidade;
    private String dataSelecionada;
    private String moedaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paridade_dolar);

        blocoParidade = findViewById(R.id.blocoParidade);
        dataSelecionada = getIntent().getStringExtra("data_cotacao");
        moedaSelecionada = getIntent().getStringExtra("moeda");

        carregarParidadeUSD(dataSelecionada, moedaSelecionada);
    }

    private void carregarParidadeUSD(String data, String moedaSelecionada) {
        Call<CotacaoResponse> call = RetrofitClient.getInstance().getMyApi()
                .getCotacaoPorData(moedaSelecionada, data);

        call.enqueue(new Callback<CotacaoResponse>() {
            @Override
            public void onResponse(Call<CotacaoResponse> call, Response<CotacaoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exibirParidade(response.body().getCotacoes());
                } else {
                    Toast.makeText(ParidadeDolarActivity.this, "Erro ao carregar paridade", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CotacaoResponse> call, Throwable t) {
                Toast.makeText(ParidadeDolarActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exibirParidade(List<Cotacao> cotacoes) {
        blocoParidade.removeAllViews();

        for (Cotacao cot : cotacoes) {
            TextView tv = new TextView(this);
            tv.setText(
                    cot.getTipo_boletim() + ":\n" +
                            String.format("Paridade Compra: %.6f\n", cot.getParidade_compra()) +
                            String.format("Paridade Venda: %.6f", cot.getParidade_venda())
            );
            tv.setTextSize(16);
            tv.setPadding(0, 16, 0, 16);

            android.view.View linha = new android.view.View(this);
            linha.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1));
            linha.setBackgroundColor(Color.LTGRAY);

            blocoParidade.addView(tv);
            blocoParidade.addView(linha);
        }
    }

}
