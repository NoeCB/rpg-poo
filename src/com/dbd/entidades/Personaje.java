package com.dbd.entidades;

import com.dbd.estados.Estado;
import com.dbd.habilidades.Perk;
import com.dbd.arma.Arma;
import com.dbd.core.Util;
import java.util.ArrayList;

public abstract class Personaje {

    protected String nombrePersonaje;
    protected int vidaActual;
    protected int vidaMax;
    protected int defensaBase;
    protected int puntosSangre;

    protected ArrayList<Estado> estados;
    protected ArrayList<Perk> perks;
    protected Arma arma;

    protected boolean defendiendo = false;

    public Personaje(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase, int puntosSangre) {
        this.nombrePersonaje = nombrePersonaje;
        this.vidaActual = vidaActual;
        this.vidaMax = vidaMax;
        this.defensaBase = defensaBase;
        this.puntosSangre = puntosSangre;
        this.estados = new ArrayList<>();
        this.perks = new ArrayList<>();
    }

    // Identificador rápido para saber de qué bando es (Los Survis tienen menos de
    // 130 de vida máxima)
    private boolean esSuperviviente() {
        return this.vidaMax <= 130;
    }

    public void addPerk(Perk p) {
        this.perks.add(p);
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public int getVidaActual() {
        return this.vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getVidaMax() {
        return this.vidaMax;
    }

    public Arma getArma() {
        return this.arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public ArrayList<Perk> getPerks() {
        return perks;
    }

    public int getDefensaBase() {
        return this.defensaBase;
    }

    public void setDefensaBase(int nuevaDefensa) {
        this.defensaBase = nuevaDefensa;
    }

    public void setDefendiendo(boolean defendiendo) {
        this.defendiendo = defendiendo;
    }

    public boolean isDefendiendo() {
        return this.defendiendo;
    }

    public void aplicarEstado(Estado nuevoEstado) {
        this.estados.add(nuevoEstado);
    }

    public void procesarEstados() {
        for (int i = estados.size() - 1; i >= 0; i--) {
            Estado e = estados.get(i);
            e.aplicarEfecto(this);
            if (e.getTurnosRestantes() <= 0)
                estados.remove(i);
        }
    }

    public abstract void accion();

    // --- 1. SISTEMA DE DAÑO REPARADO Y ASIMÉTRICO ---
    public void recibirDanio(int danioBruto) {
        // Evasión: 20% para survis, 5% para killers
        double probEvasion = 0.05;
        if (esSuperviviente()) {
            probEvasion = 0.20;
        }

        // Si no se está defendiendo y salta la evasión
        if (!this.defendiendo && Math.random() < probEvasion) {
            System.out.println(
                    Util.VERDE + " ¡" + this.nombrePersonaje + " ha ESQUIVADO el ataque por completo!" + Util.RESET);
            return;
        }

        int danioFinal = danioBruto;

        // La defensa bloquea el 50% (75% era demasiado OP para Ghost Face --> Puesto en
        // versiones anteriores)
        if (this.defendiendo) {
            danioFinal = danioBruto / 2;
            System.out.println(Util.CYAN + this.nombrePersonaje + " realiza un bloqueo perfecto." + Util.RESET);
            this.defendiendo = false; // Se gasta al recibir el golpe
        }

        // Armadura
        danioFinal -= (this.defensaBase / 5);

        // El daño NUNCA puede ser menor de 5
        if (danioFinal < 5)
            danioFinal = 5;

        this.vidaActual -= danioFinal;
        if (this.vidaActual < 0)
            this.vidaActual = 0;

        System.out.println(Util.ROJO + this.nombrePersonaje + " recibe " + danioFinal + " de daño. (Vida: "
                + this.vidaActual + "/" + this.vidaMax + ")" + Util.RESET);
    }

    // --- 2. IA BALANCEADA ---
    public void decidirAccionIA(ArrayList<Personaje> aliados, ArrayList<Personaje> enemigos) {
        this.defendiendo = false;

        ArrayList<Personaje> enemigosVivos = new ArrayList<>();
        for (Personaje e : enemigos) {
            if (e.getVidaActual() > 0)
                enemigosVivos.add(e);
        }
        if (enemigosVivos.isEmpty())
            return;

        // Selección de objetivo (50/50 de ir al más débil o al azar, si no siempre
        // atacará al más débil)
        Personaje objetivo;
        if (Math.random() < 0.50) {
            objetivo = enemigosVivos.get(0);
            for (Personaje e : enemigosVivos) {
                if (e.getVidaActual() < objetivo.getVidaActual())
                    objetivo = e;
            }
        } else {
            objetivo = enemigosVivos.get((int) (Math.random() * enemigosVivos.size()));
        }

        ArrayList<Perk> perksDisponibles = new ArrayList<>();
        for (Perk p : this.perks) {
            if (p.getUsos() > 0)
                perksDisponibles.add(p);
        }

        int dado = (int) (Math.random() * 100);
        boolean enPeligro = (this.vidaActual <= this.vidaMax * 0.35);

        int probUsarPerk = 25;
        if (enPeligro) {
            probUsarPerk = 65;
        }

        if (!perksDisponibles.isEmpty() && dado < probUsarPerk) {
            Perk perkElegida = perksDisponibles.get((int) (Math.random() * perksDisponibles.size()));
            System.out.println(
                    Util.MORADO + "\n [" + this.nombrePersonaje + "] ejecuta una jugada maestra..." + Util.RESET);
            perkElegida.lanzar(this, objetivo);
            perkElegida.consumirUso();
        }
        // Se reduce la probabilidad de spamear la defensa para que la partida avance
        else if (enPeligro && dado > 60) {
            System.out.println(
                    Util.CYAN + this.nombrePersonaje + " adopta una postura defensiva desesperada." + Util.RESET);
            this.defendiendo = true;
        } else {
            atacarBasico(objetivo);
        }
    }

    // --- 3. ATAQUE NORMAL ASIMÉTRICO ---
    private void atacarBasico(Personaje rival) {
        int danioGenerado;

        if (this.arma != null) {
            // Ataque usando Arma
            int dadoPrecision = (int) (Math.random() * 100) + 1;

            System.out.println(
                    Util.AMARILLO + this.nombrePersonaje + " se prepara para usar su " + this.arma.getNombreArma()
                            + " contra " + rival.getNombrePersonaje() + "!" + Util.RESET);
            this.arma.usar();

            if (dadoPrecision <= this.arma.getPrecision()) {
                // El ataque acierta
                // Añadimos una ligera varianza al daño del arma para que no sea estático (+- 2)
                // Generar un número aleatorio entre -2 y 2
                int varianza = (int) (Math.random() * 5) - 2;
                danioGenerado = this.arma.getDanioBase() + varianza;
                System.out
                        .println(Util.VERDE + "¡El ataque con " + this.arma.getNombreArma() + " acierta de lleno!"
                                + Util.RESET);

                // Golpe crítico (20% de probabilidad)
                if (Math.random() < 0.20) {
                    System.out.println(Util.AMARILLO + "¡GOLPE CRÍTICO! ¡El daño se duplica!" + Util.RESET);
                    danioGenerado = danioGenerado * 2;
                }

                rival.recibirDanio(danioGenerado);
            } else {
                // El ataque falla
                System.out
                        .println(Util.ROJO + "¡El ataque con " + this.arma.getNombreArma() + " ha FALLADO! (Precisión: "
                                + this.arma.getPrecision() + "%)" + Util.RESET);
            }

        } else {
            // Ataque básico sin Arma (Desarmado)
            if (esSuperviviente()) {
                // Los survis pegan MUCHO MÁS FUERTE en grupo para compensar su baja salud (Daño
                // de 20 a 34)
                danioGenerado = (int) (Math.random() * 15) + 20;
            } else {
                // Los Killers tienen mucha vida, su ataque base es más sostenido (Daño de 12 a
                // 21)
                danioGenerado = (int) (Math.random() * 10) + 12;
            }

            System.out.println(Util.AMARILLO + this.nombrePersonaje + " lanza un ataque básico directo a "
                    + rival.getNombrePersonaje() + "!" + Util.RESET);

            // Golpe crítico también para los puñetazos (20% de probabilidad)
            if (Math.random() < 0.20) {
                System.out.println(
                        Util.AMARILLO + "¡GOLPE CRÍTICO! ¡El daño base se duplica con este golpazo!" + Util.RESET);
                danioGenerado = danioGenerado * 2;
            }

            rival.recibirDanio(danioGenerado);
        }
    }
}