package twisk.vues;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import twisk.mondeIG.*;
import twisk.simulation.Client;
import twisk.simulation.Simulation;

public class  VueMondeIG extends Pane implements Observateur{

    private MondeIG monde;

    /**
     * Constructeur
     * @param monde Un monde
     */
    public VueMondeIG(MondeIG monde){
        super();
        this.monde = monde;

        for(EtapeIG etape: this.monde){
            VueEtapeIG nouvelleEtape = null;
            if(etape.estUneActivite()) {
                nouvelleEtape = new VueActiviteIG(this.monde,(ActiviteIG) etape);
            }
            //System.out.println(etape.toString());
            nouvelleEtape.relocate(etape.getPosX(),etape.getPosY());
            nouvelleEtape.resize(etape.getLargeur(),etape.getHauteur());
            this.getChildren().add(nouvelleEtape);
        }

        //Ajout de la possibilité de déplacement
        this.setOnDragOver(dragEvent -> { //Ici on valide le transfert
            //On détecte le déplacement de souris
                //System.out.println("Drag over");

            //On valide le tranfert
            dragEvent.acceptTransferModes(TransferMode.MOVE);
                //System.out.println("transfert accepté");

            dragEvent.consume();
        });

        this.setOnDragDropped(dragEvent -> {
            //On détecte le déplacement de souris
            final Dragboard dgB = dragEvent.getDragboard();
                //System.out.println("Drag dropped");

            boolean success = false;

            if(dgB.hasString()) {
                //On récupère le contenu
                final ClipboardContent content = new ClipboardContent();
                content.putString(dgB.getString());

                //On regarde le contenu
                    //System.out.println("id dans le monde lors du drop : " + content.getString());
                    //System.out.println("Coordonnée : x=" + dragEvent.getX() + " ,y= " + dragEvent.getY());
                this.monde.deplacerEtape(dgB.getString(),dragEvent.getX(),dragEvent.getY());
            }
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
        });

        this.monde.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
        this.getChildren().clear();
        this.setStyle("-fx-background-color: "+this.monde.getStyle());

        for(ArcIG arc: this.monde.iteratorArcIG()){
            VueArcIG nouvelArc = new VueArcIG(monde,arc);
            if(this.monde.getArcSelectionnes().contains(arc)){
                nouvelArc.selectionnerArc();
            }
            this.getChildren().add(nouvelArc);
        }

        for(EtapeIG etape: this.monde){
            VueEtapeIG nouvelleEtape = null;
            if(etape.estUneActivite()) {
                nouvelleEtape = new VueActiviteIG(this.monde,(ActiviteIG) etape);
            }else if(etape.estUnGuichet()){
                nouvelleEtape = new VueGuichetIG(this.monde,(GuichetIG) etape);
            }
            nouvelleEtape.relocate(etape.getPosX(),etape.getPosY());

            //On vérifie qu'une étape a été selectionné et si oui on lui change de couleur
            if(this.monde.getEtapeSelectionnees().contains(etape)) {
                nouvelleEtape.setStyle("-fx-background-color: "+etape.getCouleur()+";-fx-background-radius: 10px,10px,10px,10px;-fx-border-color: red; -fx-border-radius: 10px,10px,10px,10px;-fx-border-width: 2");
            }

            this.getChildren().add(nouvelleEtape);


            for(PointDeControleIG pdc : etape){
                VuePointDeControleIG nouveaupdc = new VuePointDeControleIG(this.monde,pdc);
                //System.out.println( pdc.getPosX()+","+pdc.getPosY());
                this.getChildren().add(nouveaupdc);
            }
        }

        try {
            if (this.monde.estEnSimulation()){
                System.out.println("test");
                for (Client cl : this.monde.getClients()) {
                    System.out.println(cl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
