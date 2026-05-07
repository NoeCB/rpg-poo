package com.dbd.controller;

import com.dbd.dto.ActionRequest;
import com.dbd.dto.GameStateResponse;
import com.dbd.service.TrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*") // Para evitar problemas de CORS con el Frontend
public class GameController {

    @Autowired
    private TrialService trialService;

    @PostMapping("/start")
    public ResponseEntity<GameStateResponse> iniciarPartida() {
        GameStateResponse response = trialService.iniciarPartidaAleatoria();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/action")
    public ResponseEntity<GameStateResponse> ejecutarAccion(@RequestBody ActionRequest request) {
        try {
            GameStateResponse response = trialService.ejecutarAccion(request);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
