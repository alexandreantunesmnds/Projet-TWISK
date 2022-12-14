package twisk.outils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {
    private Runtime runtime;
    private int noSimulation;

    /**
     * Constructeur
     */
    public KitC() {
        runtime = Runtime.getRuntime();
        noSimulation = FabriqueSimulation.getInstance().getNoSimulation();
    }

    /**
     * Fonction copie en utilisant Stream
     * @param source
     * @param dest
     * @throws IOException exception
     */
    private void copier(InputStream source, File dest) throws IOException {
        InputStream sourceFile = source;
        OutputStream destinationFile = new FileOutputStream(dest) ;
        // Lecture par segment de 0.5Mo
        byte buffer[] = new byte[512 * 1024];
        int nbLecture;
        while ((nbLecture = sourceFile.read(buffer)) != -1){
            destinationFile.write(buffer, 0, nbLecture);
        }
        destinationFile.close();
        sourceFile.close();
    }

    /**
     * Fonction qui crée un répertoire temporaire et y place des fichiers utile à l'exécution de twisk
     */
    public void creerEnvironnement() {
        try {
            // création du répertoire twisk sous /tmp. Ne déclenche pas d’erreur si le répertoire existe déjà
            Path directories = Files.createDirectories(Paths.get("/tmp/twisk"));
            // copie des deux fichiers programmeC.o et def.h depuis le projet sous /tmp/twisk
            String[] liste = {"programmeC.o", "def.h", "codeNatif.o"};
            for (String nom : liste) {
                InputStream source = getClass().getResource("/codeC/" + nom).openStream() ;
                File destination = new File("/tmp/twisk/" + nom) ;
                copier(source, destination);
                //Path source = Paths.get(getClass().getResource("/codeC/" + nom).getPath());
                //Path newdir = Paths.get("/tmp/twisk/");
                //Files.copy(source, newdir.resolve(source.getFileName()), REPLACE_EXISTING);
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
        String commande = "gcc -Wall -fPIC -c /tmp/twisk/client.c -o /tmp/twisk/client.o -lm";

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
        String commande = "gcc -shared /tmp/twisk/programmeC.o /tmp/twisk/codeNatif.o /tmp/twisk/client.o -o /tmp/twisk/libTwisk"+this.noSimulation+".so";
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
    public int getNumeroSimulation(){
        return this.noSimulation;
    }
}
