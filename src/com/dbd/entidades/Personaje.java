package com.dbd.entidades;

import com.dbd.estados.Estado;
import com.dbd.habilidades.Perk;
import java.util.ArrayList;

public abstract class Personaje {
    // Códigos de color ANSI
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String MORADO = "\u001B[35m";
    public static final String AMARILLO = "\u001B[33m";

    protected String nombrePersonaje;
    protected int vidaActual;
    protected int vidaMax;
    protected int defensaBase;
    protected int puntosSangre;

    protected ArrayList<Estado> estados;
    protected ArrayList<Perk> perks;

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

    public void addPerk(Perk p) {
        this.perks.add(p);
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getVidaMax() {
        return this.vidaMax;
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

    public void recibirDanio(int danioBruto) {
        int danioFinal = danioBruto;
        if (this.defendiendo) {
            danioFinal = danioBruto / 2;
            System.out.println(CYAN + this.nombrePersonaje + " amortigua el golpe." + RESET);
            this.defendiendo = false;
        }

        this.vidaActual -= danioFinal;
        if (this.vidaActual < 0)
            this.vidaActual = 0;

        System.out.println(ROJO + this.nombrePersonaje + " recibe " + danioFinal + " de daño. (Vida: "
                + this.vidaActual + "/" + this.vidaMax + ")" + RESET);
    }

    public void decidirAccionIA(ArrayList<Personaje> aliados, ArrayList<Personaje> enemigos) {
        this.defendiendo = false;

        Personaje objetivo = null;
        for (Personaje e : enemigos) {
            if (e.getVidaActual() > 0) {
                if (objetivo == null || e.getVidaActual() < objetivo.getVidaActual())
                    objetivo = e;
            }
        }
        if (objetivo == null)
            return;

        ArrayList<Perk> perksDisponibles = new ArrayList<>();
        for (Perk p : this.perks) {
            if (p.getUsos() > 0)
                perksDisponibles.add(p);
        }

        int dado = (int) (Math.random() * 100);

        if (!perksDisponibles.isEmpty() && dado < 40) {
            Perk perkElegida = perksDisponibles.get((int) (Math.random() * perksDisponibles.size()));
            System.out.println(
                    MORADO + "\n [" + this.nombrePersonaje + "] se prepara para usar una habilidad..." + RESET);
            perkElegida.lanzar(this, objetivo);
            perkElegida.consumirUso();
        } else if (dado < 80) {
            atacarBasico(objetivo);
        } else {
            System.out.println(CYAN + this.nombrePersonaje + " se pone en guardia." + RESET);
            this.defendiendo = true;
        }
    }

    private void atacarBasico(Personaje rival) {
        int danioGenerado = (int) (Math.random() * 16) + 15;
        System.out.println(AMARILLO + this.nombrePersonaje + " lanza un ataque básico directo a "
                + rival.getNombrePersonaje() + "!" + RESET);
        rival.recibirDanio(danioGenerado);
    }
}