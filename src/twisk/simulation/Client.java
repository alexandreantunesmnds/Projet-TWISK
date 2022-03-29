package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    int numeroClient;
    int rang;
    Etape etape;

    public Client(int numero) {
        this.numeroClient = numero;
    }

    void allerA(Etape etape, int rang){
        this.etape = etape;
        this.rang = rang;
    }
}
