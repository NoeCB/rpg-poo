package com.dbd.entidades;

import com.dbd.arma.Arma;
import com.dbd.arma.Pistola;

public class LeonKennedy extends Personaje {
    public LeonKennedy() {
        super("Leon Kennedy", 105, 105, 15, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " revisa su equipo y se prepara para correr.");
    }
}