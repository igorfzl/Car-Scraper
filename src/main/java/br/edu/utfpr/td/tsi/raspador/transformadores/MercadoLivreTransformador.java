package br.edu.utfpr.td.tsi.raspador.transformadores;

import br.edu.utfpr.td.tsi.raspador.etl.Transformador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MercadoLivreTransformador implements Transformador<Document, List<Veiculo>> {

    @Override
    public List<Veiculo> transformar(Document htmlBruto) {
        List<Veiculo> veiculos = new ArrayList<>();

        // 1. Busca todos os contêineres de anúncios na página do ML
        Elements cards = htmlBruto.select("li.ui-search-layout__item");

        System.out.println("Encontrados " + cards.size() + " anúncios no Mercado Livre.");

        // 2. Itera sobre cada anúncio para extrair os dados
        for (Element card : cards) {
            try {
                // TÍTULO: O H2 é padrão no ML para títulos de anúncios
                Element tituloEl = card.selectFirst("h2.ui-search-item__title");
                String titulo = tituloEl != null ? tituloEl.text().trim() : "";

                // PREÇO: Pega o valor numérico principal
                Element precoEl = card.selectFirst(".price-tag-fraction");
                String preco = precoEl != null ? "R$ " + precoEl.text().trim() : "Sem preço";

                // ANO E KM: Ficam em uma listagem de atributos
                Elements atributos = card.select("ul.ui-search-card-attributes li");
                String ano = "N/A";
                String km = ""; // Extra bônus, caso queira guardar depois!

                if (atributos.size() >= 1) {
                    ano = atributos.get(0).text().trim();
                }
                if (atributos.size() >= 2) {
                    km = atributos.get(1).text().trim();
                }

                // LINK: Procura o primeiro <a> válido que leva para a página do produto
                Element linkEl = card.selectFirst("a.ui-search-link");
                String link = linkEl != null ? linkEl.attr("href") : "Sem link";

                // 3. Adiciona na lista apenas se o título não estiver vazio
                if (!titulo.isEmpty()) {
                    // Opcionalmente você pode juntar a KM ao título se quiser manter apenas as 4 propriedades do Veiculo original
                    veiculos.add(new Veiculo(titulo + " (" + km + ")", preco, ano, link));
                }

            } catch (Exception e) {
                System.err.println("Erro ao tentar raspar um card do Mercado Livre. Pulando...");
            }
        }

        return veiculos;
    }
}