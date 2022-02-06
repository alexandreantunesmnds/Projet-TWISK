package twisk.monde;

public class ActiviteRestreinte extends Activite{
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
     * Fonction toString
     * @return retourne les détails de l'activité restreinte
     */
    @Override
    public String toString() {
        return "Activité Restreinte " + this.getNom() + " : " + this.nbSuccesseur() + " successeur(s) -> " + this.getSucc().toString();
    }
}
