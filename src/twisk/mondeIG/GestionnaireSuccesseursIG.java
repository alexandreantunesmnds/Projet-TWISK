package twisk.mondeIG;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireSuccesseursIG implements Iterable<EtapeIG>{
    private ArrayList<EtapeIG> etapeIGList;

    /**
     * Constructeur
     */
    public GestionnaireSuccesseursIG(){
        this.etapeIGList = new ArrayList<EtapeIG>();
    }

    /**
     * Fonction qui retourne l'étape dont l'index est donnée en paramètre
     * @param id index de l'étape
     * @return l'étape à l'index id
     */
    public EtapeIG getEtape(int id){
        return etapeIGList.get(id);
    }

    /**
     * Fonction qui ajoute à la liste des successeurs des étapes IG
     * @param etapes Liste d'étape à ajouter
     */
    public void ajouter(EtapeIG ... etapes){
        this.etapeIGList.addAll(List.of(etapes));
    }

    /**
     * Fonction qui retire le successeur de l'étape donnée en paramètre
     * @param e
     */
    public void retirer(EtapeIG e) {
        this.etapeIGList.removeAll(List.of(e));
    }

    /**
     * Fonction qui retourne le nombre de successeur d'une étape
     * @return le nombre de successeurs d'une étape
     */
    public int nbEtapes(){
        return this.etapeIGList.size();
    }

    @Override
    public Iterator<EtapeIG> iterator() {
        return etapeIGList.iterator();
    }

    /**
     * Fonction toString
     * @return Retourne les détails du GestionnaireSuccesseur
     */
    @Override
    public String toString() {
        StringBuilder listeSuccesseur = new StringBuilder("Successeurs : ");
        for(EtapeIG e : etapeIGList){
            listeSuccesseur.append(e.getNom() + " - ");
        }
        return listeSuccesseur.toString();
    }
}
