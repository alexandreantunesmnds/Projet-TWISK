package twisk.outils.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Monde;
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
        File cn = new File("/tmp/twisk/codeNatif.o");
        File def = new File("/tmp/twisk/def.h");

        assertTrue(dos.exists() && dos.isDirectory()); //Teste si le chemin est bien un dossier et qu'il existe
        assertTrue(prog.exists() && !prog.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
        assertTrue(cn.exists() && !cn.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
        assertTrue(def.exists() && !def.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
    }

    @Test
    void creerFichier(){
        KitC kc = new KitC();
        kc.creerEnvironnement();

        File cl = new File("/tmp/twisk/client.c");

        Monde world = new Monde();
        kc.creerFichier(world.toC());
        assertTrue(cl.exists() && !cl.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
    }

    @Test
    void compiler(){
        KitC kc = new KitC();
        kc.creerEnvironnement();

        File comp = new File("/tmp/twisk/client.o");

        Monde world = new Monde();
        kc.creerFichier(world.toC());
        kc.compiler();
        assertTrue(comp.exists() && !comp.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
    }

    @Test
    void construireLaLibrairie(){
        KitC kc = new KitC();
        kc.creerEnvironnement();

        File lib = new File("/tmp/twisk/libTwisk.so");

        Monde world = new Monde();
        kc.creerFichier(world.toC());
        kc.compiler();
        kc.construireLaLibrairie();
        assertTrue(lib.exists() && !lib.isDirectory()); //Teste si le chemin est bien un fichier et qu'il existe
    }
}