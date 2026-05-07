package com.dbd.dto;

import java.util.List;

public class CharacterSelectionRequest {
    private List<String> supervivientes;
    private List<String> killers;

    public List<String> getSupervivientes() {
        return supervivientes;
    }

    public void setSupervivientes(List<String> supervivientes) {
        this.supervivientes = supervivientes;
    }

    public List<String> getKillers() {
        return killers;
    }

    public void setKillers(List<String> killers) {
        this.killers = killers;
    }
}
