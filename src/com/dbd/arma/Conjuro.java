package com.dbd.arma;

public class Conjuro extends Arma {
    
    public Conjuro() {
<<<<<<< HEAD
        super("Conjuro de Sable", 40, 70);
    }
    @Override
    public void usar() {
        System.out.println("Sable ha lanzado un conjuro con daño de " + this.danio);
=======
        // Ahora le pasamos los 3 datos: Nombre, Daño Base y Precisión 
        super("Conjuro Oscuro", 20, 80);
    }
    
    @Override
    public void usar() {
        // Aprovechamos para mostrar también la precisión en el texto
        System.out.println("Lanzando " + this.nombreArma + " con un poder de " + this.danioBase + " y una precisión de " + this.precision + "%.");
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
    }
}