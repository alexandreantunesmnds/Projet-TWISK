package twisk.vues;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueGuichetIG extends VueEtapeIG implements Observateur{

    public VueGuichetIG(MondeIG monde, GuichetIG guichet){
        super(monde,guichet);
        int nbJetons = guichet.getNbJetons();
        this.titre.setText(guichet.getNom()+" : "+nbJetons+" jetons");

        //Ajout des styles
        TailleComposants constantes = TailleComposants.getInstance();

        this.setStyle("-fx-background-color: "+ guichet.getCouleur() +";-fx-background-radius: 10px,10px,10px,10px;-fx-border-color:" + guichet.getCouleurBord() + "; -fx-border-radius: 10px,10px,10px,10px;-fx-border-width: 2");

        //Ajout des zones de clients
        HBox zoneClient = new HBox();
        zoneClient.setPrefSize(constantes.getLargeurZoneClient(), constantes.getHauteurZoneClient());
        //zoneClient.setStyle("-fx-background-color: purple");
        zoneClient.setSpacing(2);
        for(int i = 0; i< 10;i++){
            HBox client = new HBox();
            client.setStyle("-fx-background-color: #c9c9c9 ;-fx-border-color: "+ guichet.getCouleurBord()+";-fx-border-width: 2");
            client.setPrefSize(25,25);
            zoneClient.getChildren().add(client);
        }
        zoneClient.setAlignment(Pos.CENTER);

        this.getChildren().addAll(this.titre,zoneClient);
    }

    @Override
    public void reagir() {

    }
}
