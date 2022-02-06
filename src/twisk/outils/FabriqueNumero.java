package twisk.outils;

public class FabriqueNumero {
    private static FabriqueNumero instance = new FabriqueNumero();

    private int cptEtape;
    private int cptSemaphore;

    private FabriqueNumero(){
    }

    public static FabriqueNumero getInstance(){
        return instance;
    }

    public int getNumeroEtape(){
        int i = this.cptEtape++;
        return i;
    }

    public int getNumeroSemaphore(){
        return ++this.cptSemaphore;
    }

    public void reset(){
        this.cptEtape = 0;
        this.cptSemaphore = 0;
    }
}
