package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

public class EcouteurPDC implements EventHandler<MouseEvent> {

    private MondeIG monde;
    private PointDeControleIG pdc;

    public EcouteurPDC(MondeIG monde, PointDeControleIG pdc) {
        this.monde = monde;
        this.pdc = pdc;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        try {
            this.monde.selectionnerPoint(pdc);
        } catch (TwiskException e) {
            //e.printStackTrace();
            Alert panneauAlerte = new Alert(Alert.AlertType.ERROR);
            panneauAlerte.setTitle("Erreur: twisk");
            panneauAlerte.setHeaderText(e.getMessage());
            panneauAlerte.show();
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
            pauseTransition.setOnFinished(actionEvent -> {
                panneauAlerte.hide();
            });
            pauseTransition.play();
        }
    }
}
