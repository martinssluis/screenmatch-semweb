package br.com.martinsluis.screenmatch.principal;

import br.com.martinsluis.screenmatch.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=11491604";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = sc.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        //https://www.omdbapi.com/?t=gilmore+girls&season="+ i +"&apikey=11491604
    }
}
