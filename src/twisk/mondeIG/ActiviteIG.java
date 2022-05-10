package twisk.mondeIG;

import twisk.exceptions.TimeException;
import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Iterator;

public class ActiviteIG extends EtapeIG{
    private int temps;
    private int ecartTemps;
    private boolean estRestreinte;

    /**
     * Constructeur
     * @param nom Nom de l'activité
     * @param idf Identifiant de l'activité
     * @param larg Largeur de la boite
     * @param haut Hauteur de la boite
     */
    public ActiviteIG(String nom, String idf, int larg, int haut) {
        super(nom, idf, larg, haut);

        this.temps = 4;
        this.ecartTemps = 2;

        this.pointsDeControle = new ArrayList<PointDeControleIG>(4);

        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()+larg/2,this.getPosY()- (int)TailleComposants.getInstance().getTaillePDC()*2,idf+" pdc 1",this)); //HAUT
        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()+larg/2,this.getPosY()+haut,idf+" pdc 2",this)); //BAS
        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()- (int)TailleComposants.getInstance().getTaillePDC()*2,this.getPosY()+haut/2,idf+" pdc 3",this)); //GAUCHE
        this.pointsDeControle.add(new PointDeControleIG(this.getPosX()+larg,this.getPosY()+haut/2,idf+" pdc 4",this)); //DROITE

        this.couleur = "#619bdc"; //Couleur bleu de base

        this.estRestreinte = false;
    }

    /**
     * Guetteur
     * @return Le temps
     */
    public int getTemps() {
        return temps;
    }

    /**
     * Guetteur
     * @return L'écart-temps
     */
    public int getEcartTemps() {
        return ecartTemps;
    }

    /**
     * Setteur
     * @param temps Nouveau temps de l'activité
     */
    public void setTemps(String temps) throws TimeException {
        int val = 0;
        try{
            val = Integer.parseInt(temps);
        }catch (NumberFormatException e){
            throw new TimeException("Erreur: la valeur saisie pour le temps n'est pas un nombre.");
        }
        if(val > 0) {
            this.temps = val;
        }else{
            throw new TimeException("Erreur: la valeur saisie ne peut être nulle ou négative.");
        }
    }

    /**
     * Setteur
     * @param ecartTemps Nouvel écart-temps de l'activité
     */
    public void setEcartTemps(String ecartTemps) throws TimeException {
        int val = 0;
        try{
            val = Integer.parseInt(ecartTemps);
        }catch (NumberFormatException e){
            throw new TimeException("Erreur: la valeur saisie pour le temps n'est pas un nombre.");
        }
        if(val > 0) {
            this.ecartTemps = val;
        }else{
            throw new TimeException("Erreur: la valeur saisie ne peut être nulle ou négative.");
        }
    }

    /**
     * Fonction qui indique si l'étape doit être une activité restreinte ou non
     */
    public void setEstRestreinte(Boolean statut) {
        this.estRestreinte =statut;
    }

    /**
     * Fonction qui déplace les pdc d'une étape
     */
    @Override
    public void deplacerPdcEtape() {
        PointDeControleIG pdc1 = this.pointsDeControle.get(0);
        PointDeControleIG pdc2 = this.pointsDeControle.get(1);
        PointDeControleIG pdc3 = this.pointsDeControle.get(2);
        PointDeControleIG pdc4 = this.pointsDeControle.get(3);

        //Haut
        pdc1.setPosX(this.getPosX()+largeur/2);
        pdc1.setPosY((int) (this.getPosY()-TailleComposants.getInstance().getTaillePDC()*2));

        //Bas
        pdc2.setPosX(this.getPosX()+largeur/2);
        pdc2.setPosY(this.getPosY()+hauteur);

        //Gauche
        pdc3.setPosX((int) (this.getPosX()-TailleComposants.getInstance().getTaillePDC()*2));
        pdc3.setPosY(this.getPosY()+hauteur/2);

        //Droite
        pdc4.setPosX(this.getPosX()+largeur);
        pdc4.setPosY(this.getPosY()+hauteur/2);
    }

    @Override
    public boolean estUneActivite() {
        return true;
    }

    @Override
    public boolean estUneActiviteRestreinte() {
        return this.estRestreinte;
    }

    /**
     * Iterator
     * @return
     */
    @Override
    public Iterator<PointDeControleIG> iterator() {
        return this.pointsDeControle.iterator();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
