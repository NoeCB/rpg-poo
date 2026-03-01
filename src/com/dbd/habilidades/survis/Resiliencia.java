package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Resiliencia extends Perk {
    
    public Resiliencia() {
        super("Resiliencia", 3); // Puede usarse más veces al ser pasiva/activa
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        double porcentajeVida = (double) caster.getVidaActual() / caster.getVidaMax();
        
        if (porcentajeVida <= 0.50) {
            System.out.println(caster.getNombrePersonaje() + " canaliza su dolor con Resiliencia.");
            System.out.println("¡Al estar herido, actúa más rápido y lanza un ataque desesperado!");
            
            // Hace un daño fijo y se prepara para el golpe
            objetivo.recibirDanio(20);
            caster.setDefendiendo(true);
        } else {
            System.out.println(caster.getNombrePersonaje() + " está demasiado sano para aprovechar Resiliencia.");
        }
    }
}