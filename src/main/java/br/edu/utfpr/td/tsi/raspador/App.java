package br.edu.utfpr.td.tsi.raspador;

import br.edu.utfpr.td.tsi.raspador.etl.Carregador;
import br.edu.utfpr.td.tsi.raspador.etl.Extrator;
import br.edu.utfpr.td.tsi.raspador.etl.Job;
import br.edu.utfpr.td.tsi.raspador.etl.ArquivoJsonCarregador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import br.edu.utfpr.td.tsi.raspador.transformadores.ChavesNaMaoTransformador;
import br.edu.utfpr.td.tsi.raspador.transformadores.LocalizaTransformador;
import br.edu.utfpr.td.tsi.raspador.transformadores.NaPistaTransformador;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jsoup.nodes.Document;

import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final Extrator<Document> extratorPadrao;

    public App(Extrator<Document> extratorPadrao) {
        this.extratorPadrao = extratorPadrao;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int limitePaginas = 15; // O limite que você definiu

        extrairNaPista(limitePaginas);
        extrairChavesNaMao(limitePaginas);
        extrairLocaliza(limitePaginas);

        System.out.println("\nExtração em lote finalizada com sucesso!");
    }

    private void extrairChavesNaMao(int limite) {
        System.out.println("\n=== INICIANDO RASPAGEM: CHAVES NA MÃO ===");

        ArquivoJsonCarregador carregador = new ArquivoJsonCarregador("veiculos_chavesnamao.json");
        Job<Document, List<Veiculo>> job = new Job<>(this.extratorPadrao, new ChavesNaMaoTransformador(), carregador);

        for (int i = 1; i <= limite; i++) {
            System.out.println("-> Extraindo página " + i + "...");
            job.executar("https://www.chavesnamao.com.br/carros-usados/pr-cascavel/?pg=" + i);
            pausa(1500); // Pausa anti-ban
        }
    }

    private void extrairNaPista(int limite) {
        System.out.println("\n=== INICIANDO RASPAGEM: NA PISTA ===");

        ArquivoJsonCarregador carregador = new ArquivoJsonCarregador("veiculos_napista.json");
        Job<Document, List<Veiculo>> job = new Job<>(this.extratorPadrao, new NaPistaTransformador(), carregador);

        for (int i = 1; i <= limite; i++) {
            System.out.println("-> Extraindo página " + i + "...");
            job.executar("https://napista.com.br/busca/carro/cascavel?pn=" + i);
            pausa(3000);
        }
    }

    private void extrairLocaliza(int limite) {
        System.out.println("\n=== INICIANDO RASPAGEM: LOCALIZA SEMINOVOS ===");

        ArquivoJsonCarregador carregador = new ArquivoJsonCarregador("veiculos_localiza.json");
        Job<Document, List<Veiculo>> job = new Job<>(this.extratorPadrao, new LocalizaTransformador(), carregador);

        for (int i = 1; i <= limite; i++) {
            System.out.println("-> Extraindo página " + i + "...");
            job.executar("https://seminovos.localiza.com/carros/pr-cascavel?page=" + i);
            pausa(2000);
        }
    }

    private void pausa(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            System.err.println("Erro na pausa do sistema.");
        }
    }
}