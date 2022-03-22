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
        StringBuilder code = new StringBuilder("entrer("+this.getNom()+");\n");

        for(Etape e : this.getSucc()){
            code.append("\ttransfert("+this.getNom()+","+e.getNom()+");\n");
        }

        for(Etape e : this.getSucc()){ //On écrit le code C des successeurs
            code.append(e.toC());
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
