package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;

import static org.junit.jupiter.api.Assertions.*;

public class MondeTest {
    private Monde world;
    private Etape act;
    private Etape guich;

    @BeforeEach
    void setUp() {
        this.world = new Monde();
        this.act = new Activite("Piscine",6,3);
        this.guich = new Guichet("Caisse");
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
        this.world.ajouter(this.guich,this.act);
        this.world.aCommeEntree(this.guich);
        this.world.aCommeSortie(this.act);
        String code = this.world.toC();
        assertEquals(code,"#include <stdio.h>\n"+"#include <stdlib.h>\n" +
                "#include def.h\n" +
                "\n" +
                "void Simulation(int ids){\n" +
                this.world.getEntree().toC()+"}");
    }
}