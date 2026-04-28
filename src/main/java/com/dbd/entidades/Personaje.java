package com.dbd.entidades;

import com.dbd.arma.Arma;
import com.dbd.core.Util;
import com.dbd.estados.Estado;
import com.dbd.habilidades.Perk;
import java.util.ArrayList;

/**
 * Clase abstracta que define la estructura y el comportamiento base de
 * cualquier personaje en el juego.
 * <p>
 * Gestiona los atributos de combate (vida, defensa), el sistema de estados
 * alterados,
 * el inventario de habilidades (perks) y la lógica de inteligencia artificial
 * para las acciones.
 * </p>
 * Esta clase implementa un sistema de combate asimétrico donde supervivientes y
 * asesinos tienen
 * mecánicas diferentes para mantener el equilibrio del juego. Por ejemplo, los
 * supervivientes
 * tienen mayor probabilidad de evasión pero menos vida, mientras que los
 * asesinos son más resistentes.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.1
 */
public abstract class Personaje {
    protected String nombrePersonaje;

    /** Puntos de vida actuales del personaje. Se reduce cuando recibe daño. */
    protected int vidaActual;

    /** Cantidad máxima de puntos de vida que puede tener el personaje. */
    protected int vidaMax;

    /**
     * Valor de defensa base que reduce el daño recibido. Se calcula como
     * defensaBase / 5.
     */
    protected int defensaBase;

    /**
     * Lista de estados alterados aplicados al personaje (hemorragia, ceguera,
     * etc.).
     */
    protected ArrayList<Estado> estados;

    /** Lista de habilidades especiales (Perks) equipadas del personaje. */
    protected ArrayList<Perk> perks;

    /** Arma equipada actualmente. Puede ser null si el personaje está desarmado. */
    protected Arma arma;

    /**
     * Bandera que indica si el personaje está en postura defensiva, reduciendo daño
     * recibido.
     */
    protected boolean defendiendo = false;

    /**
     * Constructor que inicializa un personaje con sus atributos base.
     * Crea las listas vacías de estados y perks, así como establece el arma en
     * null.
     *
     * @param nombrePersonaje Nombre único del personaje
     * @param vidaActual Vida inicial (generalmente igual a vidaMax)
     * @param vidaMax Vida máxima del personaje. Determina si es superviviente (≤130) o asesino (>130)
     * @param defensaBase Valor de defensa que reduce daño (mitiga defensaBase/5 puntos de daño)
     */
    public Personaje(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase) {
        this.nombrePersonaje = nombrePersonaje;
        this.vidaActual = vidaActual;
        this.vidaMax = vidaMax;
        this.defensaBase = defensaBase;
        this.estados = new ArrayList<>();
        this.perks = new ArrayList<>();
    }

    /**
     * Determina el bando del personaje basándose en su salud máxima.
     * Los supervivientes se identifican por tener una vida máxima igual o inferior
     * a 130.
     * Esta diferencia es crucial para el sistema asimétrico: proporciona diferentes
     * probabilidades de evasión, daño base sin arma, etc.
     *
     * @return {@code true} si es superviviente, {@code false} si es asesino
     *         (killer)
     */
    private boolean esSuperviviente() {
        return this.vidaMax <= 130;
    }

    /**
     * Añade una nueva habilidad (Perk) al repertorio del personaje.
     * Las habilidades pueden ser usadas durante el combate para obtener ventajas
     * tácticas.
     *
     * @param p La habilidad a equipar. Debe ser una instancia de Perk válida
     */
    public void addPerk(Perk p) {
        this.perks.add(p);
    }

    /**
     * Obtiene el nombre identificativo del personaje.
     *
     * @return El nombre del personaje
     */
    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    /**
     * Obtiene la vida actual del personaje.
     *
     * @return Los puntos de vida actuales
     */
    public int getVidaActual() {
        return this.vidaActual;
    }

    /**
     * Establece la vida actual del personaje.
     *
     * @param vidaActual La nueva cantidad de vida actual
     */
    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    /**
     * Obtiene la vida máxima del personaje.
     *
     * @return Los puntos de vida máximos
     */
    public int getVidaMax() {
        return this.vidaMax;
    }

    /**
     * Obtiene el arma equipada actualmente.
     *
     * @return El arma del personaje, o null si está desarmado
     */
    public Arma getArma() {
        return this.arma;
    }

    /**
     * Equipa un arma al personaje.
     *
     * @param arma El arma a equipar
     */
    public void setArma(Arma arma) {
        this.arma = arma;
    }

    /**
     * Obtiene la lista de habilidades (Perks) del personaje.
     *
     * @return ArrayList con todas las Perks equipadas
     */
    public ArrayList<Perk> getPerks() {
        return perks;
    }

    /**
     * Obtiene el valor base de defensa del personaje.
     *
     * @return El valor de defensa base
     */
    public int getDefensaBase() {
        return this.defensaBase;
    }

    /**
     * Modifica la defensa base del personaje.
     *
     * @param nuevaDefensa El nuevo valor de defensa
     */
    public void setDefensaBase(int nuevaDefensa) {
        this.defensaBase = nuevaDefensa;
    }

    /**
     * Establece si el personaje está en postura defensiva.
     * Una postura defensiva reduce el daño recibido a la mitad en el siguiente
     * ataque.
     *
     * @param defendiendo true si adopta postura defensiva, false en caso contrario
     */
    public void setDefendiendo(boolean defendiendo) {
        this.defendiendo = defendiendo;
    }

    /**
     * Comprueba si el personaje está actualmente en postura defensiva.
     *
     * @return true si está defendiendo, false en caso contrario
     */
    public boolean isDefendiendo() {
        return this.defendiendo;
    }

