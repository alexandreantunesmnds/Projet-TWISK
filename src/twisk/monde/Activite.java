package twisk.monde;

public class Activite extends Etape {
    private int temps;
    private int ecartTemps;

    /**
     * Constructeur
     * @param nom Le nom de l'Acitivité
     */
    public Activite(String nom) {
        super(nom);
        this.temps = 5;
        this.ecartTemps = 2;
    }

    /**
     * Constructeur
     * @param nom Le nom de l'Activité
     * @param t Le temps de l'Activité
     * @param e L'écart temps de l'Acitivité
     */
    public Activite(String nom, int t, int e){
        super(nom);
        this.temps = t;
        this.ecartTemps = e ;
    }

    /**
     * Getteur temps
     * @return Retourne le temps de l'Activité
     */
    public int getTemps() {
        return temps;
    }

    /**
     * Getteur ecart-temps
     * @return Retourne l'écart temps de l'Activité
     */
    public int getEcartTemps() {
        return ecartTemps;
    }

    /**
     * Fonction qui retourne vrai si c'est une Activité
     * @return retourne vrai
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }

    /**
     * Fonction qui retourne faux si c'est une Activité
     * @return retourne faux
     */
    @Override
    public boolean estUnGuichet() {
        return false;
    }

    /**
     * Fonction toString
     * @return Retourne les détails d'une Activité
     */
    @Override
    public String toString() {
        return "Activite{" +
                "temps=" + temps +
                ", ecartTemps=" + ecartTemps +
                '}';
    }
}
