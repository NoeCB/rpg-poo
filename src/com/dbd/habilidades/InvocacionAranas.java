package com.dbd.habilidades;
import com.dbd.entidades.Personaje;
import com.dbd.estados.Maldicion;

public class InvocacionAranas extends Perk {
    public InvocacionAranas() { super("Arañas Tejedoras", 4); }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " maldice a " + objetivo.getNombrePersonaje() + ".");
        objetivo.aplicarEstado(new Maldicion(3)); // 3 Turnos
    }
}