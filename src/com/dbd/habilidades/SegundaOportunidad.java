package com.dbd.habilidades;
import com.dbd.entidades.Personaje;
import com.dbd.estados.Regeneracion;

public class SegundaOportunidad extends Perk {
    public SegundaOportunidad() { super("Segunda Oportunidad", 3); }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " da Regeneración a " + objetivo.getNombrePersonaje() + ".");
        objetivo.aplicarEstado(new Regeneracion(3)); // 3 Turnos
    }
}