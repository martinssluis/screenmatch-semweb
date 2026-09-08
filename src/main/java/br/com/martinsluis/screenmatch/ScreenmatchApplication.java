package br.com.martinsluis.screenmatch;

import br.com.martinsluis.screenmatch.model.dto.DadosEpisodioDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosSerieDTO;
import br.com.martinsluis.screenmatch.model.dto.DadosTemporadaDTO;
import br.com.martinsluis.screenmatch.service.ConsumoAPI;
import br.com.martinsluis.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=11491604");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerieDTO dados = conversor.obterDados(json, DadosSerieDTO.class);
		System.out.println(dados);

		json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=11491604");
		DadosEpisodioDTO dadosEpisodio = conversor.obterDados(json, DadosEpisodioDTO.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporadaDTO> temporadas = new ArrayList<>();

		for(int i=1; i<=dados.totalTemporadas(); i++){
			json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season="+ i +"&apikey=11491604");
			DadosTemporadaDTO dadosTemporada = conversor.obterDados(json, DadosTemporadaDTO.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}

}
