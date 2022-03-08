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


}
