package twisk.monde;

public class ActiviteRestreinte extends Activite{
    private int numSemaphore;
    /**
     * Constructeur
     * @param nom nom de l'activité restreinte
     */
    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    /**
     * Constructeur
     * @param nom nom de l'activité restreinte
     * @param t temps de l'activité restreinte
     * @param e l'écart-temps de l'activité restreinte
     */
    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom, t, e);
    }

    /**
     * Retoune le numéro sémaphore lié au guichet de l'acitivté
     * @return le numéro sémaphore lié au guichet de l'acitivté
     */
    public int getNumSemaphore() {
        return numSemaphore;
    }


    /**
     * Fonction toString
     * @return retourne les détails de l'activité restreinte
     */
    @Override
    public String toString() {
        return "Activité Restreinte " + this.getNom() + " : " + this.nbSuccesseur() + " successeur(s) -> " + this.getSucc().toString();
    }
}
