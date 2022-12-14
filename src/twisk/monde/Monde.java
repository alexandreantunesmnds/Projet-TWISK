package twisk.monde;

import twisk.outils.FabriqueIdentifiant;
import twisk.outils.FabriqueNumero;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    private SasSortie sasSortie;
    private SasEntree sasEntree;
    private GestionnaireEtapes gestionEtapes;

    private boolean estEnSimulation;
    private String loi;

    /**
     * Constructeur
     */
    public Monde(){
        FabriqueNumero.getInstance().reset();
        this.sasEntree = new SasEntree();
        this.sasSortie = new SasSortie();
        this.gestionEtapes = new GestionnaireEtapes();
        this.gestionEtapes.ajouter(this.sasEntree, this.sasSortie);
        this.estEnSimulation = false;
        this.loi = "uniforme";
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
     * Fonction qui retourne le sasSortie
     * @return sasSortie
     */
    public SasSortie getSasSortie() {
        return sasSortie;
    }

    /**
     * Fonction qui retourne le sasEntrée
     * @return sasEntree
     */
    public SasEntree getSasEntree() {
        return sasEntree;
    }

    /**
     * Setteur
     * @param loi la loi
     */
    public void setLoi(String loi) {
        this.loi = loi;
    }

    private String ecrireLoi(){
        String loi = null;
        switch (this.loi){
            case "uniforme":
                loi = "void delaiEntree() {\n" +
                        "    int bi, bs ;\n" +
                        "    int n, nbSec ;\n" +
                        "    bi = 10 - 4 ;\n" +
                        "    if (bi < 0) bi = 0 ;\n" +
                        "    bs = 10 + 4 ;\n" +
                        "    n = bs - bi ;\n" +
                        "    nbSec = (rand()/ (float)RAND_MAX) * n ;\n" +
                        "    nbSec += bi ;\n" +
                        "    usleep(nbSec*1000000);\n" +
                        "}\n\n";
                break;
            case "gaussienne":
                loi = "void delaiEntree(){\n" +
                        "    double u1 = rand()/ (float)RAND_MAX;\n" +
                        "    double u2 = rand()/ (float)RAND_MAX;\n" +
                        "    double x = sqrt((-2)*log(u1))*cos(2*M_PI*u2)*4+10;\n" +
                        "    usleep(x*1000000);\n" +
                        "}\n\n";
                break;
            case "poisson":
                loi = "void delaiEntree(){\n" +
                        "    double u = rand()/ (float)RAND_MAX;\n" +
                        "    double x = -log(u)/0.1;\n" +
                        "    usleep(x*1000000);\n" +
                        "}\n\n";
                break;
        }
        return loi;
    }

    /**
     * Fonction toC
     * @return le code C demandé pour la Simulation
     */
    public String toC(){
        StringBuilder code = new StringBuilder("#include <stdio.h>\n" +
                "#include <stdlib.h>\n" +
                "#include <math.h>\n"+
                "#include \"def.h\"\n" +
                "#include <time.h>\n"+
                "\n");
        for (Etape e : this){
            if (e.estUnGuichet()){
                Guichet guichet = (Guichet) e;
                code.append("#define SEM_"+guichet.getNom()+" "+ guichet.getNumeroSema()+ "\n");
            }
        }
        for (Etape e : this){
            code.append(e.toDefine());
        }

        code.append("\n"+this.ecrireLoi());

        code.append("\nvoid simulation(int ids){\n" +
                //"\tsrand(ids);\n"+
                "\tsrand(getpid());\n"+
                "\t"+this.sasEntree.toC()+"}");
        return code.toString();
    }
    public void setEstEnSimulation(boolean estEnSimulation){
        this.estEnSimulation = estEnSimulation;
    }

    public boolean getEstEnSimulation(){
        return this.estEnSimulation;
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
        return "Liste des " + this.nbEtapes() + " etapes : \n" +
                gestionEtapes.toString();
    }
}
