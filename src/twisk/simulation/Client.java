package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    int numeroClient;
    int rang;
    Etape etape;

    /**
     * Construteur
     * @param numero le numero du client
     */
    public Client(int numero) {
        this.numeroClient = numero;
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
                ", etape=" + etape +
                '}';
    }
}
