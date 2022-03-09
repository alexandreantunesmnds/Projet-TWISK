package twisk.simulation;

import twisk.monde.Monde;
import twisk.outils.KitC;

public class Simulation {
    private KitC kc;

    /**
     * Constructeur
     */
    public Simulation(){
        kc = new KitC();
        kc.creerEnvironnement();
    }

    /**
     * Fonction qui lance la simulation du monde
     * @param monde
     */
    public void simuler(Monde monde){
        //System.out.println(monde.toString());
        kc.creerFichier(monde.toC());
        kc.compiler();
    }

    /**
     * Fonction native qui lance la simulation du monde
     * @param nbEtapes nombre d'étapes dans le monde
     * @param nbGuichets nombre de guichets dans le monde
     * @param nbClients nombre de clients dans le monde
     * @param tabJetonsGuichet tableau comportant les différents jetons de tout les guichets
     * @return Un tableau d'id des clients
     */
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichet);

    /**
     * Fonction native qui résume la position des clients dans le monde
     * @param nbEtapes nombre d'étapes dans le monde
     * @param nbClients nombre de clients dans le monde
     * @return Un tableau d'id des clients
     */
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);

    /**
     * Fonction qui nettoie le monde
     */
    public native void nettoyage();
}
