package com.example.currencyexchange;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DetalheCotacaoSalvaActivity extends AppCompatActivity {

    private TextView textResumo;
    private LinearLayout layoutDetalhes;
    private String simboloMoeda, dataCotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cotacao_salva);

        textResumo = findViewById(R.id.textResumo);
        layoutDetalhes = findViewById(R.id.layoutDetalhes);

        simboloMoeda = getIntent().getStringExtra("simbolo_moeda");
        dataCotacao = getIntent().getStringExtra("data_cotacao");

        textResumo.setText("Cotações salvas para " + simboloMoeda + " em " + dataCotacao);

        carregarDadosSalvos();
    }

    private void carregarDadosSalvos() {
        new Thread(() -> {
            List<CotacaoEntity> lista = AppDatabase.getInstance(this)
                    .cotacaoDao()
                    .buscarPorMoedaEData(simboloMoeda, dataCotacao);

            runOnUiThread(() -> {
                if (lista.isEmpty()) {
                    Toast.makeText(this, "Nenhuma cotação encontrada.", Toast.LENGTH_SHORT).show();
                } else {
                    for (CotacaoEntity cot : lista) {
                        TextView tv = new TextView(this);
                        tv.setText(
                                cot.getTipoBoletim() + ":\n" +
                                        "Compra: " + cot.getCotacaoCompra() + "\n" +
                                        "Venda: " + cot.getCotacaoVenda() + "\n"
                        );
                        tv.setPadding(0, 16, 0, 16);
                        tv.setTextSize(16);
                        layoutDetalhes.addView(tv);
                    }
                }
            });
        }).start();
    }
}
