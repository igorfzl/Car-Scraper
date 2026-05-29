package br.edu.utfpr.td.tsi.raspador.etl;

import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArquivoJsonCarregador implements Carregador<List<Veiculo>> {

    private String nomeArquivo;
    // Esta lista vai servir de "memória" para acumular os carros de todas as páginas
    private List<Veiculo> todosVeiculos = new ArrayList<>();

    public ArquivoJsonCarregador(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void carregar(List<Veiculo> dadosProcessados) {
        // Adiciona os carros da página atual na lista gigante
        todosVeiculos.addAll(dadosProcessados);

        // Grava a lista acumulada no arquivo JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(todosVeiculos, writer);
            System.out.println("   [+] Arquivo atualizado! Total acumulado: " + todosVeiculos.size() + " carros.");
        } catch (IOException e) {
            System.err.println("-> Erro ao salvar o arquivo JSON: " + e.getMessage());
        }
    }
}