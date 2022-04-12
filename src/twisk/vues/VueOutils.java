package twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueOutils extends TilePane implements Observateur{

    private MondeIG monde;
    private Button ajouter;

    /**
     * Constructeur
     * @param monde Un monde
     */
    public VueOutils(MondeIG monde){
        super();
        this.monde = monde;
        this.setStyle("-fx-background-color: "+this.monde.getStyle());

        this.ajouter = new Button();
        this.ajouter.setText("");
        this.ajouter.setOnAction(event->this.monde.ajouter("Activité"));
        this.ajouter.setTooltip(new Tooltip("Ajouter une activité"));

        //Ajout icône
        Image imageEntree = new Image(getClass().getResourceAsStream("/twisk/ressources/images/boutonAjouter.png"),25,25,true,true);
        ImageView iconEntree = new ImageView(imageEntree);
        this.ajouter.setGraphic(iconEntree);

        //ajout du style
        TailleComposants constantes = TailleComposants.getInstance();
        this.ajouter.setPrefSize(constantes.getLargeurBoutonAjouter(),constantes.getLargeurBoutonAjouter());
        //this.ajouter.setStyle("-fx-text-fill: #619bdc; -fx-font-size: 20; -fx-border-weight: bold");

        this.getChildren().add(ajouter);

        this.monde.ajouterObservateur(this);
    }

    @Override
    public void reagir() {

    }
}
