package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Etape {
    private String nom;
    private GestionnaireSuccesseurs succ;

    public Etape(String nom){
        this.nom = nom;
        this.succ = new GestionnaireSuccesseurs();
    }

    public void ajouterSuccesseur(Etape... e){
        this.succ.ajouter(e);
    }

    public abstract boolean estUneActivite();

    public abstract boolean estUnGuichet();

    public int nbSuccesseur(){
        return this.succ.nbEtapes();
    }
    
    public Iterator<Etape> iterator(){
        return this.succ.iterator();
    }

}
