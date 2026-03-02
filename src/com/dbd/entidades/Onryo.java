package com.dbd.entidades;
import com.dbd.arma.VinculoDeCondena;

import java.util.Random;
import com.dbd.arma.Arma;

public class Onryo extends Personaje {
    private VinculoDeCondena vinculoDeCondena;  
    public Onryo(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Onryo", 220, 20, 30);
        this.vinculoDeCondena = new VinculoDeCondena();
    }

    @Override
    public void accion() {
      Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.vinculoDeCondena.getPrecision()) {
            System.out.println("Onryo ha atacado con su arma.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
    }
}
