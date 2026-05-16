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
        System.out.println(">>> [GameController] GET /characters : Petición recibida correctamente. Security superado.");
        return ResponseEntity.ok(trialService.getAvailableCharacters());
    }

    @GetMapping("/saves")
    public ResponseEntity<List<Map<String, Object>>> getSaves() {
        System.out.println(">>> [GameController] GET /saves : Petición recibida.");
        return ResponseEntity.ok(trialService.getSaves());
    }

    @PostMapping("/load/{id}")
    public ResponseEntity<GameStateResponse> loadSave(@PathVariable int id) {
        System.out.println(">>> [GameController] POST /load/" + id + " : Cargando partida...");
        try {
            GameStateResponse response = trialService.cargarPartida(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error al cargar la partida " + id + ": " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<GameStateResponse> saveGame(@RequestBody Map<String, Integer> payload) {
        try {
            int ranura = payload.getOrDefault("slot", 1);
            return ResponseEntity.ok(trialService.guardarPartidaWeb(ranura));
        } catch (Exception e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/state")
    public ResponseEntity<GameStateResponse> getState() {
        try {
            return ResponseEntity.ok(trialService.getState());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
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
