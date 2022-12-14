package twisk.vues;

import java.util.ArrayList;

public class SujetObserve {
    protected ArrayList<Observateur> listeObservateur = new ArrayList<Observateur>();

    public SujetObserve() {
    }

    /**
     * Fonction qui ajoute un observateur
     * @param v Un Observateur
     */
    public void ajouterObservateur(Observateur v) {
        this.listeObservateur.add(v);
    }

    /**
     * Fonction qui notifie tout les observateurs
     */
    public void notifierObservateurs(){
        for(Observateur o : this.listeObservateur){
            o.reagir();
        }
    }

    @Override
    public String toString() {
        return "SujetObserve{" +
                "listeObservateur=" + listeObservateur +
                '}';
    }
}
