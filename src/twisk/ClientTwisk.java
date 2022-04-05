package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

public class ClientTwisk {

    public static void main(String[] args) {
        Monde world = new Monde();
        brigitte(world);
        Simulation play = new Simulation();
        play.setNbClients(5);
        play.simuler(world);
        //Monde world2 = new Monde();
        //testPompeAEssence(world2);
        //play.simuler(world2);
    }

    public static void testPompeAEssence(Monde stationEssence){
        Etape attentePompe = new Guichet("Attente Pompe", 2);
        Etape pompeEssence = new ActiviteRestreinte("Pompe à essence", 4,2);
        Etape gonflerPneus = new Activite("Gonfler pneus", 3,2);
        Etape attenteLavage = new Guichet("Attente Lavage", 1);
        Etape lavageAuto = new ActiviteRestreinte("Lavage Auto",4,3);

        attentePompe.ajouterSuccesseur(pompeEssence);
        pompeEssence.ajouterSuccesseur(gonflerPneus,attenteLavage);
        attenteLavage.ajouterSuccesseur(lavageAuto);

        stationEssence.aCommeEntree(attentePompe);
        stationEssence.aCommeSortie(lavageAuto,gonflerPneus);
        stationEssence.ajouter(attentePompe,attenteLavage,pompeEssence,gonflerPneus,lavageAuto);



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

    }
}
