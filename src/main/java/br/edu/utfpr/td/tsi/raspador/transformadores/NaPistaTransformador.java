package br.edu.utfpr.td.tsi.raspador.transformadores;

import br.edu.utfpr.td.tsi.raspador.etl.Transformador;
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

        // 1. Encontra todos os cards de veículos
        // No site Na Pista, o card inteiro é a tag <a> de anúncio
        Elements cards = htmlBruto.select("a.styles_listingCard__TnL78");

        System.out.println("Encontrados " + cards.size() + " anúncios no Na Pista.");

        // 2. Extrai os dados de cada card
        for (Element card : cards) {
            try {
                // Caçando o Título (Juntando o h2 e o h3)
                Element marcaModeloEl = card.selectFirst("h2");
                Element versaoEl = card.selectFirst("h3");

                String marcaModelo = marcaModeloEl != null ? marcaModeloEl.text() : "";
                String versao = versaoEl != null ? versaoEl.text() : "";
                String titulo = (marcaModelo + " " + versao).trim();

                // Caçando o Preço
                Element precoEl = card.selectFirst("span.typo--heading");
                String preco = precoEl != null ? precoEl.text() : "Sem preço";

                // Caçando o Ano (Geralmente é a primeira tag com essa classe de badge)
                Element anoEl = card.selectFirst("div.styles_tag__5OqIX");
                String ano = anoEl != null ? anoEl.text() : "N/A";

                // Caçando o Link (O card já é a tag <a>, então pegamos o href direto)
                String href = card.attr("href");
                String link = !href.isEmpty() ? "https://napista.com.br" + href : "Sem link";

                // 3. Monta o objeto e adiciona na lista (ignorando cards vazios ou lixo)
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