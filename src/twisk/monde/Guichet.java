package twisk.monde;

import twisk.outils.FabriqueNumero;

public class Guichet extends Etape {
    private int nbjetons;
    private int num;

    /**
     * Constructeur
     * @param nom nom du guichet
     */
    public Guichet(String nom) {
        super(nom);
        this.nbjetons = 5;
        this.num = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Constructeur
     * @param nom nom du guichet
     * @param nb nombre de jetons
     */
    public Guichet(String nom, int nb){
        super(nom);
        this.nbjetons = nb;
        this.num = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Getteur nombre de jetons
     * @return Retourne le nombre de jetons
     */
    public int getNbjetons(){
        return this.nbjetons;
    }

    /**
     * Getteur numéro sémaphore
     * @return Retourne le numéro sémaphore
     */
    public int getNum() {
        return this.num;
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
     * Fonction qui génère le code C de l'étape à partir de ces attributs
     * @return Retourne le code C de l'étape
     */
    @Override
    public String toC() {
        StringBuilder code = new StringBuilder("P(ids,"+this.getNom()+");\n"); //Fonction début sémaphore

        //On sait que seule une activité restreinte suit un guichet
        ActiviteRestreinte ar = (ActiviteRestreinte) this.getSucc().getEtape(0);

        code.append("transfert("+this.getNom()+","+ar.getNom()+");\n");
        code.append("delai("+ar.getTemps()+","+ar.getEcartTemps()+");\n");

        code.append("V(ids,"+ this.getNom() +");\n"); //Fonction fin sémaphore

        for(Etape e : ar.getSucc()){ //On écrit le code C des successeurs de l'activiteRestreinte
            code.append("transfert("+ar.getNom()+","+e.getNom()+");\n");
            code.append(e.toC());
        }

        return code.toString();
    }

    /**
     * Fonction toString
     * @return Retourne les détails du guichet
     */
    @Override
    public String toString() {
        return "Guichet " + this.getNom() + " : " + this.nbSuccesseur() + " successeur(s) -> " + this.getSucc().toString();
    }

    /**
     * Fonction getNumeroSema
     * @return le numéro sémaphore du Guichet
     */
    public int getNumeroSema() {
        return this.num;
    }
}
