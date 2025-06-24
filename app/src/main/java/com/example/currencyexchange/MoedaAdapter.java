package com.example.currencyexchange;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class MoedaAdapter extends ArrayAdapter<Moeda> {

    private Context context;
    private List<Moeda> lista;

    public MoedaAdapter(Context context, List<Moeda> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Moeda moeda = lista.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.moeda_item, parent, false);
        }

        TextView simbolo = convertView.findViewById(R.id.textSimbolo);
        TextView nome = convertView.findViewById(R.id.textNome);
        TextView tipo = convertView.findViewById(R.id.textTipo);
        Button btnVerCambio = convertView.findViewById(R.id.btnVerCambio);

        simbolo.setText(moeda.getSimbolo());
        nome.setText(moeda.getNome());
        tipo.setText("Tipo: " + moeda.getTipo_moeda());

        btnVerCambio.setOnClickListener(v -> {
            Intent intent = new Intent(context, SelecionarDataActivity.class);
            intent.putExtra("simbolo_moeda", moeda.getSimbolo());
            context.startActivity(intent);
        });

        return convertView;
    }
}

