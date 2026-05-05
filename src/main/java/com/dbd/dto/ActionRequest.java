package com.dbd.dto;

public class ActionRequest {
    private int atacanteIndex;
    private int objetivoIndex;
    private String tipoAccion;
    private int perkIndex;

    public int getAtacanteIndex() {
        return atacanteIndex;
    }

    public void setAtacanteIndex(int atacanteIndex) {
        this.atacanteIndex = atacanteIndex;
    }

    public int getObjetivoIndex() {
        return objetivoIndex;
    }

    public void setObjetivoIndex(int objetivoIndex) {
        this.objetivoIndex = objetivoIndex;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public int getPerkIndex() {
        return perkIndex;
    }

    public void setPerkIndex(int perkIndex) {
        this.perkIndex = perkIndex;
    }
}
