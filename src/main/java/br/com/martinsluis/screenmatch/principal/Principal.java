package br.com.martinsluis.screenmatch.principal;

import br.com.martinsluis.screenmatch.model.Episodio;
import br.com.martinsluis.screenmatch.model.dto.DadosEpisodioDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosSerieDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosTemporadaDTO;
import br.com.martinsluis.screenmatch.service.ConsumoAPI;
import br.com.martinsluis.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

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

        List<DadosEpisodioDTO> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                //.toList(); //lista imutavel
                .collect(Collectors.toList());

//        System.out.println("Top 10 episodios");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primeiro filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodioDTO::avaliacao).reversed())
//                .peek(e-> System.out.println("Ordenação " + e))
//                .limit(10)
//                .peek(e-> System.out.println("Limite " + e))
//                .map(e-> e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do título do episódio: ");
//        var trechoTitulo = sc.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e-> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                                .findFirst();
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        } else{
//            System.out.println("Episodio não encontrado");
//        }

//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = sc.nextInt();
//        sc.nextLine(); //limpar buffer
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter fomatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episódio: " + e.getTitulo() +
//                                " Data lançamento: " + e.getDataLancamento().format(fomatador)
//                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e-> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);
    }
}
