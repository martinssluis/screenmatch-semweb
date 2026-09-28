package br.com.martinsluis.screenmatch.model;

public enum Generos {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String generoOmdb;

    Generos(String generoOmdb) {
        this.generoOmdb = generoOmdb;
    }

    public static Generos fromString(String text){
        for (Generos genero : Generos.values()){
            if(genero.generoOmdb.equalsIgnoreCase(text)){
                return genero;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida");
    }
}
