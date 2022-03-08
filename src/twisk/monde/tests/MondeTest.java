package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import static org.junit.jupiter.api.Assertions.*;

public class MondeTest {
    private Monde world;
    private SasEntree sasEntree;
    private Activite act;
    private Guichet guich;
    private ActiviteRestreinte actR;
    private SasSortie sasSortie;

    @BeforeEach
    void setUp() {
        this.world = new Monde();
        this.sasEntree = new SasEntree();
        this.act = new Activite("Piscine",6,3);
        this.guich = new Guichet("Caisse");
        this.actR = new ActiviteRestreinte("Toboggan",4,2);
        this.sasSortie = new SasSortie();
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
        this.world.aCommeEntree(this.guich);
        this.world.aCommeSortie(this.act);

        this.guich.ajouterSuccesseur(this.actR);

        this.actR.ajouterSuccesseur(this.act);

        this.world.ajouter(this.guich,this.actR,this.act);


        String code = this.world.toC();
        assertEquals("#include <stdio.h>\n"+"#include <stdlib.h>\n" +
                "#include def.h\n" +
                "\n" +
                "void Simulation(int ids){\n" +
                "entrer(SasEntree);\n" +
                "transfert(SasEntree,Caisse);\n" +
                "P(ids,1);\n" +
                "transfert(Caisse,Toboggan);\n" +
                "delai(4,2);\n"+
                "V(ids,1);\n" +
                "transfert(Toboggan,Piscine);\n" +
                "delai(6,3);\n" +
                "transfert(Piscine,SasSortie);\n" +
                "}",code);
    }
}