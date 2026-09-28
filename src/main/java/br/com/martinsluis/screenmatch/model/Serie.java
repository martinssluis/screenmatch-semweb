package br.com.martinsluis.screenmatch.model;


import br.com.martinsluis.screenmatch.model.dto.DadosSerieDTO;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Generos genero;
    private String atores;
    private String poster;
    private String sinopse;

    public Serie(DadosSerieDTO dadosSerieDTO) {
        this.titulo = dadosSerieDTO.titulo();
        this.totalTemporadas = dadosSerieDTO.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerieDTO.avaliacao())).orElse(0);
        this.genero = Generos.fromString(dadosSerieDTO.genero().split(",")[0].trim());
        this.atores = dadosSerieDTO.atores();
        this.poster = dadosSerieDTO.poster();
        this.sinopse = dadosSerieDTO.sinopse();
    }
}
