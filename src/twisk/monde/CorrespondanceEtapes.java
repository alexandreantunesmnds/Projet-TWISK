package twisk.monde;

import twisk.mondeIG.EtapeIG;

import java.util.HashMap;

public class CorrespondanceEtapes {
    private HashMap<EtapeIG,Etape> listeCorrespondance;

    /**
     * Constructeur
     */
    public CorrespondanceEtapes(){
        this.listeCorrespondance = new HashMap<>();
    }

    /**
     * Fonction qui ajoute dans la liste l'étape avec pour clé l'étapeIG correspondante
     * @param etig L'étapeIG qui servira de clef
     * @param et l'étape
     */
    public void ajouter(EtapeIG etig, Etape et){
        this.listeCorrespondance.put(etig,et);
    }

    /**
     * Fonction qui retourne l'étape correspondante à l'étapeIG donné en paramètre
     * @param e L'étapeIG
     * @return L'étape correspondante à l'étapeIG
     */
    public Etape get (EtapeIG e){
        return this.listeCorrespondance.get(e);
    }
}
