public abstract class Arma {
    protected String nombreArma;
    protected int dañoBase;

    public Arma(String nombreArma, int dañoBase) {
        this.nombreArma = nombreArma;
        this.dañoBase = dañoBase;
    }

    public abstract void usar();

}