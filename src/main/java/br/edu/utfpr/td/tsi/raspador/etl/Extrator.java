package br.edu.utfpr.td.tsi.raspador.etl;

public interface Extrator<T> {
    T extrair(String fonte);
}