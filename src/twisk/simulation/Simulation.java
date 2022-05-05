package twisk.simulation;

import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.outils.KitC;
import twisk.outils.ThreadsManager;
import twisk.vues.SujetObserve;

public class Simulation extends SujetObserve {
    private KitC kc;
    private int nbClients;
    private GestionnaireClients gc;
    private int noSimulation;

    /**
     * Constructeur
     */
    public Simulation(){
        kc = new KitC();
        kc.creerEnvironnement();
        noSimulation = kc.getNumeroSimulation();
    }

    /**
     * Fonction qui détermine le nombre de clients
     * @param nbClients
     */
    public void setNbClients(int nbClients){
        this.nbClients = nbClients;
        this.gc = new GestionnaireClients(nbClients);
    }

    /**
     * Guetteur
     * @return Retourne le gestionnaire de clients
     */
    public GestionnaireClients getGestionnaireClients(){
        return this.gc;
    }

    /**
     * Fonction qui lance la simulation du monde
     * @param monde
     */
    public void simuler(Monde monde){
        System.out.println("\n\n===        Le Monde        ===\n\n");
        System.out.println(monde.toString());
        System.out.println("\n\n=== Début de la simulation ===\n\n");
        kc.creerFichier(monde.toC());
        kc.compiler();
        kc.construireLaLibrairie();
        System.out.println("/tmp/twisk/libTwisk"+this.noSimulation +".so");
        System.load("/tmp/twisk/libTwisk"+this.noSimulation +".so");

        //Définition des variables locales
        int nbEtapes = monde.nbEtapes();
        int nbGuichets = monde.nbGuichets();
        Etape[] tabEtape = new Etape[nbEtapes];
        int[] tabJetonsGuichet = new int[nbGuichets];

        boolean stop = false;

        //initialisation des etapes et des jetons
        for(int i = 0, jetons = 0; i < nbEtapes ; i++){
            tabEtape[i] = monde.getEtape(i);
            if(tabEtape[i].estUnGuichet()){
                Guichet guich = (Guichet) tabEtape[i];
                tabJetonsGuichet[jetons] = guich.getNbjetons();
                jetons++;
            }
        }

        //Debuggage, merci Brigitte et Etienne
        /*for(int i = 0; i < nbEtapes; i++){
            System.out.println(tabEtape[i].getNom()+" ; " + i);
            System.out.println(tabEtape[i]);
        }
        System.exit(1);*/

        int[] idClients = start_simulation(monde.nbEtapes(),monde.nbGuichets(),nbClients,tabJetonsGuichet);
        int[] etatDeLaSimulation;


        //On affiche les clients
        System.out.print("Les clients sont : ");
        this.gc.setClients(idClients);
        for(Client client : this.gc){
            System.out.print(client.getNumeroClient()+" ");
        }

        while(!stop){
            //On récupère la position de tout les clients dans le monde
            etatDeLaSimulation = ou_sont_les_clients(nbEtapes, nbClients);
            //System.out.println(Arrays.toString(etatDeLaSimulation));
            int cptEtape = 0;
            System.out.println("");
            for(int numeroEtape = 0; numeroEtape < nbEtapes ; numeroEtape++) {
                //On récupère le nombre de clients dans l'étape
                int nbClientsDansEtape = etatDeLaSimulation[cptEtape];

                //On affiche l'étape
                System.out.print("\n"+tabEtape[numeroEtape].getNom() +" avec " + etatDeLaSimulation[cptEtape] + " clients : ");

                //On affiche les clients dans l'étape
                for(int client = 0, rang = 1; client < nbClientsDansEtape && rang < nbClientsDansEtape+1; client++,rang++){
                    System.out.print(etatDeLaSimulation[cptEtape+client+1]+" ");
                    this.gc.allerA(etatDeLaSimulation[cptEtape+client+1],tabEtape[numeroEtape],rang); //a revoir
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
                Thread.sleep(1000);
                notifierObservateurs();
            } catch (InterruptedException e) {

            }
        }
        this.gc.nettoyer();
        nettoyage();
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
