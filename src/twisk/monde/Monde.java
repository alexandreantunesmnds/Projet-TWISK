package twisk.monde;

public class Monde {
    private SasSortie sasSortie;
    private SasEntree sasEntree;
    private GestionnaireEtapes gestionEtapes;
    public Monde(){
        this.sasEntree = new SasEntree();
        this.sasSortie = new SasSortie();
        this.gestionEtapes = new GestionnaireEtapes();
    }

    public void aCommeEntree(Etape... etapes){
        this.sasEntree.ajouterSuccesseur(etapes);
    }

    public void aCommeSortie(Etape... etapes){
        this.sasSortie.ajouterSuccesseur(etapes);
    }

    public int nbEntree(){
        return this.sasEntree.nbSuccesseur();
    }

    public int nbSortie(){
        return this.sasSortie.nbSuccesseur();
    }

}
