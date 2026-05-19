package com.dbd.core;

import com.dbd.entidades.LeonKennedy;
import com.dbd.entidades.GhostFace;
import com.dbd.entidades.Personaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MotorTrialTest {

    private MotorTrial motorTrial;

    @BeforeEach
    public void setUp() {
        motorTrial = new MotorTrial();
    }

    @Test
    public void testConfigurarPartida() {
        ArrayList<Personaje> survis = new ArrayList<>();
        survis.add(new LeonKennedy());
        ArrayList<Personaje> killers = new ArrayList<>();
        killers.add(new GhostFace());

        motorTrial.setSupervivientes(survis);
        motorTrial.setKillers(killers);

        assertEquals(1, motorTrial.getSupervivientes().size());
        assertEquals(1, motorTrial.getKillers().size());

        motorTrial.configurarPartida();

        assertTrue(motorTrial.getSupervivientes().isEmpty());
        assertTrue(motorTrial.getKillers().isEmpty());
    }

    @Test
    public void testIniciarWeb() {
        ArrayList<Personaje> survis = new ArrayList<>();
        survis.add(new LeonKennedy());
        ArrayList<Personaje> killers = new ArrayList<>();
        killers.add(new GhostFace());

        motorTrial.iniciarWeb(survis, killers);

        assertEquals(1, motorTrial.getSupervivientes().size());
        assertEquals(1, motorTrial.getKillers().size());

        // Verificar que repartió Perks y Armas
        Personaje leon = motorTrial.getSupervivientes().get(0);
        assertNotNull(leon.getArma(), "Leon debería tener un arma asignada");
        assertFalse(leon.getPerks().isEmpty(), "Leon debería tener perks asignadas");

        Personaje gf = motorTrial.getKillers().get(0);
        assertNotNull(gf.getArma(), "GhostFace debería tener un arma asignada");
        assertFalse(gf.getPerks().isEmpty(), "GhostFace debería tener perks asignadas");
    }

    @Test
    public void testSetAndGetDificultadYModo() {
        motorTrial.setDificultadActual("extremo");
        assertEquals("extremo", motorTrial.getDificultadActual());

        motorTrial.setBandoActual("killers");
        assertEquals("killers", motorTrial.getBandoActual());

        motorTrial.setIdRanuraActual(2, "automatico");
        assertEquals("automatico", motorTrial.getModoActual());
    }
}
