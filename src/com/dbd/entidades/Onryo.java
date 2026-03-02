package com.dbd.entidades;
import com.dbd.arma.VinculoDeCondena;

import java.util.Random;
import com.dbd.arma.Arma;

public class Onryo extends Personaje {
<<<<<<< HEAD
    private VinculoDeCondena vinculoDeCondena;  
    public Onryo(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Onryo", 220, 20, 30);
        this.vinculoDeCondena = new VinculoDeCondena();
=======
    public Onryo() {
        super("Onryo", 220, 220, 20, 0);
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
    }

    @Override
    public void accion() {
<<<<<<< HEAD
      Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.vinculoDeCondena.getPrecision()) {
            System.out.println("Onryo ha atacado con su arma.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
=======
        System.out.println(this.nombrePersonaje + " sale de un televisor cercano.");
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
    }
}