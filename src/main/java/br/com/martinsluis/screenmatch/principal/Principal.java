package br.com.martinsluis.screenmatch.principal;

import br.com.martinsluis.screenmatch.model.dto.DadosSerieDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosTemporadaDTO;
import br.com.martinsluis.screenmatch.service.ConsumoAPI;
import br.com.martinsluis.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=11491604";
    List<DadosSerieDTO> dadosSeries = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;
        while (opcao!=0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSerieBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void listarSerieBuscadas() {
        dadosSeries.forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerieDTO dados = getDadosSerie();
        dadosSeries.add(dados);
        System.out.println(dados);
    }

    private DadosSerieDTO getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerieDTO dados = conversor.obterDados(json, DadosSerieDTO.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerieDTO dados = getDadosSerie();
        List<DadosTemporadaDTO> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dados.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporadaDTO dadosTemporada = conversor.obterDados(json, DadosTemporadaDTO.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
}