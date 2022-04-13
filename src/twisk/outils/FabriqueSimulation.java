package twisk.outils;

public class FabriqueSimulation {
    private static FabriqueSimulation instance = new FabriqueSimulation();
    private int noSimulation;

    /**
     * Constructeur
     */
    private FabriqueSimulation(){this.noSimulation = 1;}

    /**
     * Guetteur
     * @return Retourne une instance de la Fabrique
     */
    public static FabriqueSimulation getInstance(){
        return instance;
    }

    /**
     * Guetteur
     * @return Le numéro de la simulation
     */
    public int getNoSimulation(){
        this.noSimulation++;
        return noSimulation;
    }

    /**
     * Fonction qui reset le compteur
     */
    public void reset(){
        this.noSimulation = 1;
    }
}
