package com.dbd.entidades;

import java.util.Random;
import com.dbd.arma.Conjuro;
import com.dbd.arma.Arma;

public class SableWard extends Personaje {
    private Conjuro conjuro;
    public SableWard(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Sable" + " " + "Ward", 250, 30, 50);
        this.conjuro = new Conjuro("Conjuro de Sable", 40, 70);
    }

    @Override
    public void accion() {
       Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.conjuro.getPrecision()) {
            System.out.println("Sable Ward ha atacado con su conjuro.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
    }

