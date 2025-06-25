package com.example.currencyexchange;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class CotacaoSalvaAdapter extends BaseAdapter {

    private Context context;
    private List<MoedaDataResumo> lista;

    public CotacaoSalvaAdapter(Context context, List<MoedaDataResumo> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        MoedaDataResumo resumo = lista.get(pos);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cotacao_item, parent, false);
        }

        TextView titulo = convertView.findViewById(R.id.textTituloCotacao);
        Button btnVer = convertView.findViewById(R.id.btnVerCotacao);
        Button btnExcluir = convertView.findViewById(R.id.btnExcluirCotacao);

        String idVisual = resumo.simboloMoeda + " - " + resumo.dataCotacao;
        titulo.setText(idVisual);

        btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalheCotacaoSalvaActivity.class);
            intent.putExtra("simbolo_moeda", resumo.simboloMoeda);
            intent.putExtra("data_cotacao", resumo.dataCotacao);
            context.startActivity(intent);
        });

        btnExcluir.setOnClickListener(v -> {
            new Thread(() -> {
                AppDatabase.getInstance(context).cotacaoDao()
                        .excluirPorMoedaEData(resumo.simboloMoeda, resumo.dataCotacao);

                ((CotacoesSalvasActivity) context).runOnUiThread(() -> {
                    lista.remove(pos);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Cotação excluída!", Toast.LENGTH_SHORT).show();
                });
            }).start();
        });

        return convertView;
    }
}

