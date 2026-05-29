package br.edu.utfpr.td.tsi.raspador.etl;

public class Job<T, R> {
    private Extrator<T> extrator;
    private Transformador<T, R> transformador;
    private Carregador<R> carregador;

    // --- ADICIONE ESTE CONSTRUTOR AQUI ---
    public Job(Extrator<T> extrator, Transformador<T, R> transformador, Carregador<R> carregador) {
        this.extrator = extrator;
        this.transformador = transformador;
        this.carregador = carregador;
    }

    // Construtor vazio por segurança
    public Job() {
    }

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