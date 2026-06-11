package br.edu.utfpr.td.tsi.raspador.servico;

import br.edu.utfpr.td.tsi.raspador.etl.Carregador;
import br.edu.utfpr.td.tsi.raspador.etl.Extrator;
import br.edu.utfpr.td.tsi.raspador.etl.Job;
import br.edu.utfpr.td.tsi.raspador.extratores.ExtratorJsoup;
import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;

import org.jsoup.nodes.Document;

import java.util.List;

public class GerenciadorDeRaspagem {

    public void executarTodosOsSites() {
        Extrator<Document> carteiroJsoup = new ExtratorJsoup();

        Carregador<List<Veiculo>> impressora = dados -> {
            for (Veiculo v : dados) {
                System.out.println(v.toString());
            }
        };
    }
}