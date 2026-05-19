package com.dbd.service;

import com.dbd.dao.GestorPersistencia;
import com.dbd.dao.UsuarioRepository;
import com.dbd.dto.ActionRequest;
import com.dbd.dto.CharacterSelectionRequest;
import com.dbd.dto.GameStateResponse;
import com.dbd.entidades.Personaje;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrialServiceTest {

    @Autowired
    private TrialService trialService;

    @MockBean
    private GestorPersistencia gestorPersistencia;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testGetAvailableCharacters() {
        Map<String, List<String>> chars = trialService.getAvailableCharacters();
        assertNotNull(chars);
        assertTrue(chars.containsKey("supervivientes"));
        assertTrue(chars.containsKey("killers"));
        assertEquals(8, chars.get("supervivientes").size());
        assertEquals(8, chars.get("killers").size());
    }

    @Test
    public void testIniciarPartidaManualNormal() {
        CharacterSelectionRequest request = new CharacterSelectionRequest();
        request.setModo("manual");
        request.setBando("survivientes");
        request.setDificultad("normal");
        request.setSupervivientes(List.of("LeonKennedy", "SteveHarrington", "FengMin"));
        request.setKillers(List.of("GhostFace", "Legion", "Onryo"));

        GameStateResponse response = trialService.iniciarPartidaManual(request);

        assertNotNull(response);
        assertEquals(3, response.getSupervivientes().size());
        assertEquals(3, response.getKillers().size());
        assertEquals("manual", response.getModoJuego());
    }

    @Test
    public void testIniciarPartidaManualDificultadExtremo() {
        // En dificultad extremo, controlando supervivientes, los asesinos deben tener sus estadísticas escaladas (1.4x)
        CharacterSelectionRequest request = new CharacterSelectionRequest();
        request.setModo("manual");
        request.setBando("survivientes");
        request.setDificultad("extremo");
        request.setSupervivientes(List.of("LeonKennedy", "SteveHarrington", "FengMin"));
        request.setKillers(List.of("GhostFace", "Legion", "Onryo"));

        GameStateResponse response = trialService.iniciarPartidaManual(request);

        assertNotNull(response);

        // Los supervivientes no se escalan (tienen sus valores base, ej. Leon = 105 de vida max)
        Personaje leon = response.getSupervivientes().stream()
                .filter(p -> "Leon Kennedy".equals(p.getNombrePersonaje()))
                .findFirst().orElseThrow();
        assertEquals(105, leon.getVidaMax());

        // Los asesinos se escalan (ej. Ghost Face base es 150 HP, escalado a extremo por 1.4 es 210 HP)
        Personaje gf = response.getKillers().stream()
                .filter(p -> "Ghost Face".equals(p.getNombrePersonaje()))
                .findFirst().orElseThrow();
        assertTrue(gf.getVidaMax() > 100, "Ghost Face vidaMax debería estar escalada por encima del base de 100");
        assertEquals(210, gf.getVidaMax());
    }

    @Test
    public void testEjecutarAccionAtacar() {
        // Iniciar una partida primero
        CharacterSelectionRequest initReq = new CharacterSelectionRequest();
        initReq.setModo("manual");
        initReq.setBando("survivientes");
        initReq.setDificultad("normal");
        initReq.setSupervivientes(List.of("LeonKennedy", "SteveHarrington", "FengMin"));
        initReq.setKillers(List.of("GhostFace", "Legion", "Onryo"));
        trialService.iniciarPartidaManual(initReq);

        // Atacar de superviviente a asesino
        ActionRequest actionReq = new ActionRequest();
        actionReq.setTipoAccion("ATACAR");
        actionReq.setAtacanteIndex(0); // Leon Kennedy (superviviente 0)
        actionReq.setObjetivoIndex(0);  // GhostFace (asesino 0)

        // Registrar vida inicial del objetivo
        GameStateResponse stateBefore = trialService.getState();
        int vidaInicial = stateBefore.getKillers().get(0).getVidaActual();

        GameStateResponse response = trialService.ejecutarAccion(actionReq);

        assertNotNull(response);
        int vidaFinal = response.getKillers().get(0).getVidaActual();
        assertTrue(vidaFinal < vidaInicial, "El objetivo debería haber recibido daño");
    }
}
