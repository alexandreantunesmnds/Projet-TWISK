package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

import java.util.List;

public class VueMenu extends MenuBar implements Observateur{
    private MondeIG monde;
    private VueRenommerEtape VueRE;
    private VueChangerTemps VueCT;
    private VueChangerEcartTemps VueCET;
    private VueChangerNbJetons VueCJ;
    private String style;

    public VueMenu(MondeIG monde) {
        super();
        this.monde = monde;
        this.VueRE = new VueRenommerEtape(this.monde);
        this.VueCT = new VueChangerTemps(this.monde);
        this.VueCET = new VueChangerEcartTemps(this.monde);
        this.VueCJ = new VueChangerNbJetons(this.monde);

        //Menu Fichier
        Menu fichier = new Menu("Fichier");
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setOnAction(event-> Platform.exit());

        fichier.getItems().addAll(quitter);


        //Menu Edition
        Menu edition = new Menu("Edition");
        MenuItem suppr = new MenuItem("Supprimer la sélection");
        suppr.setOnAction(event -> {
            this.monde.supprimerEtapesSelectionnees();
            this.monde.supprimerArc(null);
            this.monde.notifierObservateurs();
        });
        MenuItem renommer = new MenuItem("Renommer");
        renommer.setOnAction(event -> {
            List<EtapeIG> etapeSelectionnees = this.monde.getEtapeSelectionnees();
            if(etapeSelectionnees != null && etapeSelectionnees.size() == 1){
                //System.out.println("blabla");
                EtapeIG etapeARenom = etapeSelectionnees.get(0);
                this.VueRE.setEtapeARenom(etapeARenom);
                this.VueRE.show();
            }
        });
        MenuItem effacerSelection = new MenuItem("Effacer la sélection");
        effacerSelection.setOnAction(event -> this.monde.effacerSelection());

        edition.getItems().addAll(suppr,renommer,effacerSelection);


        //Menu Monde
        Menu menuMonde = new Menu("Monde");
        MenuItem addEntree = new MenuItem("Entrée");
        Image imageEntree = new Image(getClass().getResourceAsStream("/twisk/ressources/images/entree.png"),18,18,true,true);
        ImageView iconEntree = new ImageView(imageEntree);
        addEntree.setGraphic(iconEntree);
        addEntree.setOnAction(event -> {
            List<EtapeIG> etapeSelectionnes = this.monde.getEtapeSelectionnees();
            for(EtapeIG etape : etapeSelectionnes){
                etape.setEntree();
            }
            this.monde.notifierObservateurs();
        });
        MenuItem addSortie = new MenuItem("Sortie");
        Image imageSortie = new Image(getClass().getResourceAsStream("/twisk/ressources/images/sortie.png"),14,14,true,true);
        ImageView iconSortie = new ImageView(imageSortie);
        addSortie.setGraphic(iconSortie);
        addSortie.setOnAction(event -> {
            List<EtapeIG> etapeSelectionnes = this.monde.getEtapeSelectionnees();
            for(EtapeIG etape : etapeSelectionnes){
                etape.setSortie();
            }
            this.monde.notifierObservateurs();
        });

        menuMonde.getItems().addAll(addEntree,addSortie);

        //Menu Paramètre
        Menu param = new Menu("Paramètre");
        MenuItem temps = new MenuItem("Changer le temps");
        temps.setOnAction(event ->{
            List<EtapeIG> etapeSelectionnees = this.monde.getEtapeSelectionnees();
            if(etapeSelectionnees != null && etapeSelectionnees.size() == 1 && etapeSelectionnees.get(0).estUneActivite()){
                //System.out.println("blabla");
                EtapeIG etapeAChanger = etapeSelectionnees.get(0);
                this.VueCT.setEtapeAChanger(etapeAChanger);
                this.VueCT.show();
            }
        });
        MenuItem ecartTemps = new MenuItem("Changer ecart-temps");
        ecartTemps.setOnAction(event ->{
            List<EtapeIG> etapeSelectionnees = this.monde.getEtapeSelectionnees();
            if(etapeSelectionnees != null && etapeSelectionnees.size() == 1 && etapeSelectionnees.get(0).estUneActivite()){
                //System.out.println("blabla");
                EtapeIG etapeAChanger = etapeSelectionnees.get(0);
                this.VueCET.setEtapeAChanger(etapeAChanger);
                this.VueCET.show();
            }
        });
        MenuItem nbJetons = new MenuItem("Changer le nombre de jetons");
        nbJetons.setOnAction(event ->{
            List<EtapeIG> etapeSelectionnees = this.monde.getEtapeSelectionnees();
            if(etapeSelectionnees != null && etapeSelectionnees.size() == 1 && etapeSelectionnees.get(0).estUnGuichet()){
                //System.out.println("blabla");
                EtapeIG etapeAChanger = etapeSelectionnees.get(0);
                this.VueCJ.setEtapeAChanger(etapeAChanger);
                this.VueCJ.show();
            }
        });

        param.getItems().addAll(temps,ecartTemps,nbJetons);

        //Menu Style
        Menu style = new Menu("Style");
        Menu theme = new Menu("Theme");
        ToggleGroup tg = new ToggleGroup();
        RadioMenuItem white = new RadioMenuItem("Theme claire");
        white.setToggleGroup(tg);
        white.setSelected(true); //theme clair par défaut
        RadioMenuItem black = new RadioMenuItem("Theme foncé");
        black.setToggleGroup(tg);

        white.setOnAction(event -> {
            this.monde.changerStyle("CLAIR");
        });
        black.setOnAction(event -> {
            this.monde.changerStyle("FONCE");
        });

        theme.getItems().addAll(white,black);
        style.getItems().add(theme);

        //Ajout dans la barre de menu
        this.getMenus().addAll(fichier,edition,menuMonde,param,style);
    }

    @Override
    public void reagir() {

    }
}
