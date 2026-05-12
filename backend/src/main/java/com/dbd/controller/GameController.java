package com.dbd.controller;

import com.dbd.dto.ActionRequest;
import com.dbd.dto.GameStateResponse;
import com.dbd.dto.CharacterSelectionRequest;
import com.dbd.service.TrialService;
import com.dbd.core.Logro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*") // Para evitar problemas de CORS con el Frontend
public class GameController {

    @Autowired
    private TrialService trialService;

    @GetMapping("/characters")
    public ResponseEntity<Map<String, List<String>>> getCharacters() {
        return ResponseEntity.ok(trialService.getAvailableCharacters());
    }

    @GetMapping("/achievements")
    public ResponseEntity<List<Logro>> getAchievements() {
        return ResponseEntity.ok(trialService.getLogros());
    }

    @PostMapping("/start")
    public ResponseEntity<GameStateResponse> iniciarPartidaAleatoria() {
        GameStateResponse response = trialService.iniciarPartidaAleatoria();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/start-manual")
    public ResponseEntity<GameStateResponse> iniciarPartidaManual(@RequestBody CharacterSelectionRequest request) {
        GameStateResponse response = trialService.iniciarPartidaManual(request);
        return ResponseEntity.ok(response);
    }

    @Autowired
    private org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    @PostMapping("/action")
    public ResponseEntity<GameStateResponse> ejecutarAccion(@RequestBody ActionRequest request) {
        try {
            GameStateResponse response = trialService.ejecutarAccion(request);
            // Emitir actualización por WebSocket
            messagingTemplate.convertAndSend("/topic/combat", response);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
