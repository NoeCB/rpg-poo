package com.dbd.habilidades.survis;

import com.dbd.core.UtilEstados;
import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class CirculoDeCuracion extends Perk {

    public CirculoDeCuracion() {
        super("Círculo de Curación", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " invoca un místico Círculo de Curación.");
        // Usa la nueva mejora UtilEstados para limpieza rápida y fuerte
        UtilEstados.purificarEstados(caster);
        int cura = 30;
        caster.setVidaActual(Math.min(caster.getVidaActual() + cura, caster.getVidaMax()));
        System.out.println("Efectos nocivos limpiados y vida aumentada masivamente.");
    }
}
