package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Etape {
    private String nom;
    private GestionnaireSuccesseurs succ;

    public Etape(String nom){
        this.nom = nom;
    }

    public void ajouterSuccesseur(Etape... e){
        this.succ.ajouter(e);
    }
    public abstract boolean estuneActivite();
    public abstract boolean estUnGuichet();
    public Iterator<Etape> iterator(){
        return this.succ.iterator();
    }

}
