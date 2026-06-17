package br.edu.utfpr.td.tsi.raspador.etl.interfaces;

public interface Carregador<R> {
    void carregar(R dadosProcessados);
}