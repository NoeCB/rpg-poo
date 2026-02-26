package com.dbd.habilidades;
import com.dbd.entidades.Personaje;

public class Alerta extends Perk {
    public Alerta() { super("Alerta", 2); }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("🛠️ " + caster.getNombrePersonaje() + " cura a " + objetivo.getNombrePersonaje() + " al instante.");
        objetivo.setVidaActual(objetivo.getVidaActual() + 20);
    }
}