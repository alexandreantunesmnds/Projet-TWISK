package twisk.monde;

public class SasSortie extends Activite{
    /**
     * Constructeur
     */
    public SasSortie(){
        super("SasSortie");
    }

    /**
     * Fonction qui génère le code C de l'étape à partir de ces attributs
     * @return Retourne le code C de l'étape
     */
    @Override
    public String toC(){
        return "";
    }

    /**
     * Fonction toString
     * @return Retourne les détails du SasSortie
     */
    @Override
    public String toString() {
        return "SasSortie{}";
    }
}
