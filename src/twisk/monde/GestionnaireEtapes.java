package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireEtapes implements Iterable<Etape>{
    private ArrayList<Etape> EtapeList;

    /**
     * Constructeur
     */
    public GestionnaireEtapes (){
        this.EtapeList = new ArrayList<Etape>();
    }

    /**
     * Fonction qui ajoute à la liste d'étapes des étapes
     * @param etapes Liste d'étape à ajouter
     */
    public void ajouter (Etape ... etapes){
        this.EtapeList.addAll(List.of(etapes));
    }

    /**
     * Fonction qui retourne le nombre d'étape dans la liste
     * @return Retourne le nombre d'étape dans la liste
     */
    public int nbEtapes(){
        return this.EtapeList.size();
    }

    /**
     *
     * @return
     */
    public Iterator<Etape> iterator() {
        return EtapeList.iterator();
    }

    /**
     * Fonction toString
     * @return Retourne les détails du GestionnaireEtape
     */
    @Override
    public String toString() {
        return "GestionnaireEtapes{" +
                "EtapeList=" + EtapeList +
                '}';
    }
}
