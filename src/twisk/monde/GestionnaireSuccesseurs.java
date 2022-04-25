package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireSuccesseurs implements Iterable<Etape>{
    private ArrayList<Etape> etapeList;

    /**
     * Constructeur
     */
    public GestionnaireSuccesseurs(){
        this.etapeList = new ArrayList<Etape>();
    }

    /**
     * Fonction qui retourne l'étape dont l'index est donnée en paramètre
     * @param id index de l'étape
     * @return l'étape à l'index id
     */
    public Etape getEtape(int id){
        return etapeList.get(id);
    }

    /**
     * Fonction qui ajoute à la liste des successeurs des étapes
     * @param etapes Liste d'étape à ajouter
     */
    public void ajouter(Etape ... etapes){
        this.etapeList.addAll(List.of(etapes));
    }


    /**
     * Fonction qui retourne le nombre de successeur d'une étape
     * @return le nombre de successeurs d'une étape
     */
    public int nbEtapes(){
        return this.etapeList.size();
    }

    /**
     * Fonction qui permet d'itérer des étapes
     * @return Iterator<Etape>
     */
    public Iterator<Etape> iterator(){
        return etapeList.iterator();
    }

    /**
     * Fonction toString
     * @return Retourne les détails du GestionnaireSuccesseur
     */
    @Override
    public String toString() {
        StringBuilder listeSuccesseur = new StringBuilder();
        for(Etape e : etapeList){
            listeSuccesseur.append(e.getNom() + " - ");
        }
        return listeSuccesseur.toString() ;
    }
}
