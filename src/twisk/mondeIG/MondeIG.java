package twisk.mondeIG;

import twisk.exceptions.ArcException;
import twisk.exceptions.MondeException;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;
import twisk.vues.Observateur;
import twisk.vues.SujetObserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG>{
    private HashMap<String,EtapeIG> listeEtapes; //La clé est l'identifiant de l'étape
    private List<ArcIG> listeArc;
    private PointDeControleIG pointSelectionne;
    private List<EtapeIG> etapeSelectionnees;
    private List<ArcIG> arcSelectionnes;
    private String style;
    private String theme;

    /**
     * Constructeur
     */
    public MondeIG(){
        this.listeEtapes = new HashMap<>();
        this.listeObservateur = new ArrayList<Observateur>();
        this.listeArc = new ArrayList<ArcIG>();
        this.arcSelectionnes = new ArrayList<ArcIG>();
        this.etapeSelectionnees = new ArrayList<EtapeIG>();
        this.theme = "CLAIR";
        this.style = "white";
        this.ajouter("Activité");
    }

    /**
     * Fonction qui ajoute une étape
     * @param type le type de l'étape à ajouter
     */
    public void ajouter(String type){
        EtapeIG nouvelleEtape;
        String idf = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        TailleComposants constantes = TailleComposants.getInstance();
        switch (type){
            case "Activité":
                nouvelleEtape = new ActiviteIG("Etape "+ idf,idf, constantes.getLargeurEtape(), constantes.getHauteurActivite());
                listeEtapes.put(nouvelleEtape.getIdentifiant(),nouvelleEtape);
                break;
            case "Guichet":
                nouvelleEtape = new GuichetIG("Guichet "+idf,idf,constantes.getLargeurEtape(), constantes.getHauteurGuichet(),10);
                listeEtapes.put(nouvelleEtape.getIdentifiant(),nouvelleEtape);
            default:
                nouvelleEtape = new ActiviteIG("Etape "+ idf,idf,50,30);
                break;
        }

        this.changerStyle(this.theme); //On met le theme du monde

        this.notifierObservateurs();

        //System.out.println("Ajout de l'étape : "+ nouvelleEtape.toString());
    }

    /**
     * Fonction qui ajoute un Arc
     * @param pdc1 Le premier point de contrôle
     * @param pdc2 Le deuxième point de contrôle
     */
    public void ajouterArc(PointDeControleIG pdc1, PointDeControleIG pdc2) throws ArcException {
        if(verifierArc(pdc1,pdc2)) {
            this.listeArc.add(new ArcIG(pdc1, pdc2));
            this.changerStyle(this.theme); //On met le theme du monde
            this.notifierObservateurs();
        }
    }

    /**
     * Fonction qui indique au monde quel point a été selectionné
     * @param pointSelectionne
     */
    public void selectionnerPoint(PointDeControleIG pointSelectionne) throws ArcException {
        if(this.pointSelectionne == null) {
            this.pointSelectionne = pointSelectionne;
        }else{
            this.ajouterArc(this.pointSelectionne,pointSelectionne);
            this.pointSelectionne = null;
            //System.out.println("test");
        }
    }

    /**
     * Fonction qui vérifie les contraintes liées à la création des arcs
     * @param pdc1 Le premier point de contrôle
     * @param pdc2 Le deuxième point de contrôle
     * @return Retourne vrai si les contraintes sont vérifiées, faux sinon
     */
    public boolean verifierArc(PointDeControleIG pdc1, PointDeControleIG pdc2) throws ArcException {
        boolean rep = true;
        if(pdc1.equals(pdc2)){ //Si c'est le même point
            this.pointSelectionne = null;
            throw new ArcException("Erreur: Vous avez saisi deux fois le même point");
        }
        if (pdc1.getEtapeLiee().equals(pdc2.getEtapeLiee())) { //Si les deux points sont sur la même étape
            this.pointSelectionne = null;
            throw new ArcException("Erreur: Vous avez saisi deux points de la même étape");
        }
        for(ArcIG arcSaisi : this.listeArc) { //Si un des deux points à déjà été saisi
            PointDeControleIG pdcSaisi1 = arcSaisi.getPt1();
            PointDeControleIG pdcSaisi2 = arcSaisi.getPt2();
            if (pdcSaisi1.equals(pdc1) || pdcSaisi1.equals(pdc2)){ //Si le point à déjà été saisi
                this.pointSelectionne = null;
                throw new ArcException("Erreur: Un des points a déjà été saisi");
            }
            if (pdcSaisi2.equals(pdc1) || pdcSaisi2.equals(pdc2)){
                this.pointSelectionne = null;
                throw new ArcException("Erreur: Un des points a déjà été saisi");
            }
        }

        return rep;
    }

    /**
     * Fonction qui indique au monde quelle étape a été selectionnée
     * @param etapeSelectionne etape selectionnée
     */
    public void selectionneEtape(EtapeIG etapeSelectionne){
        if(this.etapeSelectionnees.contains(etapeSelectionne)) {
            this.etapeSelectionnees.remove(etapeSelectionne);
        }else {
            this.etapeSelectionnees.add(etapeSelectionne);
            //System.out.println(this.etapeSelectionne.toString());
        }
        this.notifierObservateurs();
    }

    /**
     * Fonction qui indique au monde quelle étape a été selectionnée
     * @param arcSelectionne arc selectionné
     */
    public void selectionneArc(ArcIG arcSelectionne) {
        if(this.arcSelectionnes.contains(arcSelectionne)) {
            this.arcSelectionnes.remove(arcSelectionne);
        }else {
            this.arcSelectionnes.add(arcSelectionne);
            //System.out.println(this.etapeSelectionne.toString());
        }
        this.notifierObservateurs();
    }

    /**
     * Fonction qui supprime les étapes selectionnées
     */
    public void supprimerEtapesSelectionnees(){
        for(EtapeIG etapeASuppr : this.etapeSelectionnees){
            supprimerArc(etapeASuppr);
            this.listeEtapes.remove(etapeASuppr.getIdentifiant());
        }
        this.etapeSelectionnees.clear();
    }

    /**
     * Fonction qui supprime les arcs liées aux étapes selectionnées
     * @param etapeASuppr Les étapes selectionnées
     */
    public void supprimerArc(EtapeIG etapeASuppr){
        List<ArcIG> listeArcASuprr = new ArrayList();
        for(ArcIG arc : this.iteratorArcIG()){
            PointDeControleIG pdc1 = arc.getPt1();
            PointDeControleIG pdc2 = arc.getPt2();

            if(etapeASuppr != null && (pdc1.getEtapeLiee().equals(etapeASuppr) || pdc2.getEtapeLiee().equals(etapeASuppr))) {
                listeArcASuprr.add(arc);
            }else if(arcSelectionnes.contains(arc)){
                listeArcASuprr.add(arc);
            }
        }
        for (ArcIG arcASuppr : listeArcASuprr){
            this.listeArc.remove(arcASuppr);
        }
    }

    /**
     * Guetteur
     * @return Retourne l'étape selectionnée
     */
    public List<EtapeIG> getEtapeSelectionnees(){
        return this.etapeSelectionnees;
    }

    /**
     * Guetteur
     * @return Retourne les arcs selectionés
     */
    public List<ArcIG> getArcSelectionnes(){return this.arcSelectionnes;};

    /**
     * Guetteur
     * @return récupère le style du monde
     */
    public String getStyle() {
        return style;
    }

    /**
     * Setteuur
     * @param style le style a donné au monde
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Fonction qui supprime les arcs et étapes selectionnés
     */
    public void effacerSelection(){
        this.arcSelectionnes.clear();
        this.etapeSelectionnees.clear();

        this.notifierObservateurs();
    }

    /**
     * Fonction permettant de déplacer une étape
     * @param idEtape L'identifiant de l'étape
     * @param x La nouvelle coordonnée x de l'étape
     * @param y La nouvelle coordonnée y de l'étape
     */
    public void deplacerEtape(String idEtape, double x, double y) {
        TailleComposants constantes = TailleComposants.getInstance();
        EtapeIG etapeADeplacer = this.listeEtapes.get(idEtape);
        etapeADeplacer.setPosX((int) (x-constantes.getLargeurEtape()/2));
        etapeADeplacer.setPosY((int) (y-constantes.getHauteurActivite()/2));

        etapeADeplacer.deplacerPdcEtape();

        this.notifierObservateurs();
    }

    /**
     * Fonction qui retourne le nombre d'étape dans le monde
     * @return Le nombre d'étape dans le monde
     */
    public int nbEtape(){
        return this.listeEtapes.size();
    }

    /**
     * Fonction qui change le theme de chaque composée du monde
     * @param theme
     */
    public void changerStyle(String theme){
        this.theme = theme;
        switch (theme){
            case "CLAIR" :
                for(EtapeIG etape : this){
                    if(etape.estUneActivite()) {
                        etape.setCouleurFond("#619bdc");
                        etape.setCouleurBord("#3b69a6");
                    }else if(etape.estUnGuichet()){
                        etape.setCouleurFond("#39bc58");
                        etape.setCouleurBord("#0c7c24");
                    }
                }
                this.style = "white";
                for(ArcIG arc : this.iteratorArcIG()){
                    arc.setCouleur("black");
                }
                break;
            case "FONCE" :
                for(EtapeIG etape : this){
                    if(etape.estUneActivite()) {
                        etape.setCouleurFond("#2c6f97");
                        etape.setCouleurBord("#10496b");
                    }else if(etape.estUnGuichet()){
                        etape.setCouleurFond("#c8254c");
                        etape.setCouleurBord("#9d1f3d");
                    }
                }
                for(ArcIG arc : this.iteratorArcIG()){
                    arc.setCouleur("green");
                }
                this.style = "#5b5b5b";
                break;
            default:

        }
        this.notifierObservateurs();
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

    public void simuler() throws MondeException {


    }
}
