package com.dbd.dto;

import java.util.List;

public class CustomStartRequest {
    private List<String> killerIds;
    private List<String> survivorIds;

    public List<String> getKillerIds() {
        return killerIds;
    }

    public void setKillerIds(List<String> killerIds) {
        this.killerIds = killerIds;
    }

    public List<String> getSurvivorIds() {
        return survivorIds;
    }

    public void setSurvivorIds(List<String> survivorIds) {
        this.survivorIds = survivorIds;
    }
}
