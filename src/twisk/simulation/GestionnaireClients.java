package twisk.simulation;

import twisk.monde.Etape;

import java.util.Iterator;
import java.util.List;

public class GestionnaireClients implements Iterable<Client>{
    public List<Client> listeClients;

    public GestionnaireClients(){
        
    }

    public GestionnaireClients(int nbClients){

    }

    public void setClients(int ... tabClients){

    }

    public void setNbClients(int Clients){
        
    }

    public void allerA(int numeroClient, Etape etape, int rang){
        
    }

    public void nettoyer(){
        
    }

    @Override
    public Iterator<Client> iterator() {
        return listeClients.iterator();
    }
}
