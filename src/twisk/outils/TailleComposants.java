package twisk.outils;

public class TailleComposants {
    private static TailleComposants instance = new TailleComposants();

    /**
     * Constructeur
     */
    private TailleComposants(){

    }

    /**
     * Guetteur
     * @return Retourne une instance de la constante
     */
    public static TailleComposants getInstance(){
        return instance;
    }

    /**
     * Guetteur
     * @return Retourne la largeur de la fenêtre
     */
    public int getLargeurFenetre(){
        return 1200;
    }

    /**
     * Guetteur
     * @return Retourne la hauteur de la fenêtre
     */
    public int getHauteurFenetre(){
        return 900;
    }

    /**
     * Guetteur
     * @return Retourne la largeur d'une étape
     */
    public int getLargeurEtape(){
        return 220;
    }

    /**
     * Guetteur
     * @return Retourne la hauteur d'une étape
     */
    public int getHauteurActivite(){
        return 110;
    }

    /**
     * Guetteur
     * @return Retourne la hauteur d'une étape
     */
    public int getHauteurGuichet(){
        return 70;
    }

    /**
     * Guetteur
     * @return Retourne la largeur de la zone client
     */
    public int getLargeurZoneClient(){
        return TailleComposants.getInstance().getLargeurEtape()-20;
    }

    /**
     * Guetteur
     * @return Retourne la hauteur de la zone client
     */
    public int getHauteurZoneClient(){
        return 60;
    }

    /**
     * Guetteur
     * @return Retourne la largeur d'un bouton
     */
    public int getLargeurBoutonAjouter(){return 40;}

    /**
     * Guetteur
     * @return Retourne la taille d'un point de contrôle
     */
    public double getTaillePDC() {
        return 5;
    }

    /**
     * Guetteur
     * @return Retourne la taille du triangle de la flèche
     */
    public double getTailleTriangle(){return 20;}

    /**
     * Guetteur
     * @return Retourne la taille du client
     */
    public double getTailleClient(){return 5;}
}
