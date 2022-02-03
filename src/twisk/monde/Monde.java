package twisk.monde;

public class Monde {
    private SasSortie sasSortie;
    private SasEntree sasEntree;
    private GestionnaireEtapes gestionEtapes;

    /**
     * Constructeur
     */
    public Monde(){
        this.sasEntree = new SasEntree();
        this.sasSortie = new SasSortie();
        this.gestionEtapes = new GestionnaireEtapes();
    }

    /**
     * Fonction qui ajoute les entrées du monde
     * @param etapes les étapes à ajouter en tant qu'entrée
     */
    public void aCommeEntree(Etape... etapes){
        this.sasEntree.ajouterSuccesseur(etapes);
    }

    /**
     * Fonction qui ajoute les sorties du monde
     * @param etapes les étapes à ajouter en tant que sortie
     */
    public void aCommeSortie(Etape... etapes){
        for(Etape e : etapes){
            e.ajouterSuccesseur(sasSortie);
        }
    }

    /**
     * Fonction qui ajoute les étapes dans le monde
     * @param etapes les étapes à ajouter dans le monde
     */
    public void ajouter(Etape... etapes){
        gestionEtapes.ajouter(etapes);
    }

    /**
     * Fonction qui indique le nombre d'étape dans le monde
     * @return Le nombre d'étape dans le monde
     */
    public int nbEtapes(){
        return this.gestionEtapes.nbEtapes();
    }

    /**
     * Fonction qui indique le nombre de guichet dans le monde
     * @return Le nombre de guichet dans le monde
     */
    public int nbGuichets(){
        int cptGuichet = 0;
        for(Etape etape : gestionEtapes){
            if(etape.estUnGuichet()){
                cptGuichet ++;
            }
        }
        return cptGuichet;
    }

    /**
     * Fonction qui indique le nombre d'entrée dans le monde
     * @return Le nombre d'entrée dans le monde
     */
    public int nbEntree(){
        return this.sasEntree.nbSuccesseur();
    }

    @Override
    public String toString() {
        return "Entrées : " + sasEntree.nbSuccesseur() + " successeur(s) - " + sasEntree.getSucc().toString() + "\n" +
                "Sorties : " + sasSortie.nbSuccesseur() + " successeur(s) \n" +
                "Liste des " + this.nbEtapes() + " etapes : \n" +
                gestionEtapes.toString();
    }
}