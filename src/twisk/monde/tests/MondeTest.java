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

        Guichet caisse = new Guichet("Caisse");
        ActiviteRestreinte piscine = new ActiviteRestreinte("Piscine",10,2);

        Guichet queueToboggan = new Guichet("Queue Toboggan");
        ActiviteRestreinte toboggan = new ActiviteRestreinte("Toboggan");

        Activite bainDeSoleil = new Activite("Bain de soleil",5,1);
        Activite waterpolo = new Activite("Waterpolo",8,2);
        Activite bacASable = new Activite("Bac à sable");

        Activite vestiaire = new Activite("Vestiaire");

        monde.aCommeEntree(caisse);
        monde.aCommeSortie(vestiaire);

        caisse.ajouterSuccesseur(piscine);
        piscine.ajouterSuccesseur(queueToboggan,bainDeSoleil);
        queueToboggan.ajouterSuccesseur(toboggan);
        bainDeSoleil.ajouterSuccesseur(waterpolo,bacASable);
        toboggan.ajouterSuccesseur(vestiaire);
        waterpolo.ajouterSuccesseur(vestiaire);
        bacASable.ajouterSuccesseur(vestiaire);

        monde.ajouter(caisse,vestiaire,piscine,queueToboggan,bainDeSoleil,toboggan,waterpolo,bacASable);

        String code = monde.toC();
        assertEquals("#include <stdio.h>\n" +
                "#include <stdlib.h>\n" +
                "#include \"def.h\"\n" +
                "#include <time.h>\n" +
                "\n" +
                "#define SEM_CAISSE 1\n" +
                "#define SEM_QUEUE_TOBOGGAN 2\n" +
                "#define ENTREE 0\n" +
                "#define SORTIE 1\n" +
                "#define CAISSE 2\n" +
                "#define VESTIAIRE 9\n" +
                "#define PISCINE 3\n" +
                "#define QUEUE_TOBOGGAN 4\n" +
                "#define BAIN_DE_SOLEIL 6\n" +
                "#define TOBOGGAN 5\n" +
                "#define WATERPOLO 7\n" +
                "#define BAC_À_SABLE 8\n" +
                "\n" +
                "void simulation(int ids){\n" +
                "\tsrand(ids);\n" +
                "\t\tentrer(ENTREE);\n" +
                "\tdelai(6,3);\n" +
                "\ttransfert(ENTREE,CAISSE);\n" +
                "\t\tP(ids,SEM_CAISSE);\n" +
                "\ttransfert(CAISSE,PISCINE);\n" +
                "\tdelai(10,2);\n" +
                "\tV(ids,SEM_CAISSE);\n" +
                "\tint nb = (int)((rand()/(float)RAND_MAX*1);\n" +
                "\tswitch(nb):\n" +
                "\tcase 0:{ //vers QUEUE_TOBOGGAN\n" +
                "\ttransfert(PISCINE,QUEUE_TOBOGGAN);\n" +
                "\t\tP(ids,SEM_QUEUE_TOBOGGAN);\n" +
                "\ttransfert(QUEUE_TOBOGGAN,TOBOGGAN);\n" +
                "\tdelai(5,2);\n" +
                "\tV(ids,SEM_QUEUE_TOBOGGAN);\n" +
                "\ttransfert(TOBOGGAN,VESTIAIRE);\n" +
                "\t\tdelai(5,2);\n" +
                "\ttransfert(VESTIAIRE,SORTIE);\n" +
                "\t\tbreak;\n" +
                "\t}\n" +
                "\tcase 1:{ //vers BAIN_DE_SOLEIL\n" +
                "\ttransfert(PISCINE,BAIN_DE_SOLEIL);\n" +
                "\t\tdelai(5,1);\n" +
                "\tint nb = (int)((rand()/(float)RAND_MAX*2);\n" +
                "\tswitch(nb):\n" +
                "\tcase 0:{ //vers WATERPOLO\n" +
                "\ttransfert(BAIN_DE_SOLEIL,WATERPOLO);\n" +
                "\t\tdelai(8,2);\n" +
                "\ttransfert(WATERPOLO,VESTIAIRE);\n" +
                "\t\tdelai(5,2);\n" +
                "\ttransfert(VESTIAIRE,SORTIE);\n" +
                "\t\tbreak;\n" +
                "\t}\n" +
                "\tcase 1:{ //vers BAC_À_SABLE\n" +
                "\ttransfert(BAIN_DE_SOLEIL,BAC_A_SABLE);\n" +
                "\t\tdelai(5,2);\n" +
                "\ttransfert(BAC_À_SABLE,VESTIAIRE);\n" +
                "\t\tdelai(5,2);\n" +
                "\ttransfert(VESTIAIRE,SORTIE);\n" +
                "\t\tbreak;\n" +
                "\t}\n" +
                "\tbreak;\n" +
                "\t}\n" +
                "}",code);
    }
}
