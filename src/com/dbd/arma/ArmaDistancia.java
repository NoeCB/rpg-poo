package com.dbd.arma;

public class ArmaDistancia extends Arma {
    private int municionActual;
    private int capacidadMaxima;

    public ArmaDistancia(String nombreArma, int danioBase, int capacidadMaxima, int municionMaxima) {
        super(nombreArma, danioBase);
        this.capacidadMaxima = capacidadMaxima;
        this.municionActual = municionMaxima;
    }

    @Override
    public void usar() {
        if (municionActual > 0) {
            System.out.println("Disparando " + nombreArma + "!");
            municionActual--;
            // Aquí iría la lógica de calcular si impacta según el rango
        } else {
            System.out.println("¡Sin munición! Necesitas recargar.");
        }
    }

    public void recargar() {
        this.municionActual = capacidadMaxima;
        System.out.println("Arma recargada.");
    }

    public int getMunicionActual() {
        return municionActual;
    }
}