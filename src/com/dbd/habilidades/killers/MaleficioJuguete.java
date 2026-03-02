package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class MaleficioJuguete extends Perk {
    
    public MaleficioJuguete() {
        super("Maleficio: Juguete", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " maldice a su víctima con Maleficio: Juguete.");
        
        // El superviviente pierde la noción del peligro (pierde defensa)
        int defensaPerdida = Math.max(0, objetivo.getDefensaBase() - 15);
        objetivo.setDefensaBase(defensaPerdida);
        
        System.out.println("¡" + objetivo.getNombrePersonaje() + " sufre el estado INCONSCIENTE! Su defensa cae en picado.");
        objetivo.recibirDanio(15);
    }
}