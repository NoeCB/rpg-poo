package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

/**
 * Clase abstracta base para todas las habilidades especiales (Perks)
 * 
 * Representa una habilidad que puede ser lanzada por un personaje sobre otro.
 * Cada Perk tiene un nombre identificador y un número limitado de usos.
 * 
 * Las subclases deben implementar el método abstracto {@link #lanzar(Personaje, Personaje)}
 * para definir el comportamiento específico de cada habilidad.
 * 
 * @author Dead by Daylight RPG
 */
public abstract class Perk {
    
    /**
     * Nombre de la habilidad
     */
    protected String nombre;
    
    /**
     * Número de usos disponibles de esta habilidad
     */
    protected int usos;

    /**
     * Constructor de Perk
     * 
     * @param nombre el nombre de la habilidad
     * @param usos el número de usos disponibles
     */
    public Perk(String nombre, int usos) {
        this.nombre = nombre;
        this.usos = usos;
    }

    /**
     * Obtiene el nombre de la habilidad
     * 
     * @return el nombre identificador del Perk
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Ejecuta el efecto de la habilidad
     * 
     * Método abstracto que debe ser implementado por todas las subclases.
     * Define el comportamiento específico de cada habilidad cuando se lanza.
     * 
     * @param caster el personaje que lanza la habilidad
     * @param objetivo el personaje que recibe el efecto de la habilidad
     */
    public abstract void lanzar(Personaje caster, Personaje objetivo);

    /**
     * Obtiene el número de usos disponibles
     * 
     * @return la cantidad de veces que se puede usar esta habilidad
     */
    public int getUsos() {
        return this.usos;
    }

    /**
     * Consume un uso de esta habilidad
     * 
     * Decrementa en 1 el número de usos disponibles, si hay usos disponibles.
     * Si los usos ya estaban en 0, no hace nada.
     */
    public void consumirUso() {
        if (this.usos > 0) {
            this.usos--;
        }
    }
}