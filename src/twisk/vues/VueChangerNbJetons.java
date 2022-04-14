package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;

public class VueChangerNbJetons  extends TextInputDialog {
    private MondeIG monde;
    private GuichetIG etapeAChanger;

    public VueChangerNbJetons(MondeIG monde) {
        super();
        this.monde = monde;

        //Style de la boîte de dialogue
        this.setTitle("Changement de l'écart-temps");
        this.setHeaderText("Quelle est la nouvelle valeur ?");

        VueChangerNbJetons vueCJ = this;

        this.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                etapeAChanger.setNbJetons(Integer.parseInt(vueCJ.getResult()));
                vueCJ.hide();
                vueCJ.monde.notifierObservateurs();
            }
        });
    }

    public void setEtapeAChanger(EtapeIG etapeAChanger) {
        if(etapeAChanger.estUnGuichet())
            this.etapeAChanger = (GuichetIG) etapeAChanger;
    }
}
