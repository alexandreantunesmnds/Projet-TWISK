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
    private Button ajouterActivite;
    private Button ajouterGuichet;

    /**
     * Constructeur
     * @param monde Un monde
     */
    public VueOutils(MondeIG monde){
        super();
        this.monde = monde;
        this.setStyle("-fx-background-color: "+this.monde.getStyle());

        //Bouton activite
        this.ajouterActivite = new Button();
        this.ajouterActivite.setText("");
        this.ajouterActivite.setOnAction(event->this.monde.ajouter("Activité"));
        this.ajouterActivite.setTooltip(new Tooltip("Ajouter une activité"));

        //Bouton guichet
        this.ajouterGuichet = new Button();
        this.ajouterGuichet.setText("");
        this.ajouterActivite.setOnAction(event->this.monde.ajouter("Guichet"));
        this.ajouterGuichet.setTooltip(new Tooltip("Ajouter un guichet"));

        //Ajout icône
        Image imagePlus = new Image(getClass().getResourceAsStream("/twisk/ressources/images/boutonAjouterActivite.png"),25,25,true,true);
        ImageView iconPlus = new ImageView(imagePlus);
        this.ajouterActivite.setGraphic(iconPlus);
        imagePlus = new Image(getClass().getResourceAsStream("/twisk/ressources/images/boutonAjouterGuichet.png"),25,25,true,true);
        iconPlus = new ImageView(imagePlus);
        this.ajouterGuichet.setGraphic(iconPlus);

        //ajout du style
        TailleComposants constantes = TailleComposants.getInstance();
        this.ajouterActivite.setPrefSize(constantes.getLargeurBoutonAjouter(),constantes.getLargeurBoutonAjouter());
        this.ajouterGuichet.setPrefSize(constantes.getLargeurBoutonAjouter(),constantes.getLargeurBoutonAjouter());

        this.getChildren().addAll(ajouterActivite,ajouterGuichet);

        this.monde.ajouterObservateur(this);
    }

    @Override
    public void reagir() {

    }
}
