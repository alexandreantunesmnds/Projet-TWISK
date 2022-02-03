package twisk;

import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.simulation.Simulation;

public class ClientTwisk {

    public static void main(String[] args) {
        Monde world = new Monde();
        testApresMidi(world);
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

    public static void testRestaurant(Monde restauration){
        Etape attentePass = new Guichet("AttentePassVaccinale", 1);
        Etape montrerPass = new Activite("MontrerPassVaccinale", 1,2);
        Etape attenteBorne = new Guichet("AttenteBorneDeCommande", 5);
        Etape commander = new Activite("Commander", 10,2);
        Etape attendreCommande = new Activite("AttendreCommande",30,2);
        Etape mangerSurPlace = new Activite("MangerSurPlace", 120,2);

        attentePass.ajouterSuccesseur(mangerSurPlace);
        montrerPass.ajouterSuccesseur(attenteBorne);
        attenteBorne.ajouterSuccesseur(commander);
        commander.ajouterSuccesseur(attendreCommande);
        attendreCommande.ajouterSuccesseur(mangerSurPlace);

        restauration.aCommeEntree(attentePass);
        restauration.aCommeSortie(attendreCommande,mangerSurPlace);
        restauration.ajouter(attentePass,montrerPass,attenteBorne,commander,attendreCommande,mangerSurPlace);
    }

    public static void testApresMidi(Monde aprem){
        Etape baladeAuZoo = new Activite("Ballade au zoo");
        Etape attenteToboggan = new Guichet("AttenteToboggan",2);
        Etape toboggan = new Activite("Toboggan",2,1);

        baladeAuZoo.ajouterSuccesseur(attenteToboggan);
        attenteToboggan.ajouterSuccesseur(toboggan);

        aprem.aCommeEntree(baladeAuZoo);
        aprem.aCommeSortie(toboggan);
        aprem.ajouter(baladeAuZoo,attenteToboggan,toboggan);
    }
}
