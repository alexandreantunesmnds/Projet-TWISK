package twisk.simulation;

import twisk.monde.Etape;

import java.util.HashMap;
import java.util.Iterator;

public class GestionnaireClients implements Iterable<Client>{
    public HashMap<Integer,Client> listeClients;

    /**
     * Constructeur
     */
    public GestionnaireClients(){
        this.listeClients = new HashMap<Integer,Client>();
    }

    /**
     * Constructeur
     * @param nbClients Le nombre de client
     */
    public GestionnaireClients(int nbClients){
        this.listeClients = new HashMap<Integer,Client>(nbClients);
    }

    /**
     * Fonction qui fixe le nombre de clients
     * @param tabClients Les numéros des clients
     */
    public void setClients(int ... tabClients){
        for(int num : tabClients){
            this.listeClients.put(num,new Client(num));
        }
    }

    /**
     * Fonction qui met à jour les données du client
     * @param numeroClient Le numéro du client à mettre à jour
     * @param etape L'étape où se situe le client
     * @param rang Le rang où se situe le client
     */
    public void allerA(int numeroClient, Etape etape, int rang){
        this.listeClients.get(numeroClient).allerA(etape,rang);
    }

    /**
     * Fonction qui donne le nombre de client
     * @return Retourne le nombre de client
     */
    public int getNbClient(){
        return this.listeClients.size();
    }

    /**
     * Fonction qui vide la liste des clients
     */
    public void nettoyer(){
        this.listeClients.clear();
    }

    /**
     * Fonction iteratrice
     * @return iterateur
     */
    @Override
    public Iterator<Client> iterator() {
        return listeClients.values().iterator();
    }
}
