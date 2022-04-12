package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import twisk.exceptions.TimeException;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class VueChangerEcartTemps extends TextInputDialog {
    private MondeIG monde;
    private ActiviteIG etapeAChanger;

    public VueChangerEcartTemps(MondeIG monde) {
        super();
        this.monde = monde;

        //Style de la boîte de dialogue
        this.setTitle("Changement de l'écart-temps");
        this.setHeaderText("Quelle est la nouvelle valeur ?");

        VueChangerEcartTemps vueCET = this;

        this.setOnCloseRequest(new EventHandler<DialogEvent>(){
            @Override
            public void handle(DialogEvent dialogEvent) {
                try{
                    etapeAChanger.setEcartTemps(vueCET.getResult());
                    vueCET.hide();
                    vueCET.monde.notifierObservateurs();
                }catch (TimeException e){
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
        });
    }

    public void setEtapeAChanger(EtapeIG etapeAChanger) {
        if(etapeAChanger.estUneActivite())
            this.etapeAChanger = (ActiviteIG) etapeAChanger;
    }
}
