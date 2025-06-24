package com.example.currencyexchange;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CotacaoResponse {
    @SerializedName("cotacoes")
    private List<Cotacao> cotacoes;

    @SerializedName("moeda")
    private String moeda;

    @SerializedName("data")
    private String data;

    public List<Cotacao> getCotacoes() { return cotacoes; }
    public String getMoeda() { return moeda; }
    public String getData() { return data; }
}



