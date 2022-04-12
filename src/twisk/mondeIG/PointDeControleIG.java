package twisk.mondeIG;

public class PointDeControleIG {
    private int posX;
    private int posY;
    private String id;
    private EtapeIG etapeLiee;

    /**
     * Constructeur
     * @param posX coordonnée du point
     * @param posY coordonnée du point
     * @param id identifiant du point
     * @param etapeLiee étape à laquelle est rattaché le point
     */
    public PointDeControleIG(int posX, int posY, String id, EtapeIG etapeLiee) {
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.etapeLiee = etapeLiee;
    }

    /**
     * Getteur
     * @return L'identifiant du point de contrôle
     */
    public String getId() {
        return id;
    }

    /**
     * Getteur
     * @return L'étape liée au point de contrôle
     */
    public EtapeIG getEtapeLiee() {
        return etapeLiee;
    }

    /**
     * Getteur
     * @return La coordonnée X du point de contrôle
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Getteur
     * @return La coordonnée Y du point de contrôle
     */
    public int getPosY() {
        return posY;
    }


    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public String toString() {
        return "PointDeControleIG{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", id='" + id + '\'' +
                ", etapeLiee=" + etapeLiee +
                '}';
    }
}
