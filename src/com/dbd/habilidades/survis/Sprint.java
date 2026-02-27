package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Sprint extends Perk {
    
    public Sprint() {
        super("Sprint", 3); // 3 usos por partida
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " activa Sprint y sale corriendo a toda velocidad.");
        
        // Le recuperamos algo de vida (stamina) y lo ponemos en guardia
        caster.setVidaActual(caster.getVidaActual() + 10);
        caster.setDefendiendo(true); 
        
        System.out.println("Ha tomado distancia. Recupera 10 HP y está preparado para esquivar el próximo ataque.");
    }
}