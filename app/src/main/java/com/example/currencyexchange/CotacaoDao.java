package com.example.currencyexchange;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CotacaoDao {
    @Insert
    void inserirTodas(List<CotacaoEntity> cotacoes);

    //@Query("SELECT * FROM cotacoes ORDER BY dataCotacao DESC")
    //List<CotacaoEntity> buscarTodas();

    @Query("SELECT DISTINCT simboloMoeda, dataCotacao FROM cotacoes ORDER BY dataCotacao DESC")
    List<MoedaDataResumo> buscarResumos();


    @Query("DELETE FROM cotacoes WHERE simboloMoeda = :moeda AND dataCotacao = :data")
    void excluirPorMoedaEData(String moeda, String data);

    @Query("SELECT * FROM cotacoes WHERE simboloMoeda = :moeda AND dataCotacao = :data ORDER BY tipoBoletim")
    List<CotacaoEntity> buscarPorMoedaEData(String moeda, String data);


}
