package twisk.outils;

public class FabriqueIdentifiant {
    private static FabriqueIdentifiant instance = new FabriqueIdentifiant();
    private int noEtape;

    /**
     * Constructeur
     */
    private FabriqueIdentifiant(){
        this.noEtape = 1;
    }

    /**
     * Guetteur
     * @return Retourne une instance de la Fabrique
     */
    public static FabriqueIdentifiant getInstance(){
        return instance;
    }

    /**
     * Guetteur
     * @return Retourne l'identifiant de l'étape
     */
    public String getIdentifiantEtape(){
        int i = this.noEtape++;
        return Integer.toString(i);
    }

    /**
     * Setteur
     * @param noEtape;
     */
    public void setIdentifiantEtape(int noEtape){
        this.noEtape = noEtape;
    }

    /**
     * Fonction qui reset le compteur
     */
    public void reset(){
        this.noEtape = 1;
    }
}
