package twisk.mondeIG;

import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Random;

public abstract class EtapeIG implements Iterable<PointDeControleIG>{
    protected ArrayList<PointDeControleIG> pointsDeControle;
    protected String nom;
    protected String identifiant;
    protected String couleur;
    protected String couleurBord;
    protected int posX;
    protected int posY;
    protected int largeur;
    protected int hauteur;
    protected boolean entree;
    protected boolean sortie;
    protected GestionnaireSuccesseursIG succ;

    /**
     * Constructeur
     * @param nom Nom de l'activité
     * @param idf Identifiant de l'activité
     * @param larg Largeur de la boite
     * @param haut Hauteur de la boite
     */
    public EtapeIG(String nom, String idf, int larg, int haut){
        TailleComposants constantes = TailleComposants.getInstance();
        Random rand = new Random();
        this.nom = nom;
        this.identifiant = idf;
        this.entree = false;
        this.sortie = false;
        this.largeur = larg;
        this.hauteur = haut;
        this.posX = rand.nextInt(constantes.getLargeurFenetre()-constantes.getLargeurEtape());
        this.posY = rand.nextInt(constantes.getHauteurFenetre()-constantes.getHauteurActivite()-constantes.getLargeurBoutonAjouter());
        this.succ = new GestionnaireSuccesseursIG();
    }

    public EtapeIG(String nom, String identifiant, int posX, int posY, int largeur, int hauteur, boolean entree, boolean sortie) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.posX = posX;
        this.posY = posY;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.entree = entree;
        this.sortie = sortie;
        this.succ = new GestionnaireSuccesseursIG();
    }

    /**
     * Getteur
     * @return Nom de l'étape
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getteur
     * @return Identifiant de l'étape
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * Getteur
     * @return Coordonnée X de l'étape
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Getteur
     * @return Coordonnée Y de l'étape
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Getteur
     * @return Largeur de la boîte
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Getteur
     * @return Hauteur de la boîte
     */
    public int getHauteur() {
        return hauteur;
    }


    /**
     * Setteur
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Setteur
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Guetteur
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Guetteur
     * @return La couleur de l'étape
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Setteur
     * @param couleur La couleur de l'étape
     */
    public void setCouleurFond(String couleur) {
        this.couleur = couleur;
    }

    /**
     * Setteur
     * @param couleur la couleur du bord de l'étape
     */
    public void setCouleurBord(String couleur){
        this.couleurBord = couleur;
    }

    /**
     * Guetteur
     * @return La couleur du bord de l'étape
     */
    public String getCouleurBord() {
        return couleurBord;
    }

    /**
     * Setteur
     */
    public void setEntree() {
        if(this.entree){
            this.entree = false;
        }else if(!this.sortie){
            this.entree = true;
        }
    }

    /**
     * Setteur
     */
    public void setSortie() {
        if(this.sortie){
            this.sortie = false;
        }else if(!this.entree){
            this.sortie = true;
        }
    }

    /**
     * Fonction qui dit si l'étape est une activité ou non
     * @return Vrai si c'est une activité
     */
    public boolean estUneActivite(){
        return false;
    }

    /**
     * Fonction qui dit si l'étape est une activité restreinte ou non
     * @return Vrai si c'est une activité restreinte
     */
    public boolean estUneActiviteRestreinte(){
        return false;
    }

    /**
     * Fonction qui dit si l'étape est unn guichet ou non
     * @return Vrai si c'est un guichet
     */
    public boolean estUnGuichet(){
        return false;
    }

    /**
     * Fonction qui indique si l'étape est une entrée ou non
     * @return vrai si c'est une entrée
     */
    public boolean estUneEntree(){
        return this.entree;
    }

    /**
     * Fonction qui indique si l'étape est une sortie ou non
     * @return vrai si c'est une sortie
     */
    public boolean estUneSortie(){
        return this.sortie;
    }

    /**
     * Fonction qui retourne la liste des successeurs de l'étape
     * @return La liste des successeurs de l'étape
     */
    public GestionnaireSuccesseursIG getSucc(){
        return this.succ;
    }

    /**
     * Fonction qui ajoute un successeur à l'étape passée en paramètre
     * @param e
     */
    public void ajouterSuccesseur(EtapeIG ... e){
        this.succ.ajouter(e);
    }
    /**
     * Fonction qui retire un successeur à l'étape passée en paramètre
     * @param e
     */
    public void retirerSuccesseur(EtapeIG e){
        this.succ.retirer(e);
    }

    /**
     * Fonction qui déplace les pdc d'une étape
     */
    public abstract void deplacerPdcEtape();

    /**
     * Fonction qui nous informe si une étape est accessible depuis une autre étape
     * @param etape L'étape dont on veut partir
     * @return Vrai si il y a une boucle, faux sinon
     */
    public boolean estAccessibleDepuis(EtapeIG etape){
        Boolean rep = false;

        if(etape.getSucc().nbEtapes() > 0){
            for(EtapeIG etapeSucc : etape.getSucc()){
                if(etapeSucc.equals(this)){
                    rep = true;
                }else{
                    rep = estAccessibleDepuis(etapeSucc);
                }
            }
        }

        return rep;
    }

    @Override
    public String toString() {
        return "EtapeIG{" +
                "nom='" + nom + '\'' +
                ", identifiant='" + identifiant + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", largeur=" + largeur +
                ", hauteur=" + hauteur +
                ", entree=" + entree +
                ", sortie=" + sortie +
                ", succ=" + succ +
                '}';
    }
}
