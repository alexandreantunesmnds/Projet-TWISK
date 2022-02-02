package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireSuccesseurs {
    private ArrayList<Etape> EtapeList;
    public GestionnaireSuccesseurs(){
        this.EtapeList = new ArrayList<Etape>();
    }
    public void ajouter(Etape ... etapes){
        this.EtapeList.addAll(List.of(etapes));
    }
    public int nbEtapes(){
        return this.EtapeList.size();
    }
    public Iterator<Etape> iterator(){
        return EtapeList.iterator();
    }
}
