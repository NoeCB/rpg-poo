package com.dbd.entidades;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;

import java.util.Random;
public class GhostFace extends Personaje {
<<<<<<< HEAD
    private Arma cuchillo;
    public GhostFace(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Ghost" + " " + "Face", 150, 15, 25);
        this.cuchillo = new Cuchillo();
=======
    public GhostFace() {
        super("Ghost Face", 150, 150, 15, 0);
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
    }

    @Override
    public void accion() {
<<<<<<< HEAD
      Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.cuchillo.getPrecision()) {
            System.out.println("Ghost Face ha atacado con su arma.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
=======
        System.out.println(this.nombrePersonaje + " se asoma por una esquina acechando en silencio...");
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
    }
