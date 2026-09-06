package br.com.martinsluis.screenmatch.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerieDTO(
        @JsonAlias("Title")String titulo,
        @JsonAlias("totalSeasons")Integer totalTemporadas,
        @JsonAlias("imdbRating")String avaliacao){}
