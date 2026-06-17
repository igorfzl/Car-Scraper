package br.edu.utfpr.td.tsi.raspador.servico;

import br.edu.utfpr.td.tsi.raspador.etl.interfaces.Extrator;
import br.edu.utfpr.td.tsi.raspador.etl.Job;
import br.edu.utfpr.td.tsi.raspador.etl.carregador.ArquivoJsonCarregador;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import br.edu.utfpr.td.tsi.raspador.etl.transformadores.ChavesNaMaoTransformador;
import br.edu.utfpr.td.tsi.raspador.etl.transformadores.LocalizaTransformador;
import br.edu.utfpr.td.tsi.raspador.etl.transformadores.NaPistaTransformador;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrquestradorService {

    private final Extrator<Document> extratorPadrao;

    public OrquestradorService(Extrator<Document> extratorPadrao) {
        this.extratorPadrao = extratorPadrao;
    }

    public void iniciarRaspagem(int limitePaginas) {
        extrairChavesNaMao(limitePaginas);
        extrairNaPista(limitePaginas);
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
            pausa(1500);
        }
    }

    private void extrairNaPista(int limite) {
        System.out.println("\n=== INICIANDO RASPAGEM: NA PISTA ===");
        ArquivoJsonCarregador carregador = new ArquivoJsonCarregador("veiculos_napista.json");
        Job<Document, List<Veiculo>> job = new Job<>(this.extratorPadrao, new NaPistaTransformador(), carregador);

        for (int i = 1; i <= limite; i++) {
            System.out.println("-> Extraindo página " + i + "...");
            job.executar("https://napista.com.br/busca/carro/cascavel?pn=" + i);
            pausa(2000);
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