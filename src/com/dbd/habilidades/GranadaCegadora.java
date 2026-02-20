package com.dbd.habilidades;
import com.dbd.entidades.Personaje;

public class GranadaCegadora extends Perk {
    public GranadaCegadora() { super("Granada Cegadora", 3); }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " lanza una Granada a " + objetivo.getNombrePersonaje() + "!");
        objetivo.setVidaActual(objetivo.getVidaActual() - 25);
    }
}