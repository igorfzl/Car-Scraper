package br.edu.utfpr.td.tsi.raspador.transformadores;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import br.edu.utfpr.td.tsi.raspador.etl.Transformador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class ChavesNaMaoTransformador implements Transformador<Document, List<Veiculo>> {

    @Override
    public List<Veiculo> transformar(Document htmlBruto) {
        List<Veiculo> veiculos = new ArrayList<>();

        Elements cards = htmlBruto.select("div.card_card__ENqoy");

        System.out.println("Encontrados " + cards.size() + " anúncios no Chaves na Mão.");

        for (Element card : cards) {
            try {
                Element marcaModeloEl = card.selectFirst("h2.styles_title__L3Xot b");
                Element versaoEl = card.selectFirst("h2.styles_title__L3Xot small");
                String titulo = (marcaModeloEl != null ? marcaModeloEl.text() : "") + " " +
                        (versaoEl != null ? versaoEl.text() : "");

                // Caçando o Preço (Usando o atributo de acessibilidade como segurança)
                Element precoEl = card.selectFirst("p[aria-label='Preço'] b");
                String preco = (precoEl != null) ? precoEl.text() : "Sem preço";

                // Caçando o Ano (Fica no primeiro item da lista de atributos)
                Element anoEl = card.selectFirst("ul li");
                String ano = (anoEl != null) ? anoEl.text() : "N/A";

                // Caçando o Link e adicionando o domínio base, já que o site usa links relativos
                Element linkEl = card.selectFirst("a.link_rawLink__Tabnf");
                String link = (linkEl != null) ? "https://www.chavesnamao.com.br" + linkEl.attr("href") : "Sem link";

                // 3. Monta o objeto e adiciona na lista, ignorando cards vazios
                if (!titulo.trim().isEmpty()) {
                    veiculos.add(new Veiculo(titulo.trim(), preco, ano, link));
                }
            } catch (Exception e) {
                System.err.println("Erro ao tentar raspar um card do Chaves na Mão. Pulando...");
            }
        }

        return veiculos;
    }
}