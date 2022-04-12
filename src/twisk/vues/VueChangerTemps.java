package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import twisk.exceptions.TimeException;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class VueChangerTemps extends TextInputDialog {
    private MondeIG monde;
    private ActiviteIG etapeAChanger;

    public VueChangerTemps(MondeIG monde) {
        super();
        this.monde = monde;
        VueChangerTemps vueCT = this;

        //Style de la boîte de dialogue
        this.setTitle("Changement du temps");
        this.setHeaderText("Quelle est la nouvelle valeur ?");

        this.setOnCloseRequest(new EventHandler<DialogEvent>(){
            @Override
            public void handle(DialogEvent dialogEvent) {
                try{
                    etapeAChanger.setTemps(vueCT.getResult());
                    vueCT.hide();
                    vueCT.monde.notifierObservateurs();
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