    /**
     * @return La lista de estados alterados del personaje
     */
    public ArrayList<Estado> getEstados() {
        return this.estados;
    }

    /**
     * Aplica un nuevo estado alterado al personaje (hemorragia, ceguera, etc.).
     * El estado se añade a la lista y sus efectos se procesarán cada turno.
     *
     * @param nuevoEstado El estado a aplicar
     */
    public void aplicarEstado(Estado nuevoEstado) {
        this.estados.add(nuevoEstado);
    }

    /**
     * Procesa todos los estados activos del personaje en el turno actual.
     * Aplica los efectos de cada estado y reduce la duración.
     * Los estados que llegan a 0 turnos restantes son removidos automáticamente.
     */
    public void procesarEstados() {
        for (int i = estados.size() - 1; i >= 0; i--) {
            Estado e = estados.get(i);
            e.aplicarEfecto(this);
            if (e.getTurnosRestantes() <= 0)
                estados.remove(i);
        }
    }

    /**
     * Gestiona la recepción de daño aplicando mitigaciones y lógica asimétrica.
     * <p>
     * El proceso de cálculo sigue este orden:
     * <ol>
     * <li><strong>Evasión</strong>: 20% para supervivientes, 5% para asesinos. Si
     * se esquiva, no se recibe daño.</li>
     * <li><strong>Bloqueo Activo</strong>: Si está defendiendo, reduce el daño a la
     * mitad (50% de mitigación).</li>
     * <li><strong>Armadura Pasiva</strong>: Reduce daño en defensaBase / 5
     * puntos.</li>
     * <li><strong>Daño Mínimo</strong>: El daño final siempre es mínimo de 5 puntos
     * (previene OP turtle).</li>
     * <li><strong>Cálculo Final</strong>: vidaActual se reduce por daño final, con
     * mínimo de 0.</li>
     * </ol>
     * </p>
     *
     * @param danioBruto Cantidad de daño inicial antes de mitigaciones
     */
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
            com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).desbloquearLogro(9); // Esquiva Matrix
            return;
        }

        int danioFinal = danioBruto;

        // La defensa bloquea el 50% (75% era demasiado OP para Ghost Face --> Puesto en
        // versiones anteriores)
        if (this.defendiendo) {
            danioFinal = danioBruto / 2;
            System.out.println(Util.CYAN + this.nombrePersonaje + " realiza un bloqueo perfecto." + Util.RESET);
            com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).desbloquearLogro(8); // Tanque
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
                
        com.dbd.dao.GestorPersistencia gestor = com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class);
        gestor.desbloquearLogro(1); // Primera Sangre
        if (danioFinal >= 40) {
            gestor.desbloquearLogro(10); // Ataque Letal
        }
    }

    /**
     * Lógica de toma de decisiones para personajes controlados por la inteligencia
     * artificial.
     * <p>
     * La IA evalúa múltiples factores para decidir la acción más estratégica:
     * <ul>
     * <li><strong>Estado de Salud</strong>: Si la vida es menor al 35% del máximo,
     * el personaje entra en "Peligro".</li>
     * <li><strong>Selección de Objetivo</strong>: 50% de probabilidad de atacar al
     * enemigo más débil, 50% al azar.</li>
     * <li><strong>Perks Disponibles</strong>: Se usan solo si tienen usos restantes
     * (no están agotadas).</li>
     * <li><strong>Probabilidad de Usar Perk</strong>: 25% en combate normal, 65% si
     * está en peligro.</li>
     * <li><strong>Defensa Desesperada</strong>: Si está en peligro y el dado indica
     * >60, se defiende.</li>
     * <li><strong>Ataque por Defecto</strong>: Si no usa Perk ni se defiende,
     * ejecuta un ataque básico.</li>
     * </ul>
     * </p>
     *
     * @param aliados  Lista de personajes del mismo bando (se usa para contexto
     *                 futuro)
     * @param enemigos Lista de personajes del bando contrario, de los cuales se
     *                 selecciona objetivo
     */
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

    /**
     * Ejecuta un ataque estándar contra un rival.
     * <p>
     * El ataque puede ser:
     * <ul>
     * <li><strong>Con Arma</strong>: Usa precisión, daño base y añade varianza
     * (±2). Si falla por precisión, se anuncia.</li>
     * <li><strong>Sin Arma (Desarmado)</strong>: Daño asimétrico según bando:
     * <ul>
     * <li>Supervivientes: 20-34 daño (compensan baja salud con daño alto en
     * grupo)</li>
     * <li>Asesinos: 12-21 daño (tienen mucha vida, menos necesidad de daño
     * puro)</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * @param rival El personaje rival que recibirá el impacto
     */
    private void atacarBasico(Personaje rival) {
        int danioGenerado;

        if (this.arma != null) {
            // Ataque usando Arma
            int dadoPrecision = (int) (Math.random() * 100) + 1;

            System.out.println(
                    Util.AMARILLO + this.nombrePersonaje + " se prepara para usar su " + this.arma.getNombreArma()
                            + " contra " + rival.getNombrePersonaje() + "!" + Util.RESET);

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
                    com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).desbloquearLogro(6); // Golpe Crítico
                }

                com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).desbloquearLogro(16); // Armado y Peligroso
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
                com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).desbloquearLogro(6); // Golpe Crítico
            }

            rival.recibirDanio(danioGenerado);
            if (rival.getVidaActual() <= 0) {
                com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).desbloquearLogro(17); // Mano Limpia
            }
        }
    }
}