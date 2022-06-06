package twisk.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import twisk.exceptions.MondeException;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.outils.ThreadsManager;

import java.sql.SQLOutput;

public class VueOutils extends HBox implements Observateur{

    private MondeIG monde;
    private Button ajouterActivite;
    private Button ajouterGuichet;
    private Button playSim;

    private Label infoSimulation;

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
        this.ajouterGuichet.setOnAction(event->this.monde.ajouter("Guichet"));
        this.ajouterGuichet.setTooltip(new Tooltip("Ajouter un guichet"));

        //Bouton simulation
        this.playSim = new Button();
        this.playSim.setText("");
        this.playSim.setOnAction(event-> {
            try {
                this.monde.simuler();
            } catch (MondeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(250);
                alert.setTitle("Erreur : simulation impossible");
                alert.setHeaderText("Simulation impossible");
                alert.setContentText("La simulation est impossible car le monde n'est pas valide dû à :\n"+e.getMessage());
                alert.showAndWait();
            }
        });
        this.playSim.setTooltip(new Tooltip("Lancer la simulation"));

        //Ajout icône
        Image imagePlus = new Image(getClass().getResourceAsStream("/twisk/ressources/images/boutonAjouterActivite.png"),25,25,true,true);
        ImageView iconPlus = new ImageView(imagePlus);
        this.ajouterActivite.setGraphic(iconPlus);
        imagePlus = new Image(getClass().getResourceAsStream("/twisk/ressources/images/boutonAjouterGuichet.png"),25,25,true,true);
        iconPlus = new ImageView(imagePlus);
        this.ajouterGuichet.setGraphic(iconPlus);
        imagePlus = new Image(getClass().getResourceAsStream("/twisk/ressources/images/play.png"),25,25,true,true);
        iconPlus = new ImageView(imagePlus);
        this.playSim.setGraphic(iconPlus);

        //ajout du style
        TailleComposants constantes = TailleComposants.getInstance();
        this.ajouterActivite.setPrefSize(constantes.getLargeurBoutonAjouter(),constantes.getLargeurBoutonAjouter());
        this.ajouterGuichet.setPrefSize(constantes.getLargeurBoutonAjouter(),constantes.getLargeurBoutonAjouter());
        this.playSim.setPrefSize(constantes.getLargeurBoutonAjouter(),constantes.getLargeurBoutonAjouter());

        //Label
        infoSimulation = new Label("Simulation : "+this.monde.getNbClients()+" clients, loi de probabilité : "+this.monde.getLoi());
        infoSimulation.setPrefHeight(TailleComposants.getInstance().getLargeurBoutonAjouter());
        infoSimulation.setPadding(new Insets(10));
        infoSimulation.setFont(new Font(18));

        this.getChildren().addAll(ajouterActivite,ajouterGuichet,playSim,infoSimulation);

        this.monde.ajouterObservateur(this);
    }
    public void changeSimPlay() {
        Image image = new Image(getClass().getResourceAsStream("/twisk/ressources/images/play.png"),25,25,true,true);
        ImageView iconPlay = new ImageView(image);
        this.playSim.setGraphic(iconPlay);
        this.playSim.setOnAction(event2-> {
            try {
                this.ajouterGuichet.setDisable(true);
                this.ajouterActivite.setDisable(true);
                this.monde.simuler();
            } catch (MondeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(250);
                alert.setTitle("Erreur : simulation impossible");
                alert.setHeaderText("Simulation impossible");
                alert.setContentText("La simulation est impossible car le monde n'est pas valide dû à :\n"+e.getMessage());
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * Fonction qui s'occupe de modifier l'image du bouton play en bouton stop
     */
    public void changeSimStop(){
            Image imagePlus = new Image(getClass().getResourceAsStream("/twisk/ressources/images/stop.png"), 25, 25, true, true);
            ImageView iconPlus = new ImageView(imagePlus);
            this.playSim.setGraphic(iconPlus);
            this.playSim.setOnAction(event-> {
                ThreadsManager.getInstance().detruireTacheActive();
                this.monde.setEstEnSimulation(false);
                this.ajouterGuichet.setDisable(false);
                this.ajouterActivite.setDisable(false);
                Image image = new Image(getClass().getResourceAsStream("/twisk/ressources/images/play.png"),25,25,true,true);
                ImageView iconPlay = new ImageView(image);
                this.playSim.setGraphic(iconPlay);
                this.playSim.setOnAction(event2-> this.changeSimPlay());
            });
    }


    @Override
    public void reagir() {
        infoSimulation.setText("Simulation : "+this.monde.getNbClients()+" clients, loi de probabilité : " + this.monde.getLoi());
        if(this.monde.estEnSimulation()){
            this.ajouterGuichet.setDisable(true);
            this.ajouterActivite.setDisable(true);
        }else{
            this.ajouterGuichet.setDisable(false);
            this.ajouterActivite.setDisable(false);
        }
    }
}
