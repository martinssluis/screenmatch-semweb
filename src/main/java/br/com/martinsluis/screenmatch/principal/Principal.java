package br.com.martinsluis.screenmatch.principal;

import br.com.martinsluis.screenmatch.model.dto.DadosEpisodioDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosSerieDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosTemporadaDTO;
import br.com.martinsluis.screenmatch.service.ConsumoAPI;
import br.com.martinsluis.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=11491604";

    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = sc.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerieDTO dados = conversor.obterDados(json, DadosSerieDTO.class);
        System.out.println(dados);

        List<DadosTemporadaDTO> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season="+ i + API_KEY);
            DadosTemporadaDTO dadosTemporada = conversor.obterDados(json, DadosTemporadaDTO.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e-> System.out.println(e.titulo())));
    }
}
