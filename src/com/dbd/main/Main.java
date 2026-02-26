package com.dbd.main;

import com.dbd.core.MotorTrial;

public class Main {
    public static void main(String[] args) {
        MotorTrial prueba = new MotorTrial();
        prueba.iniciar();       // Cambio de configurarPartida() a iniciar()
        prueba.iniciarJuego();
    }
}