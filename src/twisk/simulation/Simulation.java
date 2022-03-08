package twisk.simulation;

import twisk.monde.Monde;
import twisk.outils.KitC;

public class Simulation {
    public Simulation(){
        KitC kc = new KitC();
        kc.creerEnvironnement();
    }

    public void simuler(Monde monde){
        System.out.println(monde.toString());
        monde.toC();
    }
}
