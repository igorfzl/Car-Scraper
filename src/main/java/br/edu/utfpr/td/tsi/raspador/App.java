package br.edu.utfpr.td.tsi.raspador;

import br.edu.utfpr.td.tsi.raspador.servico.OrquestradorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final OrquestradorService orquestrador;

    public App(OrquestradorService orquestrador) {
        this.orquestrador = orquestrador;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        orquestrador.iniciarRaspagem(20);
    }
}