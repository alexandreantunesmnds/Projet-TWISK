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
     * Fixe le numéro sémaphore lié au guichet de l'acitivté
     * @param numSemaphore
     */
    @Override
    public void setNumSemaphore(int numSemaphore) {
        this.numSemaphore = numSemaphore;
    }

    /**
     * Fonction qui génère le code C de l'étape à partir de ces attributs
     * @return Retourne le code C de l'étape
     */
    @Override
    public String toC(){
        StringBuilder code = new StringBuilder("delai("+this.getTemps()+","+this.getEcartTemps()+");\nV(ids,"+this.numSemaphore+");\n");

        for(Etape e : this.getSucc()){
            code.append("transfert("+this.getNom()+","+e.getNom()+");\n");
            code.append(e.toC());
        }

        return code.toString();
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
