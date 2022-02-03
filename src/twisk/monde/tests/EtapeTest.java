package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class EtapeTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void ajouterSuccesseur() {
    }

    @Test
    abstract void estUneActivite();

    @Test
    abstract void estUnGuichet();

    @Test
    void iterator() {
    }
}