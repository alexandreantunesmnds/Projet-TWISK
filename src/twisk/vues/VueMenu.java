package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import twisk.exceptions.FileException;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.ThreadsManager;

import java.util.List;
import java.util.Optional;

public class VueMenu extends MenuBar implements Observateur{
    private MondeIG monde;
    private VueRenommerEtape VueRE;
    private VueChangerTemps VueCT;
    private VueChangerEcartTemps VueCET;
    private VueChangerNbJetons VueCJ;
    private String style;

    private Menu fichier;
    private Menu edition;
    private Menu menuMonde;
    private Menu simulation;
    private Menu param;
    private Menu menutyle;

    private MenuItem renommer;
    private MenuItem temps;
    private MenuItem ecartTemps;
    private MenuItem nbJetons;

    public VueMenu(MondeIG monde) {
        super();
        this.monde = monde;
        this.VueRE = new VueRenommerEtape(this.monde);
        this.VueCT = new VueChangerTemps(this.monde);
        this.VueCET = new VueChangerEcartTemps(this.monde);
        this.VueCJ = new VueChangerNbJetons(this.monde);

        //Menu Fichier
        fichier = new Menu("Fichier");
        MenuItem save = new MenuItem("Enregistrer-sous");
        save.setOnAction(event->{
            try {
                monde.enregistrer();
            } catch (FileException e) {
                e.printStackTrace();
            }
        });
        MenuItem open = new MenuItem("Ouvrir");
        open.setOnAction(event-> {
            try {
                monde.ouvrirFichier();
            } catch (FileException e) {
                e.printStackTrace();
            }

        });
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setOnAction(event-> {Platform.exit();
            ThreadsManager.getInstance().detruireTout();
        });

        fichier.getItems().addAll(save,open,quitter);


        //Menu Edition
        edition = new Menu("Edition");
        MenuItem suppr = new MenuItem("Supprimer la sélection");
        suppr.setOnAction(event -> {
            for(EtapeIG etapeASuppr : this.monde.getEtapeSelectionnees()){
                this.monde.supprimerArc(etapeASuppr);
            }
            this.monde.supprimerEtapesSelectionnees();
            this.monde.notifierObservateurs();
        });
        renommer = new MenuItem("Renommer");
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
        menuMonde = new Menu("Monde");
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
        param = new Menu("Paramètre");
        temps = new MenuItem("Changer le temps");
        temps.setOnAction(event ->{
            List<EtapeIG> etapeSelectionnees = this.monde.getEtapeSelectionnees();
            if(etapeSelectionnees != null && etapeSelectionnees.size() == 1 && etapeSelectionnees.get(0).estUneActivite()){
                //System.out.println("blabla");
                EtapeIG etapeAChanger = etapeSelectionnees.get(0);
                this.VueCT.setEtapeAChanger(etapeAChanger);
                this.VueCT.show();
            }
        });
        ecartTemps = new MenuItem("Changer ecart-temps");
        ecartTemps.setOnAction(event ->{
            List<EtapeIG> etapeSelectionnees = this.monde.getEtapeSelectionnees();
            if(etapeSelectionnees != null && etapeSelectionnees.size() == 1 && etapeSelectionnees.get(0).estUneActivite()){
                //System.out.println("blabla");
                EtapeIG etapeAChanger = etapeSelectionnees.get(0);
                this.VueCET.setEtapeAChanger(etapeAChanger);
                this.VueCET.show();
            }
        });


        nbJetons = new MenuItem("Changer le nombre de jetons");
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

        //Menu Simulation
        simulation = new Menu("Simulation");
        MenuItem nbClients = new MenuItem("Changer le nombre de clients");
        nbClients.setOnAction(event ->{
            TextInputDialog cbClient = new TextInputDialog("");
            cbClient.setTitle("Choisir le nombre de clients");
            cbClient.setHeaderText("Entrez un nombre de clients :");

            Optional<String> result = cbClient.showAndWait();

            result.ifPresent(cbC -> {
                try {
                    monde.setNbClients(Integer.parseInt(cbC));
                }catch (NumberFormatException e){
                    Alert panneauAlerte = new Alert(Alert.AlertType.ERROR);
                    panneauAlerte.setTitle("Erreur: twisk");
                    panneauAlerte.setHeaderText("Vous n'avez pas saisi un nombre correct");
                    panneauAlerte.show();
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
                    pauseTransition.setOnFinished(actionEvent -> {
                        panneauAlerte.hide();
                    });
                    pauseTransition.play();
                }
            });

        });
        Menu choixLoi = new Menu("Changer la loi d'arrivée des clients");
        ToggleGroup loi = new ToggleGroup();
        RadioMenuItem lUni = new RadioMenuItem("Loi uniforme");
        lUni.setOnAction(event ->{
            monde.setLoi("uniforme");
        });
        lUni.setToggleGroup(loi);
        lUni.setSelected(true);
        RadioMenuItem lGau = new RadioMenuItem("Loi gaussienne");
        lGau.setOnAction(event ->{
            monde.setLoi("gaussienne");
        });
        lGau.setToggleGroup(loi);
        RadioMenuItem lPoi = new RadioMenuItem("Loi de Poisson");
        lPoi.setOnAction(event ->{
            monde.setLoi("poisson");
        });
        lPoi.setToggleGroup(loi);
        choixLoi.getItems().addAll(lUni,lGau,lPoi);
        simulation.getItems().addAll(nbClients,choixLoi);

        //Menu Style
        menutyle = new Menu("Style");
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
        menutyle.getItems().add(theme);

        //Ajout dans la barre de menu
        this.getMenus().addAll(fichier,edition,menuMonde,param,simulation,menutyle);

        this.monde.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
        //Conditions sur les items
        if(this.monde.estEnSimulation()){
            fichier.setDisable(true);
            edition.setDisable(true);
            menuMonde.setDisable(true);
            param.setDisable(true);
            simulation.setDisable(true);
            menutyle.setDisable(true);
        }else{
            fichier.setDisable(false);
            edition.setDisable(false);
            menuMonde.setDisable(false);
            param.setDisable(false);
            simulation.setDisable(false);
            menutyle.setDisable(false);
            if (this.monde.getNbEtapesSelect() == 1){
                if(this.monde.getEtapeSelectionnees().get(0).estUneActivite()){
                    this.renommer.setDisable(false);
                    this.temps.setDisable(false);
                    this.ecartTemps.setDisable(false);
                }else{
                    this.nbJetons.setDisable(false);
                }
            }
            else{
                this.renommer.setDisable(true);
                this.temps.setDisable(true);
                this.ecartTemps.setDisable(true);
                this.nbJetons.setDisable(true);
            }
        }
    }
}
