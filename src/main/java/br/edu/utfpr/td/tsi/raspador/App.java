package br.edu.utfpr.td.tsi.raspador;

import br.edu.utfpr.td.tsi.raspador.etl.Extrator;
import br.edu.utfpr.td.tsi.raspador.etl.Job;
import br.edu.utfpr.td.tsi.raspador.extratores.ExtratorJsoup;
import br.edu.utfpr.td.tsi.raspador.etl.ArquivoJsonCarregador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import br.edu.utfpr.td.tsi.raspador.transformadores.ChavesNaMaoTransformador;
import br.edu.utfpr.td.tsi.raspador.transformadores.LocalizaTransformador;
import br.edu.utfpr.td.tsi.raspador.transformadores.NaPistaTransformador;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.jsoup.nodes.Document;

import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        Extrator<Document> extratorPadrao = new ExtratorJsoup();

        int limitePaginas = 40;
        System.out.println("\n=== INICIANDO RASPAGEM: CHAVES NA MÃO ===");
        ArquivoJsonCarregador carregadorChaves = new ArquivoJsonCarregador("todos_veiculos_chavesnamao.json");
        Job<Document, List<Veiculo>> jobChaves = new Job<>(
                extratorPadrao,
                new ChavesNaMaoTransformador(),
                carregadorChaves
        );

        for (int i = 1; i <= limitePaginas; i++) {
            System.out.println("-> Extraindo página " + i + "...");
            jobChaves.executar("https://www.chavesnamao.com.br/carros-usados/pr-cascavel/?pg=" + i);
            pausa(1500); // Anti-ban
        }

        System.out.println("\n=== INICIANDO RASPAGEM: NA PISTA ===");
        ArquivoJsonCarregador carregadorNaPista = new ArquivoJsonCarregador("todos_veiculos_napista.json");
        Job<Document, List<Veiculo>> jobNaPista = new Job<>(
                extratorPadrao,
                new NaPistaTransformador(),
                carregadorNaPista
        );

        for (int i = 1; i <= limitePaginas; i++) {
            System.out.println("-> Extraindo página " + i + "...");
            jobNaPista.executar("https://napista.com.br/busca/carro/cascavel?pn=" + i);
            pausa(2000); // Anti-ban
        }

        System.out.println("\nExtração finalizada com sucesso!");


        System.out.println("\n=== INICIANDO RASPAGEM: LOCALIZA SEMINOVOS ===");
        ArquivoJsonCarregador carregadorLocaliza = new ArquivoJsonCarregador("veiculos_localiza.json");
        Job<Document, List<Veiculo>> jobLocaliza = new Job<>(
                extratorPadrao,
                new LocalizaTransformador(),
                carregadorLocaliza
        );
        // Link focado na sua região
        jobLocaliza.executar("https://seminovos.localiza.com/carros/pr-cascavel");

        System.out.println("\nExtração da primeira página de todos os sites finalizada com sucesso!");
    }

    private static void pausa(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            System.err.println("Erro na pausa do sistema.");
        }
    }
}