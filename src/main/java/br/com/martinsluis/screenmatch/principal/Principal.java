package br.com.martinsluis.screenmatch.principal;

import br.com.martinsluis.screenmatch.model.Serie;
import br.com.martinsluis.screenmatch.model.dto.DadosSerieDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosTemporadaDTO;
import br.com.martinsluis.screenmatch.repository.SerieRepository;
import br.com.martinsluis.screenmatch.service.ConsultaGemini;
import br.com.martinsluis.screenmatch.service.ConsumoAPI;
import org.springframework.stereotype.Component;
import br.com.martinsluis.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=11491604";
    List<DadosSerieDTO> dadosSeries = new ArrayList<>();

    private final SerieRepository serieRepository;
    private final ConsultaGemini consultaGemini;

    public Principal(SerieRepository serieRepository, ConsultaGemini consultaGemini) {
        this.serieRepository = serieRepository;
        this.consultaGemini = consultaGemini;
    }

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
        List<Serie> series = serieRepository.findAll();
        series.stream()
                        .sorted(Comparator.comparing(Serie::getGenero))
                        .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerieDTO dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        serieRepository.save(serie);
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