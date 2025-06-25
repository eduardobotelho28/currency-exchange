package com.example.currencyexchange;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cotacoes")
public class CotacaoEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String simboloMoeda;
    private String dataCotacao;
    private String tipoBoletim;
    private double cotacaoCompra;
    private double cotacaoVenda;

    public CotacaoEntity(String simboloMoeda, String dataCotacao, String tipoBoletim,
                         double cotacaoCompra, double cotacaoVenda) {
        this.simboloMoeda = simboloMoeda;
        this.dataCotacao = dataCotacao;
        this.tipoBoletim = tipoBoletim;
        this.cotacaoCompra = cotacaoCompra;
        this.cotacaoVenda = cotacaoVenda;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSimboloMoeda() { return simboloMoeda; }
    public String getDataCotacao() { return dataCotacao; }
    public String getTipoBoletim() { return tipoBoletim; }
    public double getCotacaoCompra() { return cotacaoCompra; }
    public double getCotacaoVenda() { return cotacaoVenda; }
}
