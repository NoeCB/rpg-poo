package com.dbd.core;

import com.dbd.entidades.Personaje;
import com.dbd.entidades.LeonKennedy;
import com.dbd.entidades.SableWard;
import java.util.ArrayList;

public class MotorTrial {

    private ArrayList<Personaje> supervivientes = new ArrayList<>();
    private ArrayList<Personaje> enemigos = new ArrayList<>();

    public void iniciar() {
        System.out.println("Bienvenido al juego de Dead by Daylight");
        System.out.println("El juego consiste en un RPG por turnos con tres supervivientes y tres killers");
        System.out.println("Primero elige los supervivientes ");
        
        supervivientes.add(new LeonKennedy());
        enemigos.add(new SableWard());
        
        System.out.println("--PERSONAJES CARGADOS--");
    }

    public void iniciarJuego() {
        System.out.println("=== INICIO DE LA PRUEBA ===");
        
        Personaje leon = supervivientes.get(0);
        Personaje sable = enemigos.get(0);

        System.out.println("\n>>> FASE DE ACCIONES <<<");
        // Turno de Sable
        sable.accion();
        sable.getPerks().get(0).lanzar(sable, leon); // Sable lanza Maldición a Leon
        
        // Turno de Leon
        System.out.println();
        leon.accion();
        leon.getPerks().get(0).lanzar(leon, sable); // Leon lanza Granada a Sable

        System.out.println("\n>>> FASE DE ESTADOS (Pasa el tiempo) <<<");
        // Aquí es donde el veneno quita vida automáticamente
        leon.procesarEstados();
        sable.procesarEstados();

        System.out.println("\n>>> RESUMEN FIN DE RONDA <<<");
        System.out.println(leon.getNombrePersonaje() + ": " + leon.getVidaActual() + " HP");
        System.out.println(sable.getNombrePersonaje() + ": " + sable.getVidaActual() + " HP");
    }
}