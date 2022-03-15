package twisk.monde;

import twisk.outils.FabriqueNumero;
import java.util.Iterator;

public abstract class Etape implements Iterable<Etape>{
    private String nom;
    private GestionnaireSuccesseurs succ;
    private int numero;

    /**
     * Constructeur
     * @param nom nom de l'Etape
     */
    public Etape(String nom){
        this.nom = nom;
        this.nom = this.nom.toUpperCase();
        this.nom = this.nom.replaceAll("\\s", "_");
        this.succ = new GestionnaireSuccesseurs();
        this.numero = FabriqueNumero.getInstance().getNumeroEtape();
    }

    /**
     * Getteur nom
     * @return Retourne le nom de l'Etape
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getteur GestionnaireSuccesseur
     * @return Retourne le GestionnaireSuccesseurs
     */
    public GestionnaireSuccesseurs getSucc() {
        return succ;
    }

    /**
     * Getteur numero
     * @return  Retourne le numéro de l'étape
     */
    public int getNumero (){
        return this.numero;
    }

    /**
     * Fonction qui ajoute un successeur à une étape
     * @param e Etape qui succèdera à l'étape qui appelle la fonction
     */
    public void ajouterSuccesseur(Etape... e){
        this.succ.ajouter(e);
    }

    /**
     * Fonction qui dit si l'étape est une activité ou non
     * @return Retourne vrai si c'est une activité, faux sinon
     */
    public abstract boolean estUneActivite();

    /**
     * Fonction qui dit si l'étape est un guichet ou non
     * @return Retourne vrai si c'est un guichet, faux sinon
     */
    public abstract boolean estUnGuichet();

    /**
     * Fonction qui indique le nombre de successeur que comporte une étape
     * @return Retourne le nombre de successeur d'une étape
     */
    public int nbSuccesseur(){
        return this.succ.nbEtapes();
    }

    /**
     * Fonction qui génère le code C de l'étape à partir de ces attributs
     * @return Retourne le code C de l'étape
     */
    public abstract String toC();

    /**
     * Fonction qui permet d'itérer des étapes
     * @return Iterator<Etape>
     */
    public Iterator<Etape> iterator(){
        return this.succ.iterator();
    }

    /**
     * Fonction toString
     * @return Les détails de l'étape
     */
    @Override
    public String toString() {
        return "Etape{" +
                "nom='" + nom + '\'' +
                ", succ=" + succ +
                '}';
    }

    /**
     * Fonction toDefine qui renvoie la définition des constantes à intégrer au fichier C
     * @return String
     */
    public String toDefine(){
        StringBuilder define = new StringBuilder("#define "+this.nom+" "+this.numero + "\n");
        return define.toString();
    }
}
