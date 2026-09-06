package br.com.martinsluis.screenmatch;

import br.com.martinsluis.screenmatch.model.dto.DadosSerieDTO;
import br.com.martinsluis.screenmatch.service.ConsumoAPI;
import br.com.martinsluis.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	}

}
