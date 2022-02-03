package twisk.monde;

public class Monde {
    private SasSortie sasSortie;
    private SasEntree sasEntree;
    private GestionnaireEtapes gestionEtapes;
    public Monde(){
        this.sasEntree = new SasEntree("Entrée");
        this.sasSortie = new SasSortie("Sortie");
        this.gestionEtapes = new GestionnaireEtapes();
    }
    public void aCommeEntree(Etape... etapes){
    }

}
