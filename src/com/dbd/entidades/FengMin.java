package com.dbd.entidades;
import java.util.Random;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;
public class FengMin extends Personaje {
    private Arma cuchillo;
    public FengMin(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Feng" + " " + "Min", 100, 10, 12);
        this.cuchillo = new Cuchillo("Cuchillo de Caza", 10, 80);
    }

    @Override
    public void accion() {
    Random rd = new Random();
        int impacto = rd.nextInt(1,101);
        if(impacto <= this.cuchillo.getPrecision()) {
            System.out.println("Feng Min ha atacado con su arma.");
        } else {
            System.out.println(this.nombrePersonaje + " ha fallado su ataque.");
        }
    }
}