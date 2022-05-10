package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    int numeroClient;
    int rang;
    String couleur;
    Etape etape;

    /**
     * Construteur
     * @param numero le numero du client
     */
    public Client(int numero) {
        this.numeroClient = numero;
        this.setCouleur();
    }

    /**
     * Fonction qui met à jour les données du client
     * @param etape l'étape où se situe le client
     * @param rang le rang où se situe le client dans la file d'attente
     */
    public void allerA(Etape etape, int rang){
        this.etape = etape;
        this.rang = rang;
    }

    /**
     * Fontion qui fixe la couleur au client de manière aléatoire
     */
    private void setCouleur(){
        int nombreAleatoire = (int) (Math.random() * 9);
        switch (nombreAleatoire){
            case 0: //rouge
                this.couleur = "#e51d1d";
                break;
            case 1: //orange
                this.couleur = "#ed9c27";
                break;
            case 2: //jaune
                this.couleur = "#f1e633";
                break;
            case 3: //vert clair
                this.couleur = "#98d016";
                break;
            case 4: //vert foncé
                this.couleur = "#32d016";
                break;
            case 5: //bleu clair
                this.couleur = "#24e7c7";
                break;
            case 6: //bleu foncé
                this.couleur = "#2762c9";
                break;
            case 7: //violet
                this.couleur = "#7f27c9";
                break;
            case 8: //rose
                this.couleur = "#f23eed";
                break;
        }
    }

    /**
     * Guetteur
     * @return Retourne la couleur associé au client
     */
    public String getCouleur(){
        return this.couleur;
    }

    /**
     * Guetteur
     * @return Retourne le numéro du client
     */
    public int getNumeroClient() {
        return numeroClient;
    }

    /**
     * Setteur
     * @param numeroClient Le numéro du client
     */
    public void setNumeroClient(int numeroClient) {
        this.numeroClient = numeroClient;
    }

    /**
     * Guetteur
     * @return Retourne le rang du client dans la file d'attante
     */
    public int getRang() {
        return rang;
    }

    /**
     * Setteur
     * @param rang Le rang du client dans la file d'attente
     */
    public void setRang(int rang) {
        this.rang = rang;
    }

    /**
     * Guetteur
     * @return retourne l'étape où se situe le client
     */
    public Etape getEtape() {
        return etape;
    }

    /**
     * Setteur
     * @param etape L'étape où se situe le client
     */
    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    @Override
    public String toString() {
        return "Client{" +
                "numeroClient=" + numeroClient +
                ", rang=" + rang +
                ", etape=" + this.etape +
                '}';
    }
}
