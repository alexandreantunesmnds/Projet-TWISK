package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;

import static org.junit.jupiter.api.Assertions.*;

abstract class EtapeTest {

    protected Guichet guich;
    protected Activite act ;


    @BeforeEach
    void setUp() {
        this.act = new Activite("Piscine");
        this.guich = new Guichet("Caisse");
    }

    @Test
    void ajouterSuccesseur() {
        guich.ajouterSuccesseur(act);
        assertEquals(1,guich.nbSuccesseur());

        assertEquals(0,act.nbSuccesseur());
    }

    @Test
    abstract void estUneActivite();

    @Test
    abstract void estUnGuichet();

    @Test
    void iterator() {
        
    }
}