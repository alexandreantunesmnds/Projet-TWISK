package twisk.mondeIG;

import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import twisk.exceptions.ArcException;
import twisk.exceptions.MondeException;
import twisk.exceptions.FileException;
import twisk.monde.*;
import twisk.outils.*;
import twisk.simulation.GestionnaireClients;
import twisk.vues.Observateur;
import twisk.vues.SujetObserve;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG>, Observateur {
    private final HashMap<String, EtapeIG> listeEtapes; //La clé est l'identifiant de l'étape
    private final List<ArcIG> listeArc;
    private PointDeControleIG pointSelectionne;
    private final List<EtapeIG> etapeSelectionnees;
    private final List<ArcIG> arcSelectionnes;
    private String style;
    private String theme;
    private CorrespondanceEtapes corresEtap;
    private Class<?> c;
    private Object play;
    private boolean estEnSimulation;
    private KitJson kjon;
    private int nbClients;
    private String loi;

    /**
     * Constructeur
     */
    public MondeIG() {
        this.listeEtapes = new HashMap<>();
        this.listeArc = new ArrayList<ArcIG>();
        this.arcSelectionnes = new ArrayList<ArcIG>();
        this.etapeSelectionnees = new ArrayList<EtapeIG>();
        this.theme = "CLAIR";
        this.style = "white";
        this.ajouter("Activité");
        this.estEnSimulation = false;
        this.kjon = new KitJson(this);
        this.nbClients = 5;
        this.loi = "uniforme";
    }

    /**
     * Constructeur
     */
    public MondeIG(EtapeIG... etapeIG) {
        this.listeEtapes = new HashMap<>();
        this.listeArc = new ArrayList<ArcIG>();
        this.arcSelectionnes = new ArrayList<ArcIG>();
        this.etapeSelectionnees = new ArrayList<EtapeIG>();
        this.theme = "CLAIR";
        this.style = "white";
        this.ajouter(etapeIG);
    }

    /**
     * Fonction qui ajoute une étape
     *
     * @param type le type de l'étape à ajouter
     */
    public void ajouter(String type) {
        EtapeIG nouvelleEtape;
        String idf = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        TailleComposants constantes = TailleComposants.getInstance();
        switch (type) {
            case "Activité":
                nouvelleEtape = new ActiviteIG("Etape " + idf, idf, constantes.getLargeurEtape(), constantes.getHauteurActivite());
                listeEtapes.put(nouvelleEtape.getIdentifiant(), nouvelleEtape);
                break;
            case "Guichet":
                nouvelleEtape = new GuichetIG("Guichet " + idf, idf, constantes.getLargeurEtape(), constantes.getHauteurGuichet(), 2);
                listeEtapes.put(nouvelleEtape.getIdentifiant(), nouvelleEtape);
            default:
                nouvelleEtape = new ActiviteIG("Etape " + idf, idf, 50, 30);
                break;
        }

        this.changerStyle(this.theme); //On met le theme du monde

        this.notifierObservateurs();

        //System.out.println("Ajout de l'étape : "+ nouvelleEtape.toString());
    }

    /**
     * Fonction qui ajoute manuellement des étapes dans le monde
     *
     * @param etapes les étapes à ajouter
     */
    public void ajouter(EtapeIG... etapes) {
        for (EtapeIG etape : etapes) {
            listeEtapes.put(etape.getIdentifiant(), etape);
        }
    }

    /**
     * Fonction qui ajoute un Arc
     *
     * @param pdc1 Le premier point de contrôle
     * @param pdc2 Le deuxième point de contrôle
     */
    public void ajouterArc(PointDeControleIG pdc1, PointDeControleIG pdc2) throws ArcException {
        if (verifierArc(pdc1, pdc2)) {
            this.listeArc.add(new ArcIG(pdc1, pdc2));
            this.changerStyle(this.theme); //On met le theme du monde

            //Puisque l'arc a été vérifié, on peut ajouter les successeurs
            EtapeIG etape1 = pdc1.getEtapeLiee();
            EtapeIG etape2 = pdc2.getEtapeLiee();
            etape1.ajouterSuccesseur(etape2);
            //System.out.println(etape1.getSucc().toString());

            this.notifierObservateurs();
        }
    }

    /**
     * Fonction qui indique au monde quel point a été selectionné
     *
     * @param pointSelectionne
     */
    public void selectionnerPoint(PointDeControleIG pointSelectionne) throws ArcException {
        if (this.pointSelectionne == null) {
            this.pointSelectionne = pointSelectionne;
        } else {
            this.ajouterArc(this.pointSelectionne, pointSelectionne);
            if(pointSelectionne.getEtapeLiee().estUnGuichet()){
                ((GuichetIG)pointSelectionne.getEtapeLiee()).verifierSensCirculation(pointSelectionne);
            }
            this.pointSelectionne = null;
            //System.out.println("test");
        }
    }

    /**
     * Fonction qui vérifie les contraintes liées à la création des arcs
     *
     * @param pdc1 Le premier point de contrôle
     * @param pdc2 Le deuxième point de contrôle
     * @return Retourne vrai si les contraintes sont vérifiées, faux sinon
     */
    public boolean verifierArc(PointDeControleIG pdc1, PointDeControleIG pdc2) throws ArcException {
        boolean rep = true;
        if (pdc1.equals(pdc2)) { //Si c'est le même point
            this.pointSelectionne = null;
            throw new ArcException("Erreur: Vous avez saisi deux fois le même point");
        }
        if (pdc1.getEtapeLiee().equals(pdc2.getEtapeLiee())) { //Si les deux points sont sur la même étape
            this.pointSelectionne = null;
            throw new ArcException("Erreur: Vous avez saisi deux points de la même étape");
        }
        if(pdc1.getEtapeLiee().estAccessibleDepuis(pdc2.getEtapeLiee())){
            this.pointSelectionne = null;
            throw new ArcException("Erreur : Vous avez créer une boucle");
        }
        for (ArcIG arcSaisi : this.listeArc) { //Si un des deux points à déjà été saisi
            PointDeControleIG pdcSaisi1 = arcSaisi.getPt1();
            PointDeControleIG pdcSaisi2 = arcSaisi.getPt2();
            if (pdcSaisi1.equals(pdc1) || pdcSaisi1.equals(pdc2)) { //Si le point à déjà été saisi
                this.pointSelectionne = null;
                throw new ArcException("Erreur: Un des points a déjà été saisi");
            }
            if (pdcSaisi2.equals(pdc1) || pdcSaisi2.equals(pdc2)) {
                this.pointSelectionne = null;
                throw new ArcException("Erreur: Un des points a déjà été saisi");
            }
        }

        return rep;
    }

    /**
     * Fonction qui indique au monde quelle étape a été selectionnée
     *
     * @param etapeSelectionne etape selectionnée
     */
    public void selectionneEtape(EtapeIG etapeSelectionne) {
        if (this.etapeSelectionnees.contains(etapeSelectionne)) {
            this.etapeSelectionnees.remove(etapeSelectionne);
        } else {
            this.etapeSelectionnees.add(etapeSelectionne);
            //System.out.println(this.etapeSelectionne.toString());
        }
        this.notifierObservateurs();
    }

    /**
     * Fonction qui indique au monde quelle étape a été selectionnée
     *
     * @param arcSelectionne arc selectionné
     */
    public void selectionneArc(ArcIG arcSelectionne) {
        if (this.arcSelectionnes.contains(arcSelectionne)) {
            this.arcSelectionnes.remove(arcSelectionne);
        } else {
            this.arcSelectionnes.add(arcSelectionne);
        }
        this.notifierObservateurs();
    }

    /**
     * Fonction qui supprime les étapes selectionnées
     */
    public void supprimerEtapesSelectionnees() {
        //System.out.println(listeEtapes);
        for (EtapeIG etapeASuppr : this.etapeSelectionnees) {
            if(etapeASuppr.getSucc().nbEtapes()>0){
                supprimerArc(etapeASuppr);
            }

            List<EtapeIG> etapeASuppSucc = new ArrayList<>();

            for(EtapeIG etape : this){
                for(EtapeIG etapeSucc : etape.getSucc()){
                    if(etapeSucc.equals(etapeASuppr)){
                        etapeASuppSucc.add(etape);
                    }
                }
            }
            for(EtapeIG etape : etapeASuppSucc){
                etape.getSucc().retirer(etapeASuppr);
            }

            this.listeEtapes.remove(etapeASuppr.getIdentifiant());
        }
        //System.out.println(listeEtapes);
        this.etapeSelectionnees.clear();
    }

    /**
     * Fonction qui supprime les arcs liées aux étapes selectionnées
     * @param etapeASuppr Les étapes selectionnées
     */
    public void supprimerArc(EtapeIG etapeASuppr) {
        List<ArcIG> listeArcASuprr = new ArrayList();
        for (ArcIG arc : this.iteratorArcIG()) {
            PointDeControleIG pdc1 = arc.getPt1();
            PointDeControleIG pdc2 = arc.getPt2();

            if (pdc1.getEtapeLiee().equals(etapeASuppr) || pdc2.getEtapeLiee().equals(etapeASuppr)) {
                listeArcASuprr.add(arc);
            }
        }
        for (ArcIG arcASuppr : listeArcASuprr) {
            this.listeArc.remove(arcASuppr);
        }
    }


        /**
         * Guetteur
         *
         * @return Retourne l'étape selectionnée
         */
    public List<EtapeIG> getEtapeSelectionnees() {
        return this.etapeSelectionnees;
    }

    /**
     * Guetteur
     *
     * @return Retourne les arcs selectionés
     */
    public List<ArcIG> getArcSelectionnes() {
        return this.arcSelectionnes;
    }

    /**
     * Guetteur
     *
     * @return récupère le style du monde
     */
    public String getStyle() {
        return style;
    }

    /**
     * Guetteur
     *
     * @return récupère la correspondanceEtape
     */
    public CorrespondanceEtapes getCorresEtap() {
        return corresEtap;
    }

    /**
     * Setteur
     *
     * @param style le style a donné au monde
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Guetteur
     * @return Le nombre de clients
     */
    public int getNbClients() {
        return nbClients;
    }

    /**
     * Setteur
     * @param nbClients Le nombre de clients
     */
    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }

    /**
     * Setteur
     * @param loi Le nom de la loi de la simulation
     */
    public void setLoi(String loi) {
        this.loi = loi;
    }

    /**
     * Getteur
     * @return Le nom de la loi de probabilité
     */
    public String getLoi() {
        return loi;
    }

    /**
     * Fonction qui supprime les arcs et étapes selectionnés
     */
    public void effacerSelection() {
        this.arcSelectionnes.clear();
        this.etapeSelectionnees.clear();

        this.notifierObservateurs();
    }

    /**
     * Fonction permettant de déplacer une étape
     *
     * @param idEtape L'identifiant de l'étape
     * @param x       La nouvelle coordonnée x de l'étape
     * @param y       La nouvelle coordonnée y de l'étape
     */
    public void deplacerEtape(String idEtape, double x, double y) {
        TailleComposants constantes = TailleComposants.getInstance();
        EtapeIG etapeADeplacer = this.listeEtapes.get(idEtape);
        etapeADeplacer.setPosX((int) (x - constantes.getLargeurEtape() / 2));
        etapeADeplacer.setPosY((int) (y - constantes.getHauteurActivite() / 2));

        etapeADeplacer.deplacerPdcEtape();

        this.notifierObservateurs();
    }

    /**
     * Fonction qui retourne le nombre d'étape dans le monde
     *
     * @return Le nombre d'étape dans le monde
     */
    public int nbEtape() {
        return this.listeEtapes.size();
    }

    /**
     * Fonction qui change le theme de chaque composée du monde
     *
     * @param theme
     */
    public void changerStyle(String theme) {
        this.theme = theme;
        switch (theme) {
            case "CLAIR":
                for (EtapeIG etape : this) {
                    if (etape.estUneActivite()) {
                        etape.setCouleurFond("#619bdc");
                        etape.setCouleurBord("#3b69a6");
                    } else if (etape.estUnGuichet()) {
                        etape.setCouleurFond("#39bc58");
                        etape.setCouleurBord("#0c7c24");
                    }
                }
                this.style = "white";
                for (ArcIG arc : this.iteratorArcIG()) {
                    arc.setCouleur("black");
                }
                break;
            case "FONCE":
                for (EtapeIG etape : this) {
                    if (etape.estUneActivite()) {
                        etape.setCouleurFond("#2c6f97");
                        etape.setCouleurBord("#10496b");
                    } else if (etape.estUnGuichet()) {
                        etape.setCouleurFond("#c8254c");
                        etape.setCouleurBord("#9d1f3d");
                    }
                }
                for (ArcIG arc : this.iteratorArcIG()) {
                    arc.setCouleur("green");
                }
                this.style = "#5b5b5b";
                break;
            default:

        }
        this.notifierObservateurs();
    }

    /**
     * Fonction qui vérifie la validité du monde créé
     *
     * @throws MondeException
     */
    private void verifierMondeIG() throws MondeException {
        int cptEntree = 0;
        int cptSortie = 0;

        for(EtapeIG etape : this){
            if(etape.estUneActivite()){
                ActiviteIG act = (ActiviteIG) etape;
                act.setEstRestreinte(false);
            }
        }

        //Le monde est faux si :
        for (EtapeIG etape : this) {
            //Une etape n'a pas de successeur
            if (etape.getSucc().nbEtapes() == 0 && !etape.estUneSortie()) {
                throw new MondeException("Erreur : L'étape "+ etape.getNom() +" ne possède pas de successeurs");
            }
            if (etape.estUneEntree()) {
                cptEntree++;
            }
            if (etape.estUneSortie()) {
                cptSortie++;
            }
            if (etape.estUnGuichet()) {
                //Un guichet possède plus d'un successeur
                if (etape.getSucc().nbEtapes() != 1) {
                    throw new MondeException("Erreur : Un guichet ne peut pas posséder deux successeurs");
                } else {
                    //Deux guichets se succèdent
                    if (etape.getSucc().getEtape(0).estUnGuichet()) {
                        throw new MondeException("Erreur : Deux guichets ne peuvent pas se succeder");

                        //Si le successeur du guichet est une activité  alors elle devient une activité restreinte
                    } else if (etape.getSucc().getEtape(0).estUneActivite()) {
                        ActiviteIG act = (ActiviteIG) etape.getSucc().getEtape(0);
                        act.setEstRestreinte(true);
                    }
                }
            }
        }
        //Il n'y a aucune sortie ou aucune entrée
        if (cptEntree == 0) {
            throw new MondeException("Erreur : Le monde ne possède pas d'entrée");
        }
        if (cptSortie == 0) {
            throw new MondeException("Erreur : Le monde ne possède pas de sortie");
        }
    }

    /**
     * FOnction qui créé un monde
     *
     * @return Monde monde
     */
    private Monde creerMonde() {
        Monde monde = new Monde();
        this.corresEtap = new CorrespondanceEtapes();

        //Pour chaque etapeIG on crée l'étape correspondante
        for (EtapeIG etapeIG : this) {
            Etape etape = null;

            if (etapeIG.estUnGuichet()) {
                GuichetIG guichet = (GuichetIG) etapeIG;
                etape = new Guichet(etapeIG.getNom(), guichet.getNbJetons());
            } else if (etapeIG.estUneActivite()) {
                ActiviteIG act = (ActiviteIG) etapeIG;
                if (etapeIG.estUneActiviteRestreinte()) {
                    etape = new ActiviteRestreinte(act.getNom(), act.getTemps(), act.getEcartTemps());
                } else {
                    etape = new Activite(etapeIG.getNom(), act.getTemps(), act.getEcartTemps());
                }
            }

            if (etape != null) {
                monde.ajouter(etape);
                this.corresEtap.ajouter(etapeIG, etape);
            }
        }

        //Maintenant on ajoute tous les successeurs
        for (EtapeIG etapeIG : this) {
            Etape etape = this.corresEtap.get(etapeIG);
            for (EtapeIG succ : etapeIG.getSucc()) {
                etape.ajouterSuccesseur(this.corresEtap.get(succ));
            }
            if (etapeIG.estUneEntree()) {
                Etape sasEntree = monde.getSasEntree();
                sasEntree.ajouterSuccesseur(this.corresEtap.get(etapeIG));
            }
            if (etapeIG.estUneSortie()) {
                Etape sasSortie = monde.getSasSortie();
                this.corresEtap.get(etapeIG).ajouterSuccesseur(sasSortie);
            }
        }
        return monde;
    }

    /**
     * Fonction qui returne le gestionnaire clients de la simulation
     *
     * @return le gestionnaire clients de la simulation
     * @throws Exception
     */
    public GestionnaireClients getClients() throws Exception {
        Method md = this.c.getMethod("getGestionnaireClients");

        return (GestionnaireClients) md.invoke(play);
    }

    /**
     * Fonction qui permet la simulation du monde créé
     *
     * @throws MondeException
     */
    public void simuler() throws MondeException {
        MondeIG mIG = this;
        try {
            verifierMondeIG();
        } catch (MondeException e) {
            //System.out.println("bug : " + e.getMessage());
            throw new MondeException(e.getMessage());
        }
        //System.out.println(mIG);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                    Monde world = creerMonde(); //notre monde
                    world.setLoi(loi);
                    estEnSimulation = true;
                    ClassLoaderPerso clp = new ClassLoaderPerso(world.getClass().getClassLoader());
                    try {
                        c = clp.loadClass("twisk.simulation.Simulation");

                        //Récupération du construsteur
                        Constructor<?> co = c.getConstructor();
                        play = co.newInstance();

                        ((SujetObserve) play).ajouterObservateur(mIG);

                        //Appel des autres fonctions5
                        Method md = c.getMethod("setNbClients", int.class);
                        md.invoke(play, mIG.getNbClients());

                        md = c.getMethod("simuler", Monde.class);
                        md.invoke(play, world);
                        estEnSimulation = world.getEstEnSimulation(); //à la fin de la simulation on regarde dans le modèle si la simulation est toujours en cours
                        notifierObservateurs();
                    } catch (Exception e) {

                    }
                    return null;
                }
        };
        ThreadsManager.getInstance().lancer(task);
    }

    /**
     * Fonction qui dit si le monde est en simulation ou non
     * @return Retourne vrai si le monde est en simulation, faux sinon
     */
    public boolean estEnSimulation() {
        return this.estEnSimulation;
    }

    /**
     * Fonction qui fixe l'état de simulation du monde
     * @param estEnSimulation l'état du monde
     */
    public void setEstEnSimulation(boolean estEnSimulation){
        this.estEnSimulation = estEnSimulation;
    }

    /**
     * Fonction qui appelle à la sauvegarde du monde
     * @throws FileException
     */
    public void enregistrer() throws FileException {
        kjon.sauvegarder();
    }

    /**
     * Fonction qui appelle à l'ouverture du monde
     * @throws FileException
     */
    public void ouvrirFichier() throws FileException {
        kjon.ouvrirFichier();
    }

    /**
     * Fonction qui appelle à 'ouverture du monde donné en paramètre
     * @throws FileException
     */
    public void ouvrirFichier(int numExemple) {
        kjon.ouvrirFichier(numExemple);
    }

    /**
     * Permet d'obtenir la liste d'étape
     * @return
     */
    public HashMap<String, EtapeIG> getListeEtape() {
        return this.listeEtapes;
    }

    public List<ArcIG> getListeArc(){
        return this.listeArc;
    }


    /**
     * Fonction qui nettoie le monde
     */
    public void clear() {
        this.listeArc.clear();
        this.listeEtapes.clear();
    }

    /**
     * Fonction qui itère tous les arcs dans le monde
     * @return iterateur d'arc
     */
    public Iterable<ArcIG> iteratorArcIG(){
        return this.listeArc;
    }

    /**
     * Fonction qui itère toutes les étapes dans le monde
     * @return iterateur d'arc
     */
    @Override
    public Iterator<EtapeIG> iterator() {
        return this.listeEtapes.values().iterator();
    }

    /**
     * Fonction qui retourne le nombre d'étapes sélectionnées
     * @return
     */
    public int getNbEtapesSelect(){
        return this.etapeSelectionnees.size();
    }
    @Override
    public String toString() {
        return "MondeIG{" +
                "listeEtapes=" + listeEtapes +
                ", listeArc=" + listeArc +
                '}';
    }

    @Override
    public void reagir() {
        notifierObservateurs();
    }
}
