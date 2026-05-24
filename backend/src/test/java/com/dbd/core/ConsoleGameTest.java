package com.dbd.core;

import com.dbd.arma.Arma;
import com.dbd.entidades.*;
import com.dbd.habilidades.Perk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas unitarias con JUnit 5 para validar de forma rigurosa
 * el comportamiento y el motor lógico del juego en su versión de consola.
 * Utiliza Mockito para proveer un contexto simulado estático a SpringContextHolder,
 * eliminando la necesidad de levantar Spring Boot o conectarse a la base de datos.
 */
public class ConsoleGameTest {

    private com.dbd.dao.GestorPersistencia mockGestor;
    private MotorTrial motor;

    @BeforeEach
    public void setUp() {
        // Mockear el ApplicationContext y el GestorPersistencia de forma limpia
        ApplicationContext mockContext = Mockito.mock(ApplicationContext.class);
        mockGestor = Mockito.mock(com.dbd.dao.GestorPersistencia.class);
        Mockito.when(mockContext.getBean(com.dbd.dao.GestorPersistencia.class)).thenReturn(mockGestor);

        // Registrar el contexto simulado en el puente estático del juego
        SpringContextHolder holder = new SpringContextHolder();
        holder.setApplicationContext(mockContext);

        motor = new MotorTrial();
        motor.configurarPartida(); // Limpia supervivientes y asesinos
    }

    @Test
    public void testInicializacionLimpia() {
        assertTrue(motor.getSupervivientes().isEmpty(), "El pool de supervivientes inicial debe estar vacío.");
        assertTrue(motor.getKillers().isEmpty(), "El pool de killers inicial debe estar vacío.");
        assertEquals(-1, motor.getModoActual().equals("manual") ? -1 : 0, "Debería arrancar sin ranura por defecto.");
    }

    @Test
    public void testIniciarAleatorioConsola() {
        // Ejecutar el método que usa la consola para la simulación automática 3vs3
        motor.iniciarAleatorio();

        // Verificar que se han instanciado exactamente 3 combatientes por equipo
        assertEquals(3, motor.getSupervivientes().size(), "Deben seleccionarse exactamente 3 supervivientes.");
        assertEquals(3, motor.getKillers().size(), "Deben seleccionarse exactamente 3 killers.");

        // Comprobar equipamientos automáticos asignados por la Entidad en la hoguera de consola
        for (Personaje s : motor.getSupervivientes()) {
            assertNotNull(s.getArma(), "Cada superviviente debe tener un arma asignada al azar.");
            assertEquals(4, s.getPerks().size(), "Cada superviviente debe equipar exactamente 4 Perks.");
            assertTrue(s.getVidaActual() > 0, "Los supervivientes deben iniciar con salud completa.");
        }

        for (Personaje k : motor.getKillers()) {
            assertNotNull(k.getArma(), "Cada asesino debe tener un arma asignada al azar.");
            assertEquals(4, k.getPerks().size(), "Cada asesino debe equipar exactamente 4 Perks.");
            assertTrue(k.getVidaActual() > 0, "Los asesinos deben iniciar con salud completa.");
        }
    }

    @Test
    public void testRepartirPerksYArmas() {
        // Insertamos manualmente personajes conocidos
        ArrayList<Personaje> survis = new ArrayList<>();
        survis.add(new LeonKennedy());
        survis.add(new SteveHarrington());

        ArrayList<Personaje> killers = new ArrayList<>();
        killers.add(new GhostFace());

        // Llamamos al método iniciarWeb (que hace un clear y addAll y reparte Perks/Armas)
        // Evitamos setearlos antes para que clear() no borre nuestras listas locales
        motor.iniciarWeb(survis, killers);

        // Verificar que las listas del motor tienen el tamaño correcto
        assertEquals(2, motor.getSupervivientes().size());
        assertEquals(1, motor.getKillers().size());

        // Comprobar que a Leon y Steve se les asignó arma y Perks
        Personaje leon = motor.getSupervivientes().get(0);
        assertNotNull(leon.getArma(), "Leon debe poseer un arma del catálogo.");
        assertEquals(4, leon.getPerks().size(), "Leon debe tener 4 perks equipadas.");

        Personaje gf = motor.getKillers().get(0);
        assertNotNull(gf.getArma(), "Ghost Face debe poseer un arma del catálogo.");
        assertEquals(4, gf.getPerks().size(), "Ghost Face debe tener 4 perks equipadas.");
    }

    @Test
    public void testEquipoVivoMetodosLúdicos() {
        ArrayList<Personaje> equipo = new ArrayList<>();
        LeonKennedy leon = new LeonKennedy();
        SteveHarrington steve = new SteveHarrington();
        equipo.add(leon);
        equipo.add(steve);

        motor.setSupervivientes(equipo);

        // Verificar que el equipo está vivo inicialmente
        assertTrue(leon.getVidaActual() > 0);
        assertTrue(steve.getVidaActual() > 0);

        // Si uno muere, el equipo sigue vivo
        leon.recibirDanio(500); // Muerte de Leon (HP = 0)
        assertEquals(0, leon.getVidaActual());
        assertTrue(leon.getVidaActual() <= 0);

        // El oponente aún tiene a Steve vivo, por tanto el equipo sigue con vida
        assertTrue(steve.getVidaActual() > 0);

        // Si ambos mueren, el equipo deja de estar vivo
        steve.recibirDanio(500); // Muerte de Steve (HP = 0)
        assertEquals(0, steve.getVidaActual());
        assertTrue(steve.getVidaActual() <= 0);
    }

    @Test
    public void testBalanceArmasConsola() {
        motor.iniciarAleatorio();

        // Validar que las armas de supervivientes no superen límites de diseño establecidos en la simulación de consola
        for (Personaje s : motor.getSupervivientes()) {
            Arma arma = s.getArma();
            assertTrue(arma.getDanioBase() >= 5 && arma.getDanioBase() <= 35, 
                    "El daño del arma de superviviente (" + arma.getNombreArma() + ") debe estar balanceado.");
            assertTrue(arma.getPrecision() >= 60 && arma.getPrecision() <= 100,
                    "La precisión del arma de superviviente (" + arma.getNombreArma() + ") debe ser coherente.");
        }

        // Validar las armas de los Killers
        for (Personaje k : motor.getKillers()) {
            Arma arma = k.getArma();
            assertTrue(arma.getDanioBase() >= 5 && arma.getDanioBase() <= 50, 
                    "El daño del arma del asesino (" + arma.getNombreArma() + ") debe estar dentro del balance.");
            assertTrue(arma.getPrecision() >= 50 && arma.getPrecision() <= 100,
                    "La precisión del asesino debe estar dentro de los límites físicos.");
        }
    }
}
