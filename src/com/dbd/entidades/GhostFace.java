
package com.dbd.entidades;

import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;

import java.util.Random;

public class GhostFace extends Personaje {
    public GhostFace() {
        super("Ghost Face", 150, 150, 15, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " se asoma por una esquina acechando en silencio...");
    }
}
