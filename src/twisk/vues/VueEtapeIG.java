package twisk.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public abstract class VueEtapeIG extends VBox implements Observateur {
    protected MondeIG monde;
    protected EtapeIG etape;
    protected Label titre;

    /**
     * Constructeur
     * @param monde Un monde
     * @param etape Une étape
     */
    public VueEtapeIG(MondeIG monde, EtapeIG etape){
        super();
        this.monde = monde;
        this.etape = etape;
        this.titre = new Label();

        //Ajout du style
        this.setMinSize(etape.getLargeur(),etape.getHauteur());
        this.setMaxSize(etape.getLargeur(),etape.getHauteur());
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,10,10,10));
        this.setSpacing(10);
        this.titre.setStyle("-fx-text-fill: white; -fx-font-size: 14");

        //Ajout du symbole entree/sortie
        if(this.etape.estUneEntree()){
            //Label entree = new Label("E");
            Image imageEntree = new Image(getClass().getResourceAsStream("/twisk/ressources/images/entree.png"),18,18,true,true);
            ImageView iconEntree = new ImageView(imageEntree);
            this.titre.setGraphic(iconEntree);
        }
        if(this.etape.estUneSortie()){
            //Label sortie = new Label("S");
            Image imageSortie = new Image(getClass().getResourceAsStream("/twisk/ressources/images/sortie.png"),14,14,true,true);
            ImageView iconSortie = new ImageView(imageSortie);
            this.titre.setGraphic(iconSortie);
        }
        if(this.etape.estUnGuichet()){
            Image imageGuichet = new Image(getClass().getResourceAsStream("/twisk/ressources/images/guichet.png"),20,20,true,true);
            ImageView iconGuichet = new ImageView(imageGuichet);
            this.titre.setGraphic(iconGuichet);
        }
        if(this.etape.estUneActivite() || this.etape.estUneActiviteRestreinte()){
            Image imageGuichet = new Image(getClass().getResourceAsStream("/twisk/ressources/images/activite.png"),20,20,true,true);
            ImageView iconGuichet = new ImageView(imageGuichet);
            this.titre.setGraphic(iconGuichet);
        }

        //Ajout de la possibilité de sélectionné une étape
        this.setOnMouseClicked(mouseEvent -> this.monde.selectionneEtape(etape));

        //Ajout de la possibilité de déplacement
        this.setOnDragDetected(mouseEvent -> {
            //On détecte le déplacement de souris
                //System.out.println("DnD detecté.");
            final Dragboard dgB = this.startDragAndDrop(TransferMode.MOVE);

            //On remplie le contenu
            final ClipboardContent content = new ClipboardContent();
            content.putString(etape.getIdentifiant());
                //System.out.println("id etape : "+content.getString());

            //On transfert l'image de l'activité
            final WritableImage capture = this.snapshot(null,null);
            content.putImage(capture);

            dgB.setContent(content);
            mouseEvent.consume();
        });
    }

    @Override
    public abstract void reagir();
}
