package com.example.currencyexchange;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://brasilapi.com.br/api/cambio/v1/";
    @GET("moedas")
    Call<List<Moeda>> getMoedasDisponiveis();

    @GET("cotacao/{moeda}/{data}")
    Call<CotacaoResponse> getCotacaoPorData(
            @Path("moeda") String moeda,
            @Path("data") String data
    );
}

