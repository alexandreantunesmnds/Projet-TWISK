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
        StringBuilder code = new StringBuilder("entrer("+this.getNom()+");\n\tdelaiEntree();\n");

        if(this.getSucc().nbEtapes()>1) {
            code.append("\tint nb = (int)((rand()/(float)RAND_MAX*" + this.getSucc().nbEtapes() + "));\n");
            code.append("\tswitch(nb){\n");
        }
        int cpt = 0;
        for(Etape e : this.getSucc()){ //On écrit le code C des successeurs
            if(this.getSucc().nbEtapes()>1) {
                code.append("\tcase " + cpt + ":{ //vers "+e.getNom()+"\n");
            }
            code.append("\ttransfert("+this.getNom()+","+e.getNom()+");\n");
            code.append(e.toC());
            if(this.getSucc().nbEtapes()>1) {
                code.append("break;\n}\n");
            }
            cpt++;
        }
        if(this.getSucc().nbEtapes()>1) {
            code.append("\t} //SasEntree("+this.getNom()+")\n");
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
