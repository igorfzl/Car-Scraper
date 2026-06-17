package br.edu.utfpr.td.tsi.raspador.etl.transformadores;

import br.edu.utfpr.td.tsi.raspador.etl.interfaces.Transformador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class NaPistaTransformador implements Transformador<Document, List<Veiculo>> {

    @Override
    public List<Veiculo> transformar(Document htmlBruto) {
        List<Veiculo> veiculos = new ArrayList<>();

        Elements cards = htmlBruto.select("a.styles_listingCard__TnL78");

        System.out.println("Encontrados " + cards.size() + " anúncios no Na Pista.");

        for (Element card : cards) {
            try {

                Element marcaModeloEl = card.selectFirst("h2");
                Element versaoEl = card.selectFirst("h3");

                String marcaModelo = marcaModeloEl != null ? marcaModeloEl.text() : "";
                String versao = versaoEl != null ? versaoEl.text() : "";
                String titulo = (marcaModelo + " " + versao).trim();

                Element precoEl = card.selectFirst("span.typo--heading");
                String preco = precoEl != null ? precoEl.text() : "Sem preço";


                Elements tags = card.select("div.styles_tag__5OqIX");
                String ano = tags.size() >= 1 ? tags.get(0).text() : "N/A";

                String href = card.attr("href");
                String link = !href.isEmpty() ? "https://napista.com.br" + href : "Sem link";

                if (!titulo.isEmpty()) {
                    veiculos.add(new Veiculo(titulo, preco, ano, link));
                }
            } catch (Exception e) {
                System.err.println("Erro ao tentar raspar um card do Na Pista. Pulando...");
            }
        }

        return veiculos;
    }
}