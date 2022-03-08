package twisk.outils.tests;

import org.junit.jupiter.api.Test;
import twisk.outils.KitC;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class KitCTest {

    @Test
    void creerEnvironnement() {
        KitC kc = new KitC();
        kc.creerEnvironnement();
        File dos = new File("/tmp/twisk");
        File prog = new File("/tmp/twisk/programmeC.o");
        File def = new File("/tmp/twisk/def.h");

        assertTrue(dos.exists() && dos.isDirectory()); //Teste si le chemin est bien un dossier et qu'il existe
        assertTrue(prog.exists() && !prog.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
        assertTrue(def.exists() && !def.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
    }
}