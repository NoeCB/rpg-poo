package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

public class AutoCuracion extends Perk {
    private int cantidadCura;

    public AutoCuracion(){
        super("Autocuracion", 3);
        this.cantidadCura = 20; // Cantidad de vida que puede recuperarse
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo){
        // Usamos getNombrePersonaje() en lugar de .nombre
        System.out.println(caster.getNombrePersonaje() + " usa Autocuración en " + objetivo.getNombrePersonaje() + ".");
        
        // Sumamos la cura a la vida actual del personaje usando los getters y setters
        int nuevaVida = objetivo.getVidaActual() + cantidadCura;
        objetivo.setVidaActual(nuevaVida);
        
        System.out.println(objetivo.getNombrePersonaje() + " recupera " + cantidadCura + " puntos de vida instantáneamente. (Vida actual: " + objetivo.getVidaActual() + ")");
    }
}