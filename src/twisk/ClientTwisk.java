package twisk;

import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.simulation.Simulation;

public class ClientTwisk {

    public static void main(String[] args) {
        Monde world = new Monde();
        testPompeAEssence(world);
        Simulation play = new Simulation();
        play.simuler(world);
    }

    public static void testPompeAEssence(Monde stationEssence){
        Etape attentePompe = new Guichet("FileDAttentePompe", 8);
        Etape pompeAEssence = new Activite("PompeAEssence", 10, 2);
        Etape attenteCaisse = new Guichet("FileDAttenteCaisse",1);
        Etape caisse = new Activite("Caisse", 4, 2);
        Etape attentePneu = new Guichet("FileDAttentePneu",2);
        Etape gonflementPneu = new Activite("GonflerPneu",8,5);

        attenteCaisse.ajouterSuccesseur(pompeAEssence);
        pompeAEssence.ajouterSuccesseur(caisse,attentePneu);
        caisse.ajouterSuccesseur(attentePneu);
        attentePneu.ajouterSuccesseur(gonflementPneu);

        stationEssence.aCommeEntree(attentePompe);
        stationEssence.aCommeSortie(pompeAEssence,caisse,gonflementPneu);
        stationEssence.ajouter(attentePompe,pompeAEssence,attenteCaisse,caisse,attentePneu,gonflementPneu);

    }
}
