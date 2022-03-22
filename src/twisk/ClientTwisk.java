package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

public class ClientTwisk {

    public static void main(String[] args) {
        Monde world = new Monde();
        brigitte(world);
        Simulation play = new Simulation();
        play.simuler(world);
    }

    public static void testPompeAEssence(Monde stationEssence){
        Etape attentePompe = new Guichet("FileDAttentePompe", 8);
        Etape pompeAEssence = new ActiviteRestreinte("PompeAEssence", 10, 2);
        Etape attenteCaisse = new Guichet("FileDAttenteCaisse",1);
        Etape caisse = new ActiviteRestreinte("Caisse", 4, 2);
        Etape attentePneu = new Guichet("FileDAttentePneu",2);
        Etape gonflementPneu = new ActiviteRestreinte("GonflerPneu",8,5);

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
        Etape attenteZoo = new Guichet("GUICHET_ZOO",2);
        Etape baladeAuZoo = new ActiviteRestreinte("ZOO");

        attenteZoo.ajouterSuccesseur(baladeAuZoo);
        aprem.aCommeEntree(attenteZoo);
        aprem.aCommeSortie(baladeAuZoo);
        aprem.ajouter(attenteZoo,baladeAuZoo);
    }

    public static void brigitte(Monde monde){
        Activite zoo = new Activite("balade au zoo", 3, 1);
        Guichet guichet = new Guichet("acces au toboggan", 2);
        Activite tob = new ActiviteRestreinte("toboggan", 2, 1);

        zoo.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(tob);

        monde.ajouter(zoo, tob, guichet);

        monde.aCommeEntree(zoo);
        monde.aCommeSortie(tob);

        Simulation s = new Simulation();
        s.setNbClients(5);
        s.simuler(monde);
    }
}
