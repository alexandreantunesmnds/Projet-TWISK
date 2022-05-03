package twisk.outils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThreadsManager implements Iterable <Thread>{
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
    public void lancerTask(){
        Thread thread = new Thread();
        this.listThread.add(thread);
        thread.start();
    }

    @Override
    public Iterator<Thread> iterator() {
        return this.listThread.iterator();
    }

    /**
     * Fonction qui tue les threads
     */
    public void detruireTout(){
        for(Thread threads : this){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
