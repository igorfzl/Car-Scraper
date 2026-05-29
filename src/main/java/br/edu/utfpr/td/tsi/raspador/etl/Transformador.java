package br.edu.utfpr.td.tsi.raspador.etl;

public interface Transformador<T, R> {
    R transformar(T dadosBrutos);
}