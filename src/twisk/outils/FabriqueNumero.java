package twisk.outils;

public class FabriqueNumero {
    private static FabriqueNumero instance = new FabriqueNumero();

    private int cptEtape;
    private int cptSemaphore;

    /**
     * Constructeur
     */
    private FabriqueNumero(){
    }

    /**
     * Fonction getInstance
     * @return FabriqueNumero qui est utilisé comme Singleton
     */
    public static FabriqueNumero getInstance(){
        return instance;
    }

    /**
     * Fonction getNumeroEtape
     * @return le numéro de l'étape qui commence à partir de 0
     */
    public int getNumeroEtape(){
        int i = this.cptEtape++;
        return i;
    }

    /**
     * Fonction getNumeroSemaphore
     * @return le numéro de sémaphore qui commence à partir de 1
     */
    public int getNumeroSemaphore(){
        return ++this.cptSemaphore;
    }

    /**
     * Fonction reset qui remet les compteurs à zéro
     */
    public void reset(){
        this.cptEtape = 0;
        this.cptSemaphore = 0;
    }
}
