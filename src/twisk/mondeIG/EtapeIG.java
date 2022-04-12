package twisk.mondeIG;

import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Random;

public abstract class EtapeIG implements Iterable<PointDeControleIG>{
    protected ArrayList<PointDeControleIG> pointsDeControle;
    protected String nom;
    protected String identifiant;
    protected String couleur;
    protected int posX;
    protected int posY;
    protected int largeur;
    protected int hauteur;
    protected boolean entree;
    protected boolean sortie;

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
        this.couleur = "#619bdc"; //Couleur bleu de base
        this.posX = rand.nextInt(constantes.getLargeurFenetre()-constantes.getLargeurEtape());
        this.posY = rand.nextInt(constantes.getHauteurFenetre()-constantes.getHauteurEtape()-constantes.getLargeurBoutonAjouter());
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
    public void setCouleur(String couleur) {
        this.couleur = couleur;
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
    public abstract boolean estUneActivite();

    /**
     * Fonction qui indique si l'étape est une entrée ou non
     * @return vrai si c'est une entrée
     */
    public boolean estUneEntree(){
        boolean rep = false;
        if(this.entree){
            rep = true;
        }
        return rep;
    }

    /**
     * Fonction qui indique si l'étape est une sortie ou non
     * @return vrai si c'est une sortie
     */
    public boolean estUneSortie(){
        boolean rep = false;
        if(this.sortie){
            rep = true;
        }
        return rep;
    }

    /**
     * Fonction qui déplace les pdc d'une étape
     */
    public abstract void deplacerPdcEtape();

    @Override
    public String toString() {
        return "EtapeIG{" +
                "nom='" + nom + '\'' +
                ", identifiant='" + identifiant + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", largeur=" + largeur +
                ", hauteur=" + hauteur +
                '}';
    }
}
