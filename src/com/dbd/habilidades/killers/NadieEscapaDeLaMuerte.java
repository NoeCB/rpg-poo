package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class NadieEscapaDeLaMuerte extends Perk {
    
    public NadieEscapaDeLaMuerte() {
        super("Maleficio: NOED", 1); // Solo 1 uso, pero letal
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡Se han reparado todos los motores! " + caster.getNombrePersonaje() + " activa NOED.");
        System.out.println("¡Aumenta su velocidad de movimiento y todos quedan EXPUESTOS!");
        
        // Ataque crítico masivo y aumento de defensa/velocidad
        caster.setDefensaBase(caster.getDefensaBase() + 20);
        objetivo.recibirDanio(55); 
        
        System.out.println(objetivo.getNombrePersonaje() + " recibe un golpe crítico devastador. ¡La muerte acecha!");
    }
}