package twisk.monde;

public class Activite extends Etape {
    private int temps;
    private int ecartTemps;

    public Activite(String nom) {
        super(nom);
        this.temps = 5;
        this.ecartTemps = 2;
    }
    public Activite(String nom, int t, int e){
        super(nom);
        this.temps = t;
        this.ecartTemps = e ;
    }

    public int getTemps() {
        return temps;
    }

    public int getEcartTemps() {
        return ecartTemps;
    }

    @Override
    public boolean estUneActivite() {
        return true;
    }

    @Override
    public boolean estUnGuichet() {
        return false;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "temps=" + temps +
                ", ecartTemps=" + ecartTemps +
                '}';
    }
}
