package twisk.mondeIG;

public class ArcIG {
    private String couleur;
    private PointDeControleIG pt1;
    private PointDeControleIG pt2;

    /**
     * Constructeur
     * @param pt1 Extrémité 1 de l'arc
     * @param pt2 Extrémité 2 de l'arc
     */
    public ArcIG(PointDeControleIG pt1, PointDeControleIG pt2) {
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.couleur = "black";
    }

    /**
     * Guetteur
     * @return retourne le premier point
     */
    public PointDeControleIG getPt1() {
        return pt1;
    }

    /**
     * Guetteur
     * @return retourne le deuxième point
     */
    public PointDeControleIG getPt2() {
        return pt2;
    }

    /**
     * Guetteur
     * @return retourne la couleur de l'arc
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * setteur
     * @param couleur Fixe la couleur de l'arc
     */
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
