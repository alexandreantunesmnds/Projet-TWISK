package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireClients implements Iterable<Client>{
    public List<Client> listeClients;

    public GestionnaireClients(){
        
    }

    public GestionnaireClients(int nbClients){
        this.listeClients = new ArrayList<Client>(nbClients);
    }

    public void setClients(int ... tabClients){
        for(int num : tabClients){
            this.listeClients.add(new Client(num));
        }
    }

    public void setNbClients(int Clients){
        
    }

    public void allerA(int numeroClient, Etape etape, int rang){

    }

    public void nettoyer(){
        this.listeClients.clear();
    }

    @Override
    public Iterator<Client> iterator() {
        return listeClients.iterator();
    }
}
