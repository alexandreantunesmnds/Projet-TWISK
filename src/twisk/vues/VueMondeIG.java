package twisk.vues;

import javafx.application.Platform;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import twisk.mondeIG.*;
import twisk.outils.TailleComposants;
import twisk.simulation.Client;
import twisk.simulation.Simulation;

import java.util.Random;

public class  VueMondeIG extends Pane implements Observateur{

    private MondeIG monde;
    private VueOutils vueOutils;

    /**
     * Constructeur
     * @param monde Un monde
     */
    public VueMondeIG(MondeIG monde, VueOutils vueOutils){
        super();
        this.monde = monde;
        this.vueOutils = vueOutils;

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
        Pane panneau = this;
        Runnable command = new Runnable() {
            @Override
            public void run() {
                panneau.getChildren().clear();
                setStyle("-fx-background-color: "+ monde.getStyle());

                for(ArcIG arc: monde.iteratorArcIG()){
                    VueArcIG nouvelArc = new VueArcIG(monde,arc);
                    if(monde.getArcSelectionnes().contains(arc)){
                        nouvelArc.selectionnerArc();
                    }
                    panneau.getChildren().add(nouvelArc);
                }

                for(EtapeIG etape: monde){
                    VueEtapeIG nouvelleEtape = null;
                    if(etape.estUneActivite()) {
                        nouvelleEtape = new VueActiviteIG(monde,(ActiviteIG) etape);
                    }else if(etape.estUnGuichet()){
                        nouvelleEtape = new VueGuichetIG(monde,(GuichetIG) etape);
                    }
                    nouvelleEtape.relocate(etape.getPosX(),etape.getPosY());

                    //On vérifie qu'une étape a été selectionné et si oui on lui change de couleur
                    if(monde.getEtapeSelectionnees().contains(etape)) {
                        nouvelleEtape.setStyle("-fx-background-color: "+etape.getCouleur()+";-fx-background-radius: 10px,10px,10px,10px;-fx-border-color: red; -fx-border-radius: 10px,10px,10px,10px;-fx-border-width: 2");
                    }

                    panneau.getChildren().add(nouvelleEtape);


                    for(PointDeControleIG pdc : etape){
                        VuePointDeControleIG nouveaupdc = new VuePointDeControleIG(monde,pdc);
                        //System.out.println( pdc.getPosX()+","+pdc.getPosY());
                        panneau.getChildren().add(nouveaupdc);
                    }

                    try {
                        if (monde.estEnSimulation()){
                            vueOutils.changeSimStop();
                            for (Client cl : monde.getClients()) {
                                if(monde.getCorresEtap().get(etape).equals(cl.getEtape())) {
                                    Circle client = new Circle();

                                    if(etape.estUneActivite()) {
                                        int min_x = 20;
                                        int max_x = 200;

                                        int min_y = 50;
                                        int max_y = 85;
                                        int x = min_x + (int) (Math.random() * (max_x - min_x) + 1);
                                        int y = min_y + (int) (Math.random() * (max_y - min_y) + 1);

                                        client.setCenterX(etape.getPosX() + x);
                                        client.setCenterY(etape.getPosY() + y);
                                    }else if(etape.estUnGuichet()){
                                        int x = 19;
                                        int y = 49;

                                        client.setCenterX(etape.getPosX() + (x*cl.getRang()));
                                        client.setCenterY(etape.getPosY() + y);
                                    }
                                    client.setRadius(TailleComposants.getInstance().getTailleClient());
                                    client.setFill(Paint.valueOf(cl.getCouleur()));
                                    panneau.getChildren().add(client);
                                    System.out.println("Coord client "+ cl.getNumeroClient() +" : ("+client.getCenterX()+", "+client.getCenterY()+")");
                                }
                            }
                        }
                        else{
                            vueOutils.changeSimPlay();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        };
        if(Platform.isFxApplicationThread()){
            command.run();
        }
        else{
            Platform.runLater(command);
        }
    }
}
