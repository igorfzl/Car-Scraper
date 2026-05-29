package br.edu.utfpr.td.tsi.raspador.etl;

import br.edu.utfpr.td.tsi.raspador.modelo.Veiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArquivoJsonCarregador implements Carregador<List<Veiculo>> {

    private String nomeArquivo;

    public ArquivoJsonCarregador(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void carregar(List<Veiculo> dadosProcessados) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(dadosProcessados, writer);
            System.out.println("-> Sucesso! Dados salvos no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("-> Erro ao salvar o arquivo JSON: " + e.getMessage());
        }
    }
}