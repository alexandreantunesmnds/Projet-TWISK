package twisk.monde;

public class Activite extends Etape {
    private int temps;
    private int ecartTemps;

    /**
     * Constructeur
     * @param nom Le nom de l'Acitivité
     */
    public Activite(String nom) {
        super(nom);
        this.temps = 5;
        this.ecartTemps = 2;
    }

    /**
     * Constructeur
     * @param nom Le nom de l'Activité
     * @param t Le temps de l'Activité
     * @param e L'écart temps de l'Acitivité
     */
    public Activite(String nom, int t, int e){
        super(nom);
        this.temps = t;
        this.ecartTemps = e ;
    }

    /**
     * Getteur temps
     * @return Retourne le temps de l'Activité
     */
    public int getTemps() {
        return temps;
    }

    /**
     * Getteur ecart-temps
     * @return Retourne l'écart temps de l'Activité
     */
    public int getEcartTemps() {
        return ecartTemps;
    }

    /**
     * Fonction qui retourne vrai si c'est une Activité
     * @return retourne vrai
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }

    /**
     * Fonction qui dit si l'étape est une activité restreinte ou non
     * @return Retourne vrai si c'est une activité, faux sinon
     */
    @Override
    public boolean estUneActiviteRestreinte() {
        return false;
    }

    /**
     * Fonction qui retourne faux si c'est une Activité
     * @return retourne faux
     */
    @Override
    public boolean estUnGuichet() {
        return false;
    }

    /**
     * Fonction qui génère le code C de l'étape à partir de ces attributs
     * @return Retourne le code C de l'étape
     */
    @Override
    public String toC() {
        StringBuilder code = new StringBuilder("\tdelai("+this.temps+","+ this.ecartTemps +");" + "\n");

        if(this.getSucc().nbEtapes()>1) {
            code.append("\tint nb = (int)((rand()/(float)RAND_MAX*" + this.getSucc().nbEtapes() + ");\n");
            code.append("\tswitch(nb):\n");
        }
        int cpt = 0;
        for(Etape e : this.getSucc()){ //On écrit le code C des successeurs
            if(this.getSucc().nbEtapes()>1) {
                code.append("\tcase " + cpt + ":{ //vers "+e.getNom()+"\n");
            }
            code.append("\ttransfert("+this.getNom()+","+e.getNom()+");\n");
            code.append("\t"+e.toC());
            if(this.getSucc().nbEtapes()>1) {
                code.append("\tbreak;\n\t}\n");
            }
            cpt++;
        }

        return code.toString();
    }

    /**
     * Fonction toString
     * @return Retourne les détails d'une Activité
     */
    @Override
    public String toString() {
        return "Activité " + this.getNom()  + "(" + numero + "){" + " : " + this.nbSuccesseur() + " successeur(s) -> " + this.getSucc().toString();
    }
}
