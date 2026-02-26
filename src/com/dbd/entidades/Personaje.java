package com.dbd.entidades;

// Estos tres imports son importantes para que no dé error, NO cambiarlos
import com.dbd.estados.Estado;
import com.dbd.habilidades.Perk;
import java.util.ArrayList;

public abstract class Personaje {
    protected String nombrePersonaje;
    protected int vidaActual;
    protected int vidaMax;
    protected int defensaBase;
    protected int puntosSangre;
    
    // Las listas deben ser de objetos, no de Strings
    protected ArrayList<Estado> estados;
    protected ArrayList<Perk> perks;

    public Personaje(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase, int puntosSangre) {
        this.nombrePersonaje = nombrePersonaje;
        this.vidaActual = vidaActual;
        this.vidaMax = vidaMax;
        this.defensaBase = defensaBase;
        this.puntosSangre = puntosSangre;
        this.estados = new ArrayList<>();
        this.perks = new ArrayList<>();
    }

    // Métodos Getters y Setters
    public String getNombrePersonaje() { return nombrePersonaje; }
    public int getVidaActual() { return vidaActual; }
    public void setVidaActual(int vidaActual) { this.vidaActual = vidaActual; }
    public ArrayList<Perk> getPerks() { return perks; }

    // Métodos para el combate y los turnos
    public void aplicarEstado(Estado nuevoEstado) {
        this.estados.add(nuevoEstado);
    }

    public void procesarEstados() {
        for (int i = estados.size() - 1; i >= 0; i--) {
            Estado e = estados.get(i);
            e.aplicarEfecto(this); 
            
            if (e.getTurnosRestantes() <= 0) {
                estados.remove(i); 
            }
        }
    }

    // Método abstracto obligatorio para las clases hijas
    // Cada personaje tendrá su propia implementación de acción
    public abstract void accion();
    // Añade esta variable arriba con las demás (si no la tienes)
    protected boolean defendiendo = false;

    // --- 1. SISTEMA DE DAÑO Y DEFENSA ---
    public void recibirDanio(int danioBruto) {
    int danioFinal = danioBruto;
    if (this.defendiendo) {
        // La defensa ahora reduce el daño a la mitad en lugar de restar un valor fijo
        // Esto evita que el daño sea 0 siempre.
        danioFinal = danioBruto / 2; 
        System.out.println(this.nombrePersonaje + " amortigua el golpe.");
        this.defendiendo = false;
    }

    this.vidaActual -= danioFinal;
    if (this.vidaActual < 0) this.vidaActual = 0; // Para que no salga -571 HP
    
    System.out.println(this.nombrePersonaje + " recibe " + danioFinal + " de daño. (Vida: " + this.vidaActual + "/" + this.vidaMax + ")");
}

    // --- 2. LA AUTOMATIZACION ---
public void decidirAccionIA(ArrayList<Personaje> aliados, ArrayList<Personaje> enemigos) {
    this.defendiendo = false;
    
    // 1. FILTRAR OBJETIVOS VIVOS (Importante para que no ataquemos a muertos)
    ArrayList<Personaje> objetivosValidos = new ArrayList<>();
    for (Personaje e : enemigos) {
        if (e.getVidaActual() > 0) objetivosValidos.add(e);
    }

    // Si no hay nadie vivo, no hacemos nada
    if (objetivosValidos.isEmpty()) return;

    // 2. ELEGIR AL MÁS DÉBIL DE LOS QUE ESTÁN VIVOS
    Personaje objetivo = objetivosValidos.get(0);
    for (Personaje e : objetivosValidos) {
        if (e.getVidaActual() < objetivo.getVidaActual()) {
            objetivo = e;
        }
    }

    // 3. DECIDIR ACCIÓN
    int dado = (int) (Math.random() * 100);
    double porcentajeVida = (double) this.vidaActual / this.vidaMax;

    if (porcentajeVida < 0.25 && dado < 50) { // Si tiene < 25% vida, 50% de defenderse
        System.out.println(this.nombrePersonaje + " se protege desesperadamente.");
        this.defendiendo = true;
    } else {
        atacarBasico(objetivo);
    }
}
    // --- 3. ATAQUE NORMAL ---
    private void atacarBasico(Personaje rival) {
        // Generamos un daño aleatorio entre 15 y 30 para darle variabilidad al combate
        int danioGenerado = (int) (Math.random() * 16) + 15; 
        System.out.println(this.nombrePersonaje + " lanza un ataque básico directo a " + rival.getNombrePersonaje() + "!");
        rival.recibirDanio(danioGenerado);
    }
}
