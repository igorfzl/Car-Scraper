package br.edu.utfpr.td.tsi.raspador.etl;

public interface Carregador<R> {
    void carregar(R dadosProcessados);
}