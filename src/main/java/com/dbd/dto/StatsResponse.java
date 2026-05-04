package com.dbd.dto;

public class StatsResponse {
    private int partidasTotales;
    private double mediaRondas;
    private int victoriasKillers;
    private int victoriasSurvis;

    public int getPartidasTotales() {
        return partidasTotales;
    }

    public void setPartidasTotales(int partidasTotales) {
        this.partidasTotales = partidasTotales;
    }

    public double getMediaRondas() {
        return mediaRondas;
    }

    public void setMediaRondas(double mediaRondas) {
        this.mediaRondas = mediaRondas;
    }

    public int getVictoriasKillers() {
        return victoriasKillers;
    }

    public void setVictoriasKillers(int victoriasKillers) {
        this.victoriasKillers = victoriasKillers;
    }

    public int getVictoriasSurvis() {
        return victoriasSurvis;
    }

    public void setVictoriasSurvis(int victoriasSurvis) {
        this.victoriasSurvis = victoriasSurvis;
    }
}
