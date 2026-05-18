package com.dbd.dto;

import com.dbd.entidades.Personaje;
import java.util.List;
import java.util.ArrayList;

public class GameStateResponse {
    private List<Personaje> supervivientes = new ArrayList<>();
    private List<Personaje> killers = new ArrayList<>();
    private List<String> logs = new ArrayList<>();
    private boolean partidaTerminada = false;
    private String ganador = "";

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

    public void setLogs(List<String> logs) {
        this.logs = logs;
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

    private String modoJuego = "manual";

    public String getModoJuego() {
        return modoJuego;
    }

    public void setModoJuego(String modoJuego) {
        this.modoJuego = modoJuego;
    }

    private String decidedAction = "";
    private int decidedTargetIndex = -1;
    private int decidedPerkIndex = -1;

    public String getDecidedAction() {
        return decidedAction;
    }

    public void setDecidedAction(String decidedAction) {
        this.decidedAction = decidedAction;
    }

    public int getDecidedTargetIndex() {
        return decidedTargetIndex;
    }

    public void setDecidedTargetIndex(int decidedTargetIndex) {
        this.decidedTargetIndex = decidedTargetIndex;
    }

    public int getDecidedPerkIndex() {
        return decidedPerkIndex;
    }

    public void setDecidedPerkIndex(int decidedPerkIndex) {
        this.decidedPerkIndex = decidedPerkIndex;
    }
}
