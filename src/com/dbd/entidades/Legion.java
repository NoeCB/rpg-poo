package com.dbd.entidades;
import java.util.Random;
import com.dbd.arma.Cuchillo;
import com.dbd.arma.Arma;
public class Legion extends Personaje {
<<<<<<< HEAD
    private Cuchillo cuchillo;

    public Legion(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        super("Legion", 180, 25, 35);
        this.cuchillo = new Cuchillo();

=======
    public Legion() {
        super("Legion", 180, 180, 25, 0);
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
=======
        System.out.println(this.nombrePersonaje + " entra en frenesí y empieza a correr hacia ti.");
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
    }
}