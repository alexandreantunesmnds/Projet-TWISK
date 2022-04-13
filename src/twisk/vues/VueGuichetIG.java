package twisk.vues;

import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueGuichetIG extends VueEtapeIG implements Observateur{

    public VueGuichetIG(MondeIG monde, GuichetIG guichet){
        super(monde,guichet);
        this.titre.setText(guichet.getNom()+" : "+guichet.getNbJetons()+" jetons");

        //Ajout des styles
        TailleComposants constantes = TailleComposants.getInstance();

        this.setStyle("-fx-background-color: "+ guichet.getCouleur() +";-fx-background-radius: 10px,10px,10px,10px;-fx-border-color: #3b69a6; -fx-border-radius: 10px,10px,10px,10px;-fx-border-width: 2");

        this.getChildren().addAll(this.titre);
    }

    @Override
    public void reagir() {

    }
}
