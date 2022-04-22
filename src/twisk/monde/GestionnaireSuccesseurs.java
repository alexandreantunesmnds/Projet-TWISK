package twisk.monde;

import twisk.mondeIG.EtapeIG;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireSuccesseurs implements Iterable<Etape>{
    private ArrayList<Etape> EtapeList;
    private ArrayList<EtapeIG> EtapeIGList;

    /**
     * Constructeur
     */
    public GestionnaireSuccesseurs(){
        this.EtapeList = new ArrayList<Etape>();
    }

    /**
     * Fonction qui retourne l'étape dont l'index est donnée en paramètre
     * @param id index de l'étape
     * @return l'étape à l'index id
     */
    public Etape getEtape(int id){
        return EtapeList.get(id);
    }

    /**
     * Fonction qui ajoute à la liste des successeurs des étapes IG
     * @param etapes Liste d'étape à ajouter
     */
    public void ajouter(EtapeIG etapes){
        this.EtapeIGList.addAll(List.of(etapes));
    }
    /**
     * Fonction qui ajoute à la liste des successeurs des étapes
     * @param etapes Liste d'étape à ajouter
     */
    public void ajouter(Etape etapes){
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

    public void retirer(EtapeIG e) {
        this.EtapeIGList.removeAll(List.of(e));
    }
}
