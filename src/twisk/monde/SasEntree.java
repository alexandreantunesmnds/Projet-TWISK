package twisk.monde;

public class SasEntree extends Activite {
    /**
     * Constructeur
     */
    public SasEntree(){
        super("ENTREE");
    }

    /**
     * Fonction qui génère le code C de l'étape à partir de ces attributs
     * @return Retourne le code C de l'étape
     */
    @Override
    public String toC(){
        StringBuilder code = new StringBuilder("\tentrer("+this.getNom()+");\n\tdelai(6,3);\n");

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
     * @return Retourne les détails du SasEntree
     */
    @Override
    public String toString() {
        return this.getNom() + " : " + this.nbSuccesseur() + " successeur(s) -> " + this.getSucc().toString();
    }
}
