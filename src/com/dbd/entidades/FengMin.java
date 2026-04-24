package com.dbd.entidades;

/**
 * Representa a Feng Min, una superviviente del juego inspirada en la comunidad gamer.
 * <p>
 * Feng Min es una superviviente ágil y concentrada que utiliza su agilidad mental y reflexos
 * rápidos para escapar del peligro. Su vida máxima de 100 puntos la posiciona como una
 * superviviente frágil, pero compensada con mayor evasón (20%) y daño superior en grupo (20-34 HP).
 * </p>
 * Con una defensa base de 10, Feng Min mitiga solo 2 puntos de daño (10/5) por ataque,
 * lo que requiere estratégia defensiva inteligente para sobrevivir.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class FengMin extends Personaje {
    /**
     * Constructor que inicializa la instancia de Feng Min con sus atributos específicos.
     * <p>
     * Estadísticas de Feng Min:
     * <ul>
     * <li><strong>Nombre</strong>: "Feng Min" (la gamer asilenciosa)</li>
     * <li><strong>Vida Actual</strong>: 100 HP (vulnerable)</li>
     * <li><strong>Vida Máxima</strong>: 100 HP (clasificada como superviviente por ser \u2264130)</li>
     * <li><strong>Defensa Base</strong>: 10 (mitiga solo 2 puntos de daño por ataque = 10/5)</li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial inicial)</li>
     * </ul>
     * Feng Min hereda todas las mecánicas de combate base de la clase {@link Personaje},
     * incluyendo el sistema asimétrico de daño donde supervivientes obtienen:
     * <ul>
     * <li><strong>Evasón mejorada</strong>: 20% (vs 5% de killers)</li>
     * <li><strong>Daño sin armas</strong>: 20-34 HP (vs 12-21 de killers)</li>
     * </ul>
     * </p>
     */
    public FengMin() {
        super("Feng Min", 100, 100, 10);
    }

    /**
     * Ejecuta la acción específica de Feng Min cuando es su turno (método abstracto implementado).
     * <p>
     * Feng Min analiza su entorno con concentración de gamer, mostrando su naturaleza
     * estratégica y reflexiva. Este método es principalmente de flavour/narrativa y se
     * ejecuta al inicio de su turno.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina automáticamente
     * mediante {@link Personaje#decidirAccionIA(ArrayList, ArrayList)} o manualmente
     * en modo juego player-controlled.
     */
    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " analiza su entorno con concentración gamer.");
    }
}