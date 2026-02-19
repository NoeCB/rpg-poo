public abstract class Arma {
    protected String nombreArma;
    protected int dañoBase;
    protected double alcance;
    protected double tiempoRecuperacion; // En segundos
    
    public Arma(String nombre, int dañoBase, double alcance, double tiempoRecuperacion, String rareza) {
        this.nombre = nombre;
        this.dañoBase = dañoBase;
        this.alcance = alcance;
        this.tiempoRecuperacion = tiempoRecuperacion;

    }

    public abstract void usar();
    
    // Un método común para todas las armas inspirado en DBD
    public void limpiarArma() {
        System.out.println("El Killer limpia la sangre de " + nombrePersonaje + "...");
    }
}