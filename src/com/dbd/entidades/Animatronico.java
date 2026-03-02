package com.dbd.entidades;
import com.dbd.arma.Arma;
import java.util.Random;

import com.dbd.arma.Hacha;

public class Animatronico extends Personaje {
    private Hacha hacha;
    public Animatronico(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Animatrónico", 220, 20, 30);
        this.hacha = new Hacha("Hacha", 10, 80);
    }

    @Override
    public void accion() {
      Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.hacha.getPrecision()) {
            System.out.println("Animatrónico ha atacado con su arma.");
        } else {
            System.out.println("Animatrónico ha fallado su ataque.");
        }
    }
    }
