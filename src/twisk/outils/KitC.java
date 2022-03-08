package twisk.outils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {
    public KitC() {
    }

    /**
     * Fonction qui crée un répertoire temporaire et y place des fichiers utile à l'exécution de twisk
     */
    public void creerEnvironnement() {
        try {
            // création du répertoire twisk sous /tmp. Ne déclenche pas d’erreur si le répertoire existe déjà
            Path directories = Files.createDirectories(Paths.get("/tmp/twisk"));
            // copie des deux fichiers programmeC.o et def.h depuis le projet sous /tmp/twisk
            String[] liste = {"programmeC.o", "def.h"};
            for (String nom : liste) {
                Path source = Paths.get(getClass().getResource("/codeC/" + nom).getPath());
                Path newdir = Paths.get("/tmp/twisk/");
                Files.copy(source, newdir.resolve(source.getFileName()), REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Fonction qui écrit le codeC du clientTwisk
     * @param codeC Code c du clientTwisk
     */
    public void creerFichier(String codeC){
        BufferedWriter flotFiltre ;
        FileWriter flot ;

        File file = new File("/tmp/twisk/client.c");

        try {
            flot = new FileWriter(file);
            flotFiltre = new BufferedWriter(flot) ;

            flotFiltre.write(codeC);

            flotFiltre.close() ;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ErreurFichier : erreur lors de la création du fichier");
        }
    }
}
