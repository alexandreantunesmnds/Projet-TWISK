package twisk.vues;

import javafx.scene.control.TextInputDialog;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

import java.util.List;

public class VueRenommerEtape extends TextInputDialog {
    private MondeIG monde;
    private EtapeIG etapeARenom;

    public VueRenommerEtape(MondeIG monde) {
        super();
        this.monde = monde;
        this.etapeARenom = etapeARenom;

        this.setOnCloseRequest(dialogEvent -> {
            if (etapeARenom!=null)
                etapeARenom.setNom(this.getResult());
            this.hide();
            this.monde.notifierObservateurs();
        });
    }

    public void setEtapeARenom(EtapeIG etapeARenom) {
        this.etapeARenom = etapeARenom;
    }
}
