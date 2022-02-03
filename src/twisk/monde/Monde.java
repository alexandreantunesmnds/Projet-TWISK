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
        for(Etape e : etapes){
            e.ajouterSuccesseur(sasSortie);
        }
    }

    public int nbEtapes(){
        return this.gestionEtapes.nbEtapes();
    }

    public int nbGuichets(){
        int cptGuichet = 0;
        for(Etape etape : gestionEtapes){
            if(etape.estUnGuichet()){
                cptGuichet ++;
            }
        }
        return cptGuichet;
    }

    public int nbEntree(){
        return this.sasEntree.nbSuccesseur();
    }

    @Override
    public String toString() {
        return "Monde{" +
                "sasSortie=" + sasSortie +
                ", sasEntree=" + sasEntree +
                ", gestionEtapes=" + gestionEtapes +
                '}';
    }
}
