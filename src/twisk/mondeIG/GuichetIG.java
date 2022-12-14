package twisk.mondeIG;

import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Iterator;

public class GuichetIG extends EtapeIG{
    private int nbJetons;
    private boolean sensGaucheDroite; //Sens de circulation

    /**
     * Constructeur
     * @param nom Nom de l'activité
     * @param idf Identifiant de l'activité
     * @param larg Largeur de la boite
     * @param haut Hauteur de la boite
     * @param nbJetons Le nombre de jetons
     */
    public GuichetIG(String nom, String idf, int larg, int haut, int nbJetons) {
        super(nom, idf, larg, haut);
        this.nbJetons = nbJetons;

        this.pointsDeControle = new ArrayList<PointDeControleIG>(2);

        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()- (int) TailleComposants.getInstance().getTaillePDC()*2,this.getPosY()+haut/2,idf+" pdc 3",this)); //GAUCHE
        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()+larg,this.getPosY()+haut/2,idf+" pdc 4",this)); //DROITE

        this.couleur = "#39bc58"; //Couleur verte de base
        this.sensGaucheDroite = true;
    }


    public GuichetIG(String nom, String identifiant, int posX, int posY, int largeur, int hauteur, boolean entree, boolean sortie, int nbJetons, boolean sensGaucheDroite) {
        super(nom, identifiant, posX, posY, largeur, hauteur, entree, sortie);
        this.nbJetons = nbJetons;

        this.pointsDeControle = new ArrayList<PointDeControleIG>(2);

        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()- (int) TailleComposants.getInstance().getTaillePDC()*2,this.getPosY()+hauteur/2,identifiant+" pdc 3",this)); //GAUCHE
        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()+largeur,this.getPosY()+hauteur/2,identifiant+" pdc 4",this)); //DROITE

        this.couleur = "#39bc58"; //Couleur verte de base
        this.couleurBord = "#0c7c24";
        this.sensGaucheDroite = sensGaucheDroite;
    }

    /**
     * Getteur
     * @return Le nombre de jetons
     */
    public int getNbJetons() {
        return nbJetons;
    }

    /**
     * Setteur
     * @param nbJetons Le nombre de jetons
     */
    public void setNbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * Getteur
     * @return vrai si le sens de circulation est gauche-droite, faux sinon
     */
    public boolean isSensGaucheDroite() {
        return sensGaucheDroite;
    }

    /**
     * Setteur
     * @param sensGaucheDroite le sens de circulation
     */
    public void setSensGaucheDroite(boolean sensGaucheDroite) {
        this.sensGaucheDroite = sensGaucheDroite;
    }

    /**
     * Fonction qui vérifie quel point a été relier et en détermine le sens de circulation
     * @param pointSelectionne
     */
    public void verifierSensCirculation(PointDeControleIG pointSelectionne) {
        if(pointSelectionne.getPosX() == this.getPosX()- (int) TailleComposants.getInstance().getTaillePDC()*2){
            this.sensGaucheDroite = true;
        }else{
            this.sensGaucheDroite = false;
        }
    }

    /**
     * Fonction qui déplace les pdc d'une étape
     */
    @Override
    public void deplacerPdcEtape() {
        PointDeControleIG pdc1 = this.pointsDeControle.get(0);
        PointDeControleIG pdc2 = this.pointsDeControle.get(1);

        //Gauche
        pdc1.setPosX((int) (this.getPosX()-TailleComposants.getInstance().getTaillePDC()*2));
        pdc1.setPosY(this.getPosY()+hauteur/2);

        //Droite
        pdc2.setPosX(this.getPosX()+largeur);
        pdc2.setPosY(this.getPosY()+hauteur/2);
    }

    @Override
    public Iterator<PointDeControleIG> iterator() {
        return this.pointsDeControle.iterator();
    }

    @Override
    public String toString() {
        return "GuichetIG{" +
                "nom='" + nom + '\'' +
                ", identifiant='" + identifiant + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", largeur=" + largeur +
                ", hauteur=" + hauteur +
                ", entree=" + entree +
                ", sortie=" + sortie +
                ", succ=" + succ +
                ", nbJetons=" + nbJetons +
                ", sensGaucheDroite=" + sensGaucheDroite +
                '}';
    }
}
