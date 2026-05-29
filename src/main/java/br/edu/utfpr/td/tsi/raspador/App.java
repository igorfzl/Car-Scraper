package br.edu.utfpr.td.tsi.raspador;

import br.edu.utfpr.td.tsi.raspador.etl.Carregador;
import br.edu.utfpr.td.tsi.raspador.etl.Extrator;
import br.edu.utfpr.td.tsi.raspador.etl.Job;
import br.edu.utfpr.td.tsi.raspador.extratores.ExtratorJsoup;
import br.edu.utfpr.td.tsi.raspador.etl.ArquivoJsonCarregador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import br.edu.utfpr.td.tsi.raspador.transformadores.ChavesNaMaoTransformador;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.jsoup.nodes.Document;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // 1. Prepara as ferramentas gerais
        Extrator<Document> extratorPadrao = new ExtratorJsoup(); // Confirme se o nome exato da sua classe é este
        Carregador<List<Veiculo>> carregadorJson = new ArquivoJsonCarregador("veiculos_chavesnamao.json");

        // 2. Monta o fluxo APENAS para o Chaves na Mão
        System.out.println("\n=== INICIANDO RASPAGEM: CHAVES NA MÃO ===");

        Job<Document, List<Veiculo>> jobChaves = new Job<>(
                extratorPadrao,
                new ChavesNaMaoTransformador(),
                carregadorJson
        );

        // 3. Executa a busca em Toledo
        jobChaves.executar("https://www.chavesnamao.com.br/carros-usados/pr-toledo/");
    }
}