package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

public class AutoCuracion extends Perk {
    private int cantidadCura;

    public AutoCuracion(){
        super("Autocuracion", 3);
        this.cantidadCura = 20; //Cantidad de vida que puede recuoperarse
    }

    @Override

    public void lanzar(Personaje caster, Personaje objetivo){
        System.out.println(caster.nombre + " usa " + this.nombre + " en " + objetivo.nombre + ".");
        
        // Aquí llamamos al método curar del personaje
        // (Asegúrate de tener un método curar(int cantidad) en tu clase Personaje)
        // objetivo.curar(cantidadCura); 
        
        System.out.println(objetivo.nombre + " recupera " + cantidadCura + " puntos de vida instantáneamente.");
    }

}

