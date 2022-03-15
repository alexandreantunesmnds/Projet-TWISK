package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

public class MondeTest {
    private Monde world;
    private Activite act;
    private Guichet guich;
    private ActiviteRestreinte actR;

    @BeforeEach
    void setUp() {
        this.world = new Monde();
        this.guich = new Guichet("Caisse");
        this.actR = new ActiviteRestreinte("Toboggan",4,2);
        this.act = new Activite("Piscine",6,3);
    }

    @Test
    void aCommeEntree() {
        this.world.aCommeEntree(new Activite("A1"), new Activite("A2"), new Guichet("G1"));

        assertEquals(3, this.world.nbEntree());
    }

    @Test
    void aCommeSortie() {
        Etape Activite1 = new Activite("Piscine");
        Etape Guichet1 = new Guichet("GuichetPiscine");
        this.world.aCommeSortie(Activite1,Guichet1);
        assertEquals(1,Activite1.nbSuccesseur());
        assertEquals(1,Guichet1.nbSuccesseur());
    }

    @Test
    void ajouter() {
        this.world.ajouter(act,guich);
        assertEquals(this.world.nbEtapes(),2);
        assertEquals(this.world.nbGuichets(),1);
    }

    @Test
    void nbEntree() {
        this.world.aCommeEntree(this.act,this.guich);
        assertEquals(this.world.nbEntree(),2);
    }
    @Test
    void toC() {
        //On crée le monde
        FabriqueNumero.getInstance().reset();
        Monde monde = new Monde();
        Guichet guichet = new Guichet("Caisse");
        ActiviteRestreinte actR = new ActiviteRestreinte("Toboggan",3,2);
        Activite act = new Activite("bac a sable",4,2);
        monde.aCommeEntree(guichet);
        guichet.ajouterSuccesseur(actR);
        actR.ajouterSuccesseur(act);
        monde.aCommeSortie(act);
        monde.ajouter(guichet,actR,act);
        String code = monde.toC();
        assertEquals("#include <stdio.h>\n"+"#include <stdlib.h>\n" +
                "#include \"def.h\"\n" +
                "\n" +
                "#define SEM_CAISSE 1\n" +
                "#define ENTREE 0\n" +
                "#define SORTIE 1\n" +
                "#define CAISSE 2\n" +
                "#define TOBOGGAN 3\n" +
                "#define BAC_A_SABLE 4\n" +
                "\n" +
                "void Simulation(int ids){\n" +
                "entrer(ENTREE);\n" +
                "transfert(ENTREE,CAISSE);\n" +
                "P(ids,CAISSE);\n" +
                "transfert(CAISSE,TOBOGGAN);\n" +
                "delai(3,2);\n"+
                "V(ids,CAISSE);\n" +
                "transfert(TOBOGGAN,BAC_A_SABLE);\n" +
                "delai(4,2);\n" +
                "transfert(BAC_A_SABLE,SORTIE);\n" +
                "}",code);
    }
}
