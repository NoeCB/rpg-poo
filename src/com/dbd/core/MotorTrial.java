package com.dbd.core;

//importacion de todas las entidades 
import com.dbd.entidades.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MotorTrial {
    private ArrayList<Personaje> supervivientes = new ArrayList<>();
    private ArrayList<Personaje> enemigos = new ArrayList<>();

    // creacion de arraylist auxiliares para nombrar los personajes
    public void configurarPartida() {

        supervivientes.add(new LeonKennedy("Leon" + " " + "Kennedy", 100, 10, 20));
        supervivientes.add(new SableWard("Sable" + " " + "Ward", 120, 15, 25));
        supervivientes.add(new FengMin("Feng" + " " + "Min", 100, 10, 20));
        supervivientes.add(new SteveHarrington("Steve" + " " + "Harrington", 100, 10, 20));
        enemigos.add(new Onryo("Onryo", 120, 15, 25));
        enemigos.add(new Legion("Legion", 120, 15, 25));
        enemigos.add(new GhostFace("GhostFace", 120, 15, 25));
        enemigos.add(new Animatronico("Animatronico", 120, 15, 25));
    }

    // metodo para mostrar los supervivientes
    public void mostrarSupervivientes() {
        System.out.println("\nLISTA DE SUPERVIVIENTES");
        for (Personaje p : supervivientes) {
            System.out.println("- " + p.getNombrePersonaje() + " [Vida: " + p.getVidaBase() + "]");
        }
    }

    // metodo para mostrar los asesinos
    public void mostrarAsesinos() {
        System.out.println("\nLISTA DE ASESINOS");
        for (Personaje p : enemigos) {
            System.out.println("- " + p.getNombrePersonaje() + " [Daño: " + p.getDanioBase() + "]");
        }
    }




    // public void iniciarJuego() {
    //     System.out.println("=== INICIO DE LA PRUEBA ===");
    //     Personaje leon = supervivientes.get(0);
    //     Personaje sable = enemigos.get(0);

    //     System.out.println("\n>>> FASE DE ACCIONES <<<");
    //     // Turno de Sable
    //     sable.accion();
    //     sable.getPerks().get(0).lanzar(sable, leon); // Sable lanza Maldición a Leon

    //     // Turno de Leon
    //     System.out.println();
    //     leon.accion();
    //     leon.getPerks().get(0).lanzar(leon, sable); // Leon lanza Granada a Sable

    //     System.out.println("\n>>> FASE DE ESTADOS (Pasa el tiempo) <<<");
    //     // Aquí es donde el veneno quita vida automáticamente
    //     leon.procesarEstados();
    //     sable.procesarEstados();

    //     System.out.println("\n>>> RESUMEN FIN DE RONDA <<<");
    //     System.out.println(leon.getNombrePersonaje() + ": " + leon.getVidaActual() + " HP");
    //     System.out.println(sable.getNombrePersonaje() + ": " + sable.getVidaActual() + " HP");
    // }

    public void iniciarJuegoManual() {
        System.out.println("Elige tu superviviente:");
        for (Personaje p : supervivientes) {
            System.out.println("- " + p.getNombrePersonaje() + " [Vida: " + p.getVidaBase() + "]");
        }
       Personaje supervivienteElegido = elegirPersonaje(supervivientes);
    System.out.println("Has elegido a: " + supervivienteElegido.getNombrePersonaje());
        System.out.println("Elige tu asesino:");

       for(Personaje p : enemigos) {
        System.out.println(p.getNombrePersonaje() + " [Daño: " + p.getDanioBase() + "]");
       }
       Personaje asesinoElegido = elegirPersonaje(enemigos);
    System.out.println("Has elegido a: " + asesinoElegido.getNombrePersonaje());
        
        
    }
    private Personaje elegirPersonaje(ArrayList<Personaje> personaje) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Selecciona un personaje: ");
        int opcion = sc.nextInt();
        sc.nextLine(); 
        if (opcion >= 1 && opcion <= personaje.size()) {
            return personaje.get(opcion - 1);
        } else {
            System.out.println("Opción no válida, seleccionando el primer personaje por defecto.");
            return personaje.get(0);
        }
    }
}
    
