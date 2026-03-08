package com.dbd.entidades;

import java.util.Random;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;

public class FengMin extends Personaje {
    public FengMin() {
        super("Feng Min", 100, 100, 10, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " analiza su entorno con concentración gamer.");
    }
}