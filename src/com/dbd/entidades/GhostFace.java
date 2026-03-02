package com.dbd.entidades;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;

import java.util.Random;
public class GhostFace extends Personaje {
    private Arma cuchillo;
    public GhostFace(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Ghost" + " " + "Face", 150, 15, 25);
        this.cuchillo = new Cuchillo();
    }

    @Override
    public void accion() {
      Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.cuchillo.getPrecision()) {
            System.out.println("Ghost Face ha atacado con su arma.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
    }
