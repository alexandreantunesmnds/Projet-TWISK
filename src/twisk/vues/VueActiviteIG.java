package twisk.vues;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueActiviteIG extends VueEtapeIG implements Observateur{

    /**
     * Constructeur
     * @param monde Un monde
     * @param etape Une étape
     */
    public VueActiviteIG(MondeIG monde, ActiviteIG etape) {
        super(monde, etape);
        this.titre.setText(etape.getNom()+" : "+etape.getTemps()+" temps ± "+etape.getEcartTemps());

        HBox zoneClient = new HBox();

        //Ajout des styles
        TailleComposants constantes = TailleComposants.getInstance();

        this.setStyle("-fx-background-color: "+ this.etape.getCouleur() +";-fx-background-radius: 10px,10px,10px,10px;-fx-border-color:"+etape.getCouleurBord()+"; -fx-border-radius: 10px,10px,10px,10px;-fx-border-width: 2");

        //Ajout de la zone de client
        zoneClient.setStyle("-fx-background-color: #c9c9c9 ;-fx-border-color: "+ etape.getCouleurBord()+";-fx-border-width: 2");
        zoneClient.setPrefSize(constantes.getLargeurZoneClient(), constantes.getHauteurZoneClient());

        this.getChildren().addAll(this.titre,zoneClient);
    }

    @Override
    public void reagir() {

    }
}
