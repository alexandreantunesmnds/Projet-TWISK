package twisk.outils;

public class FabriqueNumero {
    private static FabriqueNumero instance = new FabriqueNumero();

    private int cptEtape;

    private FabriqueNumero(){
        this.cptEtape = 0;
    }

    public static FabriqueNumero getInstance(){
        return instance;
    }

    public int getNumeroEtape(){
        this.cptEtape++;
        return cptEtape;
    }

    public void reset(){
        this.cptEtape = 0;
    }
}
