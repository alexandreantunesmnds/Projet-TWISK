package twisk.vues;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.TailleComposants;

import static java.lang.Math.*;

public class VueArcIG extends Pane implements Observateur {

    private Polyline pl;
    private Line ligne;
    private ArcIG arc;
    private MondeIG monde;

    /**
     * Constructeur
     * @param arc Un arc
     */
    public VueArcIG(MondeIG monde, ArcIG arc) {
        super();
        this.arc = arc;
        this.monde = monde;

        TailleComposants constantes = TailleComposants.getInstance();
        double taillePoint = constantes.getTaillePDC();
        //System.out.println("test");

        PointDeControleIG pdc1 = this.arc.getPt1();
        PointDeControleIG pdc2 = this.arc.getPt2();

        ligne = new Line();
        ligne.setStartX(pdc1.getPosX()+taillePoint);
        ligne.setStartY(pdc1.getPosY()+taillePoint);
        ligne.setEndX(pdc2.getPosX()+taillePoint);
        ligne.setEndY(pdc2.getPosY()+taillePoint);
        ligne.setStroke(Paint.valueOf(arc.getCouleur()));
        ligne.setStrokeWidth(5);

        pl = dessinerTriangle(pdc1,pdc2);
        //pl.setRotationAxis(ligne.getRotationAxis());
        pl.setRotate(ligne.getRotate());

        //Ajout de la possibilité de sélectionné un arc
        this.setOnMouseClicked(mouseEvent -> this.monde.selectionneArc(this.arc));

        this.getChildren().addAll(ligne,pl);

    }

    /**
     * Fonction qui dessine un Triangle au bout de la flèche
     * @param pt1 un point
     * @param pt2 un point
     * @return Un triangle
     */
    public Polyline dessinerTriangle(PointDeControleIG pt1, PointDeControleIG pt2){
        TailleComposants constantes = TailleComposants.getInstance();
        double tailleTriangle = constantes.getTailleTriangle();
        double taillePDC = constantes.getTaillePDC();

        Polyline pl = new Polyline();

        //On calcule l'angle
        double ang = atan2(pt1.getPosY() - pt2.getPosY(), pt1.getPosX() - pt2.getPosX());

        double posX1 = (pt2.getPosX() + tailleTriangle * cos(ang + PI / 4));
        double posY1 = (pt2.getPosY() + tailleTriangle * sin(ang + PI / 4));

        double posX2 = (pt2.getPosX() + tailleTriangle * cos(ang - PI / 4));
        double posY2 = (pt2.getPosY() + tailleTriangle * sin(ang - PI / 4));

        pl.getPoints().addAll(posX1 + taillePDC, posY1 + taillePDC, posX2 + taillePDC, posY2 + taillePDC,(double)pt2.getPosX() + taillePDC, (double)pt2.getPosY() + taillePDC, posX1 + taillePDC, posY1+ taillePDC);

            pl.setStroke(Paint.valueOf(arc.getCouleur()));
        pl.setFill(Paint.valueOf(arc.getCouleur()));
        //pl.setRotate(180);
        return pl;
    }

    /**
     * Fonction qui change la couleur de l'arc
     */
    public void selectionnerArc(){
        ligne.setStroke(Paint.valueOf("red"));
        pl.setStroke(Paint.valueOf("red"));
        pl.setFill(Paint.valueOf("red"));
    }

    @Override
    public void reagir() {

    }
}
