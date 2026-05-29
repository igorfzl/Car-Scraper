package br.edu.utfpr.td.tsi.raspador.etl;

public class Job<T, R> {
    private Extrator<T> extrator;
    private Transformador<T, R> transformador;
    private Carregador<R> carregador;

    public void setExtrator(Extrator<T> extrator) {
        this.extrator = extrator;
    }

    public void setTransformador(Transformador<T, R> transformador) {
        this.transformador = transformador;
    }

    public void setCarregador(Carregador<R> carregador) {
        this.carregador = carregador;
    }

    public void executar(String urlFonte) {
        T dadosBrutos = extrator.extrair(urlFonte);
        if (dadosBrutos != null) {
            R dadosProcessados = transformador.transformar(dadosBrutos);
            carregador.carregar(dadosProcessados);
        }
    }
}