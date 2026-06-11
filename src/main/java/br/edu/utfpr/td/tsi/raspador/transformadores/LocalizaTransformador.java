package br.edu.utfpr.td.tsi.raspador.transformadores;

import br.edu.utfpr.td.tsi.raspador.etl.Transformador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LocalizaTransformador implements Transformador<Document, List<Veiculo>> {

    @Override
    public List<Veiculo> transformar(Document htmlBruto) {
        List<Veiculo> veiculos = new ArrayList<>();

        Elements cards = htmlBruto.select("article[data-testid=product-card-standard]");

        System.out.println("Encontrados " + cards.size() + " anúncios na Localiza Seminovos.");

        for (Element card : cards) {
            try {
                Element marcaModeloEl = card.selectFirst("h2");
                Element versaoEl = card.selectFirst("span[class*=card-content__description]");

                String marcaModelo = marcaModeloEl != null ? marcaModeloEl.text().trim() : "";
                String versao = versaoEl != null ? versaoEl.text().trim() : "";
                String titulo = marcaModelo + " " + versao;

                Element precoEl = card.selectFirst("div[class*=card-content__price] h3");
                String preco = precoEl != null ? precoEl.text().trim() : "Sem preço";

                Elements infos = card.select("div[class*=card-content__info] p");
                String ano = infos.size() >= 2 ? infos.get(1).text().trim() : "N/A";
                Element linkEl = card.parent();
                String href = linkEl != null ? linkEl.attr("href") : "";
                String link = href.startsWith("/") ? "https://seminovos.localiza.com" + href : href;

                if (!titulo.trim().isEmpty()) {
                    veiculos.add(new Veiculo(titulo, preco, ano, link));
                }

            } catch (Exception e) {
                System.err.println("Erro ao tentar raspar um card da Localiza. Pulando...");
            }
        }

        return veiculos;
    }
}