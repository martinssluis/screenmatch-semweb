package br.com.martinsluis.screenmatch.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosSerieDTO(
        @JsonAlias("Title")String titulo,
        @JsonAlias("totalSesons")Integer totalEmporadas,
        @JsonAlias("imdbRating")String avaliacao){}
