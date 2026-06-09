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

        // 1. Busca todos os cards de anúncio pelo atributo data-testid (muito seguro e estável!)
        Elements cards = htmlBruto.select("article[data-testid=product-card-standard]");

        System.out.println("Encontrados " + cards.size() + " anúncios na Localiza Seminovos.");

        // 2. Itera sobre cada card extraindo as informações
        for (Element card : cards) {
            try {
                // TÍTULO: O título na Localiza vem quebrado em h2 (Marca/Modelo) e span (Versão)
                Element marcaModeloEl = card.selectFirst("h2");
                Element versaoEl = card.selectFirst("span[class*=card-content__description]");

                String marcaModelo = marcaModeloEl != null ? marcaModeloEl.text().trim() : "";
                String versao = versaoEl != null ? versaoEl.text().trim() : "";
                String titulo = marcaModelo + " " + versao;

                // PREÇO: Fica dentro de um h3 na div de price
                Element precoEl = card.selectFirst("div[class*=card-content__price] h3");
                String preco = precoEl != null ? precoEl.text().trim() : "Sem preço";

                // ANO E KM: Ficam em tags <p> dentro da área de informações
                Elements infos = card.select("div[class*=card-content__info] p");
                String km = infos.size() >= 1 ? infos.get(0).text().trim() : "";
                String ano = infos.size() >= 2 ? infos.get(1).text().trim() : "N/A";

                // LINK: O card <article> fica dentro de uma tag <a>, então pegamos o href do elemento pai
                Element linkEl = card.parent();
                String href = linkEl != null ? linkEl.attr("href") : "";
                String link = href.startsWith("/") ? "https://seminovos.localiza.com" + href : href;

                // 3. Adiciona na lista
                if (!titulo.trim().isEmpty()) {
                    veiculos.add(new Veiculo(titulo + " (" + km + ")", preco, ano, link));
                }

            } catch (Exception e) {
                System.err.println("Erro ao tentar raspar um card da Localiza. Pulando...");
            }
        }

        return veiculos;
    }
}