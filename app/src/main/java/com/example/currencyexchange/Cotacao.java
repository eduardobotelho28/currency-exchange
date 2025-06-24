package com.example.currencyexchange;

public class Cotacao {
    private double paridade_compra;
    private double paridade_venda;
    private double cotacao_compra;
    private double cotacao_venda;
    private String data_hora_cotacao;
    private String tipo_boletim;

    public double getParidade_compra() { return paridade_compra; }
    public double getParidade_venda() { return paridade_venda; }
    public double getCotacao_compra() { return cotacao_compra; }
    public double getCotacao_venda() { return cotacao_venda; }
    public String getData_hora_cotacao() { return data_hora_cotacao; }
    public String getTipo_boletim() { return tipo_boletim; }
}
