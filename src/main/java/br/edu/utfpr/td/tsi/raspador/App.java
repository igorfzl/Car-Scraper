package br.edu.utfpr.td.tsi.raspador;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        Extrator<Document> extratorPadrao = new JsoupExtrator();
        Carregador<List<Veiculo>> carregadorJson = new ArquivoJsonCarregador();

        // Monta e executa o fluxo para o site 1
        Job<Document, List<Veiculo>> jobML = new Job<>(
                extratorPadrao,
                new MercadoLivreTransformador(),
                carregadorJson
        );
        jobML.executar("https://lista.mercadolivre.com.br/veiculos/carros-caminhonetes/parana/toledo/");

        // Monta e executa o fluxo para o site 2
        Job<Document, List<Veiculo>> jobSoCarrao = new Job<>(
                extratorPadrao,
                new SoCarraoTransformador(),
                carregadorJson
        );
        jobSoCarrao.executar("https://www.socarrao.com.br/carros/toledo-pr");

    }
}
