package com.dbd.arma;

/**
 * Representa la base (superclase) para cualquier tipo de armamento en el juego.
 * Al ser una clase abstracta, no se pueden crear instancias directas de ella.
 * * @author Noelia Cantador y Luis Lázaro
 * 
 * @version 1.0
 */
public abstract class Arma {
    /** Nombre del arma. */
    protected String nombreArma;
    /** Cantidad de daño que inflige el arma */
    protected int danioBase;
    /** Probabilidad de acierto del 0-100 */
    protected int precision;

    /**
     * Constructor principal para inicializar un arma.
     * * @param nombreArma El nombre que recibirá el arma.
     * 
     * @param danioBase El valor de daño base.
     * @param precision El porcentaje de eficacia en el golpe.
     */

    public Arma(String nombreArma, int danioBase, int precision) {
        this.nombreArma = nombreArma;
        this.danioBase = danioBase;
        this.precision = precision;
    }

    /**
     * Getters y setters de esta clase con sus atributos correspondientes
     * 
     */

    public String getNombreArma() {
        return nombreArma;
    }

    public void setNombreArma(String nombreArma) {
        this.nombreArma = nombreArma;
    }

    public int getDanioBase() {
        return danioBase;
    }

    public void setDanioBase(int danioBase) {
        this.danioBase = danioBase;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

}