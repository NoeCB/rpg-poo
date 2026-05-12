package com.dbd.estados;

import com.dbd.entidades.Personaje;

/**
 * Clase abstracta que define la estructura base de todos los estados alterados en el juego.
 * <p>
 * Los estados son efectos temporales que se aplican a los personajes durante el combate.
 * Ejemplos incluyen: hemorragia, ceguera, maldición, regeneración, vigor, etc.
 * </p>
 * Cada estado tiene una duración limitada (medida en turnos) y un efecto específico que se
 * aplica cada turno hasta que el estado vence. La duración se decrementa automáticamente en
 * el método {@link com.dbd.entidades.Personaje#procesarEstados()}.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public abstract class Estado {
    /** Nombre descriptivo del estado (ej: "Hemorragia", "Ceguera", "Maldición"). */
    protected String nombre;
    
    /** Cantidad de turnos que aún le quedan a este estado antes de ser removido automáticamente. */
    protected int turnosRestantes;

    /**
     * Constructor que inicializa un estado con su nombre y duración.
     *
     * @param nombre Nombre descriptivo del estado (ej: "Hemorragia", "Ceguera", "Maldición")
     * @param turnosRestantes Número de turnos que durará este estado antes de ser removido
     */
    public Estado(String nombre, int turnosRestantes) {
        this.nombre = nombre;
        this.turnosRestantes = turnosRestantes;
    }

    /**
     * Obtiene el nombre descriptivo del estado.
     *
     * @return El nombre del estado
     */
    public String getNombre() { return nombre; }
    /**
     * Obtiene la cantidad de turnos que aún le quedan a este estado.
     *
     * @return Número de turnos restantes. Si es ≤ 0, el estado será removido automáticamente
     */
    public int getTurnosRestantes() { return turnosRestantes; }

    /**
     * Método abstracto que aplica el efecto específico del estado al objetivo.
     * <p>
     * Cada subclase implementa su propio efecto:
     * <ul>
     * <li><strong>Hemorragia</strong>: Inflige daño cada turno</li>
     * <li><strong>Ceguera</strong>: Reduce precisión de ataques</li>
     * <li><strong>Maldición</strong>: Debilita o impide habilidades</li>
     * <li><strong>Regeneración</strong>: Recupera vida cada turno</li>
     * <li><strong>Vigor</strong>: Aumenta defensa temporalmente</li>
     * </ul>
     * Este método es invocado automáticamente por {@link com.dbd.entidades.Personaje#procesarEstados()}
     * en cada turno, y después decrementará turnosRestantes.
     * </p>
     *
     * @param objetivo El personaje que recibirá el efecto del estado
     */
    public abstract void aplicarEfecto(Personaje objetivo);
}