package br.edu.utfpr.td.tsi.raspador.etl.interfaces;

public interface Extrator<T> {
    T extrair(String fonte);
}