package twisk.vues;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.TailleComposants;

public class VuePointDeControleIG extends Circle implements Observateur{

    /**
     * Constructeur
     * @param monde Un monde
     * @param pdc Une point de contrôle
     */
    public VuePointDeControleIG(MondeIG monde,PointDeControleIG pdc) {
        super(TailleComposants.getInstance().getTaillePDC());
        this.setFill(Paint.valueOf("red"));
        this.relocate(pdc.getPosX(),pdc.getPosY());
        this.setOnMouseClicked(new EcouteurPDC(monde,pdc));

    }

    @Override
    public void reagir() {

    }
}
