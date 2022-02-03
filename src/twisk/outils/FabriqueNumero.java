package twisk.outils;

public class FabriqueNumero {
    private static FabriqueNumero instance = new FabriqueNumero();

    private int cptEtape;

    public static FabriqueNumero getInstance(){
        return instance;
    }

    private FabriqueNumero(){}

    int getNumeroEtape(){
        return cptEtape;
    }

    void reset(){

    }
}
