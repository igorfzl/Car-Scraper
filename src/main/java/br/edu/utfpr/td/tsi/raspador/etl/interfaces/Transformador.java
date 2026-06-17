package br.edu.utfpr.td.tsi.raspador.etl.interfaces;

public interface Transformador<T, R> {
    R transformar(T dadosBrutos);
}