package twisk.monde;

import twisk.outils.FabriqueNumero;

public class Guichet extends Etape {
    private int nbjetons;
    private int numero;

    /**
     * Constructeur
     * @param nom nom du guichet
     */
    public Guichet(String nom) {
        super(nom);
        this.nbjetons = 5;
        this.numero = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Constructeur
     * @param nom nom du guichet
     * @param nb nombre de jetons
     */
    public Guichet(String nom, int nb){
        super(nom);
        this.nbjetons = nb;
        this.numero = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Getteur nombre de jetons
     * @return Retourne le nombre de jetons
     */
    public int getNbjetons(){
        return this.nbjetons;
    }

    /**
     * Fonction qui retourne faux si c'est un guichet
     * @return Retourne faux si c'est un guichet
     */
    @Override
    public boolean estUneActivite() {
        return false;
    }

    /**
     * Fonction qui retourne vrai si c'est un guichet
     * @return Retourne vrai si c'est un guichet
     */
    @Override
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * Fonction toString
     * @return Retourne les détails du guichet
     */
    @Override
    public String toString() {
        return this.getNom() + " : " + this.nbSuccesseur() + " successeurs -> " + this.getSucc().toString();
    }

    public int getNumero() {
        return this.numero;
    }
}
