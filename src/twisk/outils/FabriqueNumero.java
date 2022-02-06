package twisk.outils;

public class FabriqueNumero {
    private static FabriqueNumero instance = new FabriqueNumero();

    private int cptEtape;

    private FabriqueNumero(){
    }

    public static FabriqueNumero getInstance(){
        return instance;
    }

    public int getNumeroEtape(){
        int i = this.cptEtape++;
        return i;
    }

    public void reset(){
        this.cptEtape = 0;
    }
}
