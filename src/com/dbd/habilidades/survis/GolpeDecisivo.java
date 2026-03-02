package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class GolpeDecisivo extends Perk {
    
    public GolpeDecisivo() {
        super("Golpe Decisivo", 1); // Solo se permite un uso por partida
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " acierta el test de habilidad y clava un cristal a " + objetivo.getNombrePersonaje() + "!");
        
        // Daño directo al Killer y el Superviviente se defiende
        objetivo.recibirDanio(40);
        caster.setDefendiendo(true);
        
        System.out.println(objetivo.getNombrePersonaje() + " queda aturdido por el golpe. " + caster.getNombrePersonaje() + " se pone en guardia.");
    }
}