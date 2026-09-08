package br.com.martinsluis.screenmatch.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporadaDTO(
        @JsonAlias("Season") Integer numero,
        @JsonAlias("Episodes") List<DadosEpisodioDTO> episodios
) {}
