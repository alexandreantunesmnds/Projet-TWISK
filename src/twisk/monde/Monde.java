package twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
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
        this.gestionEtapes.ajouter(this.sasEntree, this.sasSortie);
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

    /**
     * Retourne l'étape à l'indice donnée
     * @param indice l'indice donnée
     * @return
     */
    public Etape getEtape(int indice) {
        return this.gestionEtapes.getEtape(indice);
    }

    /**
     * Fonction toC
     * @return le code C demandé pour la Simulation
     */
    public String toC(){
        StringBuilder code = new StringBuilder("#include <stdio.h>\n" +
                "#include <stdlib.h>\n" +
                "#include \"def.h\"\n" +
                "#include <time.h>\n"+
                "\n");
        for (Etape e : this){
            if (e.estUnGuichet()){
                Guichet guichet = (Guichet) e;
                String nom_guichet = guichet.getNom();
                nom_guichet = nom_guichet.toUpperCase();
                nom_guichet = nom_guichet.replaceAll("\\s", "_");
                code.append("#define RAND_MAX 1\n");
                code.append("#define SEM_"+nom_guichet+" "+ guichet.getNumeroSema()+ "\n");
            }
        }
        for (Etape e : this){
            code.append(e.toDefine());
        }
        code.append("\nvoid simulation(int ids){\n" +
                "\tsrand(time(NULL));\n"+
                "\t"+this.sasEntree.toC()+"}");
        return code.toString();
    }

    /**
     * Fonction iterator
     * @return un Iterator <Etape> pour rendre les étapes du monde itérables
     */
    @Override
    public Iterator<Etape> iterator() {
        return this.gestionEtapes.iterator();
    }

    /**
     * Fonction toString
     * @return Les informations du monde sur la sortie standard
     */
    @Override
    public String toString() {
        return "Entrées : " + sasEntree.nbSuccesseur() + " successeur(s) - " + sasEntree.getSucc().toString() + "\n" +
                "Sorties : " + sasSortie.nbSuccesseur() + " successeur(s) \n" +
                "Liste des " + this.nbEtapes() + " etapes : \n" +
                gestionEtapes.toString();
    }
}
