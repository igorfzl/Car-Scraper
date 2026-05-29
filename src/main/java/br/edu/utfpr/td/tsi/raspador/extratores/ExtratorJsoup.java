package br.edu.utfpr.td.tsi.raspador.extratores;

import br.edu.utfpr.td.tsi.raspador.etl.Extrator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ExtratorJsoup implements Extrator<Document> {

    @Override
    public Document extrair(String url) {
        try {
            System.out.println("-> Conectando e baixando HTML de: " + url);
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(10000)
                    .get();
        } catch (IOException e) {
            System.err.println("Erro ao tentar raspar a página.");
            return null;
        }
    }
}