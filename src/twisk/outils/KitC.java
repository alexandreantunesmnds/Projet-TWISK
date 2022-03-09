package twisk.outils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {
    private Runtime runtime;

    public KitC() {
        runtime = Runtime.getRuntime();
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

    /**
     * Fonction qui compile les fichiers temporaires
     */
    public void compiler(){
        String commande = "gcc -Wall -fPIC -c /tmp/twisk/client.c -o /tmp/twisk/client.o";

        try {
            Process p = runtime.exec(commande);
            p.waitFor();

            // récupération des messages sur la sortie standard et la sortie d’erreur de la commande exécutée
            // à reprendre éventuellement et à adapter à votre code
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String ligne ;
            while ((ligne = output.readLine()) != null) {
                System.out.println(ligne);
            }
            while ((ligne = error.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui crée la librairie
     */
    public void construireLaLibrairie(){
        String commande = "gcc -shared /tmp/twisk/programmeC.o /tmp/twisk/client.o -o /tmp/twisk/libTwisk.so";

        try {
            Process p = runtime.exec(commande);
            p.waitFor();
            // récupération des messages sur la sortie standard et la sortie d’erreur de la commande exécutée
            // à reprendre éventuellement et à adapter à votre code
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String ligne ;
            while ((ligne = output.readLine()) != null) {
                System.out.println(ligne);
            }
            while ((ligne = error.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction native qui lance la simulation du monde
     * @param nbEtapes nombre d'étapes dans le monde
     * @param nbGuichets nombre de guichets dans le monde
     * @param nbClients nombre de clients dans le monde
     * @param tabJetonsGuichet tableau comportant les différents jetons de tout les guichets
     * @return Un tableau d'id des clients
     */
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichet);

    /**
     * Fonction native qui résume la position des clients dans le monde
     * @param nbEtapes nombre d'étapes dans le monde
     * @param nbClients nombre de clients dans le monde
     * @return Un tableau d'id des clients
     */
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);

    public native void nettoyage();
}
