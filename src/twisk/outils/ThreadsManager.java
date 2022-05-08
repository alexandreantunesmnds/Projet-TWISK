package twisk.outils;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThreadsManager{
    private static ThreadsManager instance = new ThreadsManager();
    private List <Thread> listThread;
    /**
     * Constructeur
     */
    private ThreadsManager(){
        this.listThread = new ArrayList<>();
    }

    /**
     * Getter
     * @return une instance de ThreadsManager
     */
    public static ThreadsManager getInstance(){
        return instance;
    }

    /**
     * Fonction qui permet de créer un thread et d'en demander son exécution
     */
    public void lancer(Task task){
        Thread thread = new Thread(task);
        this.listThread.add(thread);
        thread.start();
    }

    /**
     * Fonction qui tue les threads
     */
    public void detruireTout(){
        for(Thread threads : listThread){
            threads.interrupt();
        }
    }

    /**
     * Fonction qui tue les threads actifs (permet l'arrêt de la simulation en cours)
     */
    public void detruireTacheActive(){
        for(Thread threads : listThread){
            if (threads.isAlive()){
                threads.interrupt();
            }
        }
    }
}
