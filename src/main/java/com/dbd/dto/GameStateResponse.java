package com.dbd.dto;

import com.dbd.entidades.Personaje;
import java.util.ArrayList;
import java.util.List;

public class GameStateResponse {
    private List<Personaje> supervivientes = new ArrayList<>();
    private List<Personaje> killers = new ArrayList<>();
    private List<String> logs = new ArrayList<>();
    private boolean partidaTerminada;
    private String ganador;

    public List<Personaje> getSupervivientes() {
        return supervivientes;
    }

    public void setSupervivientes(List<Personaje> supervivientes) {
        this.supervivientes = supervivientes;
    }

    public List<Personaje> getKillers() {
        return killers;
    }

    public void setKillers(List<Personaje> killers) {
        this.killers = killers;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void addLog(String log) {
        this.logs.add(log);
    }

    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public void setPartidaTerminada(boolean partidaTerminada) {
        this.partidaTerminada = partidaTerminada;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }
}
