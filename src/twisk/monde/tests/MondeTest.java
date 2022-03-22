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
        Activite act1 = new Activite("bac a sable",4,2);
        Activite act2 = new Activite("bain de soleil",10,1);
        Activite act3 = new Activite("water polo",6,2);
        Activite act4 = new Activite("vestiaire",4,2);
        monde.aCommeEntree(guichet);
        guichet.ajouterSuccesseur(actR);
        actR.ajouterSuccesseur(act1);
        act1.ajouterSuccesseur(act2,act3);
        act2.ajouterSuccesseur(act4);
        act3.ajouterSuccesseur(act4);
        monde.aCommeSortie(act4);
        monde.ajouter(guichet,actR,act1,act2,act3,act4);
        String code = monde.toC();
        assertEquals("#include <stdio.h>\n"+"#include <stdlib.h>\n" +
                "#include \"def.h\"\n" +
                "#include <time.h>\n\n"+
                "#define RAND_MAX\n" +
                "#define SEM_CAISSE 1\n" +
                "#define ENTREE 0\n" +
                "#define SORTIE 1\n" +
                "#define CAISSE 2\n" +
                "#define TOBOGGAN 3\n" +
                "#define BAC_A_SABLE 4\n" +
                "#define BAIN_DE_SOLEIL 5\n"+
                "#define WATER_POLO 6\n"+
                "#define VESTIAIRE 7\n"+
                "\n" +
                "void simulation(int ids){\n" +
                "\tsrand(time(NULL));\n"+
                "\tentrer(ENTREE);\n" +
                "\ttransfert(ENTREE,CAISSE);\n" +
                "\tP(ids,CAISSE);\n" +
                "\ttransfert(CAISSE,TOBOGGAN);\n" +
                "\tdelai(3,2);\n"+
                "\tV(ids,CAISSE);\n" +
                "\ttransfert(TOBOGGAN,BAC_A_SABLE);\n" +
                "\tdelai(4,2);\n" +
                "\tint nb = (int)((rand()/(float)RAND_MAX*2);\n"+
                "\tswitch(nb):\n"+
                "\tcase 0:{\n"+
                    "\t\ttransfert(BAC_A_SABLE,BAIN_DE_SOLEIL);\n"+
                    "\t\tdelai(10,1);\n"+
                    "\t\ttransfert(BAIN_DE_SOLEIL,VESTIAIRE);\n"+
                    "\t\tdelai(4,2);\n"+
                    "\t\ttransfert(VESTIAIRE,SORTIE);\n"+
                    "\t\tbreak;\n"+
                "\t}\n"+
                "\tcase 1:{\n"+
                        "\t\ttransfert(BAC_A_SABLE,WATER_POLO);\n"+
                        "\t\tdelai(6,2);\n"+
                        "\t\ttransfert(WATER_POLO,VESTIAIRE);\n"+
                        "\t\tdelai(4,2);\n"+
                        "\t\ttransfert(VESTIAIRE,SORTIE);\n"+
                        "\t\tbreak;\n"+
                    "\t}\n"+
                "}",code);
    }
}
