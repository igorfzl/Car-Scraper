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
    private List<Veiculo> todosVeiculos = new ArrayList<>();

    public ArquivoJsonCarregador(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void carregar(List<Veiculo> dadosProcessados) {
        todosVeiculos.addAll(dadosProcessados);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(todosVeiculos, writer);
            System.out.println("   [+] Arquivo atualizado! Total acumulado: " + todosVeiculos.size() + " carros.");
        } catch (IOException e) {
            System.err.println("-> Erro ao salvar o arquivo JSON: " + e.getMessage());
        }
    }
}