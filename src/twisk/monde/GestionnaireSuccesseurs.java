package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireSuccesseurs implements Iterable<Etape>{
    private ArrayList<Etape> EtapeList;

    /**
     * Constructeur
     */
    public GestionnaireSuccesseurs(){
        this.EtapeList = new ArrayList<Etape>();
    }

    /**
     * Fonction qui ajoute à la liste des successeurs des étapes
     * @param etapes Liste d'étape à ajouter
     */
    public void ajouter(Etape ... etapes){
        this.EtapeList.addAll(List.of(etapes));
    }

    /**
     * Fonction qui retourne le nombre de successeur d'une étape
     * @return le nombre de successeurs d'une étape
     */
    public int nbEtapes(){
        return this.EtapeList.size();
    }

    /**
     * Fonction qui permet d'itérer des étapes
     * @return Iterator<Etape>
     */
    public Iterator<Etape> iterator(){
        return EtapeList.iterator();
    }

    /**
     * Fonction toString
     * @return Retourne les détails du GestionnaireSuccesseur
     */
    @Override
    public String toString() {
        StringBuilder listeSuccesseur = new StringBuilder();
        for(Etape e : EtapeList){
            listeSuccesseur.append(e.getNom() + " - ");
        }
        return listeSuccesseur.toString() ;
    }
}
