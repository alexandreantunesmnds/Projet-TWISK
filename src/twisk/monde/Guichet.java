package twisk.monde;

public class Guichet extends Etape {
    private int nbjetons;

    /**
     * Constructeur
     * @param nom nom du guichet
     */
    public Guichet(String nom) {
        super(nom);
        this.nbjetons = 5;
    }

    /**
     * Constructeur
     * @param nom nom du guichet
     * @param nb nombre de jetons
     */
    public Guichet(String nom, int nb){
        super(nom);
        this.nbjetons = nb;
    }

    /**
     * Getteur nombre de jetons
     * @return Retourne le nombre de jetons
     */
    public int getNbjetons(){
        return this.nbjetons;
    }

    /**
     * Fonction qui retourne faux si c'est un guichet
     * @return Retourne faux si c'est un guichet
     */
    @Override
    public boolean estUneActivite() {
        return false;
    }

    /**
     * Fonction qui retourne vrai si c'est un guichet
     * @return Retourne vrai si c'est un guichet
     */
    @Override
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * Fonction toString
     * @return Retourne les détails du guichet
     */
    @Override
    public String toString() {
        return "Guichet{" +
                "nbjetons=" + nbjetons +
                '}';
    }
}
