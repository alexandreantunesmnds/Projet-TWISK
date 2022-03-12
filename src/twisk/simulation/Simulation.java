package twisk.simulation;

import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.outils.KitC;

import java.util.ArrayList;

public class Simulation {
    private KitC kc;
    private int nbClients;

    /**
     * Constructeur
     */
    public Simulation(){
        kc = new KitC();
        kc.creerEnvironnement();
    }

    /**
     * Fonction qui détermine le nombre de clients
     * @param nbClients
     */
    public void setNbClients(int nbClients){
        this.nbClients = nbClients;
    }
    /**
     * Fonction qui lance la simulation du monde
     * @param monde
     */
    public void simuler(Monde monde){
        //System.out.println(monde.toString());
        kc.creerFichier(monde.toC());
        kc.compiler();
        kc.construireLaLibrairie();

        //Définition des variables locales
        int nbEtapes = monde.nbEtapes();
        int nbGuichets = monde.nbGuichets();
        int nbClients = this.nbClients;
        Etape[] tabEtape = new Etape[nbEtapes];
        int[] tabJetonsGuichet = new int[nbGuichets];

        boolean stop = false;
        int[] idClients = start_simulation(monde.nbEtapes(),monde.nbGuichets(),nbClients,tabJetonsGuichet);
        int[] etatDeLaSimulation;

        //initialisation des etapes et des jetons
        for(int i = 0, jetons = 0; i < nbEtapes ; i++){
            tabEtape[i] = monde.getEtape(i);
            if(tabEtape[i].estUnGuichet()){
                Guichet guich = (Guichet) tabEtape[i];
                tabJetonsGuichet[jetons] = guich.getNbjetons();
                jetons++;
            }
        }


        //On affiche les clients
        System.out.print("Les clients sont : ");
        for(int i = 0; i < nbClients; i++){
            System.out.print(idClients[i]);
        }

        while(!stop){
            //On récupère la position de tout les clients dans le monde
            etatDeLaSimulation = ou_sont_les_clients(nbEtapes, nbClients);
            int cptEtape = 0;
            for(int numeroEtape = 0; numeroEtape < nbEtapes ; numeroEtape++) {
                //On récupère le nombre de clients dans l'étape
                int nbClientsDansEtape = etatDeLaSimulation[cptEtape];

                //On affiche l'étape
                System.out.print("\n"+tabEtape[numeroEtape].getNom() +" avec " + etatDeLaSimulation[cptEtape] + " clients : ");

                //On affiche les clients dans l'étape
                for(int client = 0; client < nbClientsDansEtape; client++){
                    System.out.println(etatDeLaSimulation[cptEtape+client]+" ");
                }

                cptEtape += nbClients;

                if (numeroEtape == 1) { //Si on est dans le sas de sortie
                    if (nbClientsDansEtape == nbClients) {
                        stop = true;
                    }
                }
                cptEtape++;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nettoyage();
        System.load("/tmp/twisk/libTwisk.so") ;
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
