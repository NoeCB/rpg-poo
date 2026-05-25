package com.dbd.core;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dbd.entidades.GhostFace;
import com.dbd.entidades.LeonKennedy;
import com.dbd.entidades.Personaje;

public class MotorTrialTest {

    private MotorTrial motorTrial;

    @BeforeEach
    public void setUp() {
        motorTrial = new MotorTrial();
    }

    // --- 1. CONFIGURACIÓN Y LIMPIEZA ---
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

    // --- 2. CONTROL DE LÍMITES DE COMBATIENTES (Sinergia con la selección de la Web) ---
    @Test
    public void testLimitesDePersonajesMínimoYMáximo() {
        // CASO A: Probar el mínimo absoluto para jugar bien (1 Superviviente y 1 Killer)
        ArrayList<Personaje> minSurvis = new ArrayList<>();
        minSurvis.add(new LeonKennedy());
        ArrayList<Personaje> minKillers = new ArrayList<>();
        minKillers.add(new GhostFace());

        motorTrial.iniciarWeb(minSurvis, minKillers);
        assertEquals(1, motorTrial.getSupervivientes().size(), "El motor debe aceptar un mínimo de 1 superviviente.");
        assertEquals(1, motorTrial.getKillers().size(), "El motor debe aceptar un mínimo de 1 killer.");

        // CASO B: Probar el límite estándar de la simulación automática (3vs3)
        motorTrial.configurarPartida(); // Reseteamos la mesa
        
        ArrayList<Personaje> maxSurvis = new ArrayList<>();
        maxSurvis.add(new LeonKennedy());
        maxSurvis.add(new LeonKennedy());
        maxSurvis.add(new LeonKennedy());

        ArrayList<Personaje> maxKillers = new ArrayList<>();
        maxKillers.add(new GhostFace());
        maxKillers.add(new GhostFace());
        maxKillers.add(new GhostFace());

        motorTrial.iniciarWeb(maxSurvis, maxKillers);
        
        // El test obliga a que las listas tengan exactamente 3 integrantes por bando
        assertEquals(3, motorTrial.getSupervivientes().size(), "La partida de consola/automatizada requiere exactamente 3 supervivientes.");
        assertEquals(3, motorTrial.getKillers().size(), "La partida de consola/automatizada requiere exactamente 3 killers.");
    }

    // --- 3. REPARTO DE EQUIPAMIENTO ---
    @Test
    public void testIniciarWebYRepartoEquipamiento() {
        ArrayList<Personaje> survis = new ArrayList<>();
        survis.add(new LeonKennedy());
        ArrayList<Personaje> killers = new ArrayList<>();
        killers.add(new GhostFace());

        motorTrial.iniciarWeb(survis, killers);

        // Verificar que el personaje sacado del motor tiene herramientas asignadas
        Personaje leon = motorTrial.getSupervivientes().get(0);
        assertNotNull(leon.getArma(), "Leon debería tener un arma asignada automáticamente.");
        assertFalse(leon.getPerks().isEmpty(), "Leon debería tener perks (habilidades) en su inventario.");

        Personaje gf = motorTrial.getKillers().get(0);
        assertNotNull(gf.getArma(), "GhostFace debería tener un arma asignada automáticamente.");
        assertFalse(gf.getPerks().isEmpty(), "GhostFace debería tener perks asignadas.");
    }

    // --- 4. VALIDACIÓN DE CREDENCIALES (Lo que Selenium escribe en la pantalla) ---
    @Test
    public void testValidacionCredencialesLogin() {
        // Simulamos los datos válidos que Selenium metería en los inputs
        String usuarioCorrecto = "user_test_1";
        String claveCorrecta = "password123";

        // Registramos y autenticamos usando la lógica simulada del motor
        motorTrial.registrarUsuario(usuarioCorrecto, claveCorrecta);

        boolean accesoConcedido = motorTrial.autenticar(usuarioCorrecto, claveCorrecta);
        assertTrue(accesoConcedido, "El motor debería permitir el acceso con las credenciales correctas.");

        boolean accesoDenegado = motorTrial.autenticar("usuario_falso", "clave_erronea");
        assertFalse(accesoDenegado, "El motor debería rechazar credenciales incorrectas (Evita fallos de seguridad).");
    }

    // --- 5. GETTERS Y SETTERS DE ESTADO ---
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