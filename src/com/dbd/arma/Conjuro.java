package com.dbd.arma;

public class Conjuro extends Arma {
    
    public Conjuro() {
        // Ahora le pasamos los 3 datos: Nombre, Daño Base y Precisión 
        super("Conjuro Oscuro", 20, 80);
    }
    
    @Override
    public void usar() {
        // Aprovechamos para mostrar también la precisión en el texto
        System.out.println("Lanzando " + this.nombreArma + " con un poder de " + this.danioBase + " y una precisión de " + this.precision + "%.");
    }
}