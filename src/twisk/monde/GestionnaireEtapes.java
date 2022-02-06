package twisk.monde;

import java.util.*;

public class GestionnaireEtapes implements Iterable<Etape>{
    private ArrayList<Etape> etapeList;

    /**
     * Constructeur
     */
    public GestionnaireEtapes (){
        this.etapeList = new ArrayList<Etape>();
    }

    /**
     * Fonction qui ajoute à la liste d'étapes des étapes
     * @param etapes Liste d'étape à ajouter
     */
    public void ajouter (Etape ... etapes){
        Collections.addAll(this.etapeList, etapes);
    }

    /**
     * Fonction qui retourne le nombre d'étape dans la liste
     * @return Retourne le nombre d'étape dans la liste
     */
    public int nbEtapes(){
        return this.etapeList.size();
    }

    /**
     * Fonction qui permet d'itérer des étapes
     * @return Iterator<Etape>
     */
    public Iterator<Etape> iterator() {
        return etapeList.iterator();
    }

    /**
     * Fonction toString
     * @return Retourne les détails du GestionnaireEtape
     */
    @Override
    public String toString() {
        StringBuilder listeEtape = new StringBuilder();
        for(Etape e : etapeList){
            listeEtape.append(e.toString() + "\n");
        }
        return listeEtape.toString();
    }
}
