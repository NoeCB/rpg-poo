package com.dbd.entidades;

import java.util.Random;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;

public class Legion extends Personaje {
    public Legion() {
        super("Legion", 180, 180, 25, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " entra en frenesí y empieza a correr hacia ti.");
    }
}