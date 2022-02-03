package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireSuccesseurs {
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
     * @return
     */
    public int nbEtapes(){
        return this.EtapeList.size();
    }

    /**
     *
     * @return
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
