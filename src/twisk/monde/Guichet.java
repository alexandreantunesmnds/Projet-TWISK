package twisk.monde;

import twisk.outils.FabriqueNumero;

public class Guichet extends Etape {
    private int nbjetons;
    private int num;

    /**
     * Constructeur
     * @param nom nom du guichet
     */
    public Guichet(String nom) {
        super(nom);
        this.nbjetons = 5;
        this.num = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Constructeur
     * @param nom nom du guichet
     * @param nb nombre de jetons
     */
    public Guichet(String nom, int nb){
        super(nom);
        this.nbjetons = nb;
        this.num = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Getteur nombre de jetons
     * @return Retourne le nombre de jetons
     */
    public int getNbjetons(){
        return this.nbjetons;
    }

    /**
     * Getteur numéro sémaphore
     * @return Retourne le numéro sémaphore
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Fonction qui retourne faux si c'est un guichet
     * @return Retourne faux si c'est un guichet
     */
    @Override
    public boolean estUneActivite() {
        return false;
    }


    /**
     * Fonction qui dit si l'étape est une activité restreinte ou non
     * @return Retourne vrai si c'est une activité, faux sinon
     */
    @Override
    public boolean estUneActiviteRestreinte() {
        return false;
    }

    /**
     * Fonction qui retourne vrai si c'est un guichet
     * @return Retourne vrai si c'est un guichet
     */
    @Override
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * Fonction qui génère le code C de l'étape à partir de ces attributs
     * @return Retourne le code C de l'étape
     */
    @Override
    public String toC() {
        StringBuilder code = new StringBuilder("");
        //On sait que seule une activité restreinte suit un guichet
        if(this.getSucc().getEtape(0).estUneActiviteRestreinte()) {
            ActiviteRestreinte ar = (ActiviteRestreinte) this.getSucc().getEtape(0);

            code.append("\tP(ids,SEM_"+this.getNom()+");\n"); //Fonction début sémaphore
            code.append("\ttransfert("+this.getNom()+","+ar.getNom()+");\n");
            code.append("\tdelai("+ar.getTemps()+","+ar.getEcartTemps()+");\n");
            code.append("\tV(ids,SEM_"+ this.getNom() +");\n"); //Fonction fin sémaphore

            //On transfère ensuite aux successeurs de l'activité restreinte
            if(ar.getSucc().nbEtapes()>1) {
                code.append("\tint nb = (int)((rand()/(float)RAND_MAX*" + ar.getSucc().nbEtapes() + "));\n");
                code.append("\tswitch(nb){\n");
            }
            int cpt = 0;
            for(Etape e : ar.getSucc()){ //On écrit le code C des successeurs de l'activiteRestreinte
                if(ar.getSucc().nbEtapes()>1) {
                    code.append("\tcase " + cpt + ":{ //vers "+ar.getSucc().getEtape(cpt).getNom()+"\n");
                }
                code.append("\ttransfert("+ar.getNom()+","+e.getNom()+");\n");
                code.append(e.toC());
                if(ar.getSucc().nbEtapes()>1) {
                    code.append("\tbreak;\n\t}\n");
                }
                cpt++;
            }
            if(ar.getSucc().nbEtapes()>1) {
                code.append("\t} //Guichet("+this.getNom()+")\n");
            }
        }
        return code.toString();
    }

    /**
     * Fonction toString
     * @return Retourne les détails du guichet
     */
    @Override
    public String toString() {
        return "Guichet " + this.getNom() + "(" + numero + "){" +" : " + this.nbSuccesseur() + " successeur(s) -> " + this.getSucc().toString();
    }

    /**
     * Fonction getNumeroSema
     * @return le numéro sémaphore du Guichet
     */
    public int getNumeroSema() {
        return this.num;
    }
}
