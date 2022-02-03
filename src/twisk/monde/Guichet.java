package twisk.monde;

public class Guichet extends Etape {
    private int nbjetons;

    public Guichet(String nom) {
        super(nom);
        this.nbjetons = 5;
    }

    public Guichet(String nom, int nb){
        super(nom);
        this.nbjetons = nb;
    }

    public int getNbjetons(){
        return this.nbjetons;
    }

    @Override
    public boolean estUneActivite() {
        return false;
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }

    @Override
    public String toString() {
        return "Guichet{" +
                "nbjetons=" + nbjetons +
                '}';
    }
}
