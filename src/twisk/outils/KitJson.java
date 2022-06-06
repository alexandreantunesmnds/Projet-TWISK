package twisk.outils;

import org.json.JSONArray;
import org.json.JSONObject;
import twisk.exceptions.ArcException;
import twisk.exceptions.FileException;
import twisk.mondeIG.*;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitJson {

    private MondeIG monde;

    /**
     * Constructeur
     * @param monde
     */
    public KitJson(MondeIG monde){
        this.monde = monde;
    }

    /**
     * Fonction qui permet de sauvegarder le monde
     */
    public void sauvegarder() throws FileException {
        try{
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."+File.separator));
            chooser.setMultiSelectionEnabled(true);

            int reponse = chooser.showDialog(chooser,"Enregistrez sous");

            if(reponse == JFileChooser.APPROVE_OPTION){
                String cheminFichier = chooser.getSelectedFile().toString();

                if(!cheminFichier.contains(".json")){ //On force à enregister en .json
                    cheminFichier += ".json";
                }

                JSONObject obj = new JSONObject();

                obj.put("ListeEtape",this.mapToList(this.monde.getListeEtape()));
                obj.put("ListeArc",this.monde.getListeArc());

                //On sauvegarde dans une liste les étapes entrées et sorties
                List<String> listeEntrees = new ArrayList<String>();
                List<String> listeSorties = new ArrayList<String>();
                for(EtapeIG etapeIG : this.monde){
                    if(etapeIG.estUneEntree()){
                        listeEntrees.add(etapeIG.getIdentifiant());
                    }else if(etapeIG.estUneSortie()){
                        listeSorties.add(etapeIG.getIdentifiant());
                    }
                }
                obj.put("ListeEntrees",listeEntrees);
                obj.put("ListeSorties",listeSorties);

                //System.out.println(obj.toString());

                FileWriter file = new FileWriter(cheminFichier);
                file.write(obj.toString());
                file.flush();
                file.close();
            }
        } catch (IOException s) {
            s.printStackTrace();
        }
    }

    /**
     * Fonction qui permet d'ouvrir un fichier au format json
     * @throws FileException
     */
    public void ouvrirFichier() throws FileException {
        try{
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."+File.separator));
            chooser.setMultiSelectionEnabled(true);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                Path filePath = Path.of(chooser.getSelectedFile().toString());

                if(chooser.getSelectedFile().toString().contains(".json")){
                    this.monde.clear();
                    String content = Files.readString(filePath);
                    //System.out.println(content);
                    JSONObject obj = new JSONObject(content);

                    //System.out.println(obj.toString());
                    JSONArray etapes = obj.getJSONArray("ListeEtape");

                    //On crée toutes les étapes
                    int nbEtape = 0;
                    for (Object etape : etapes){
                        JSONObject jetape = (JSONObject) etape;
                        EtapeIG etapeIG = null;
                        if(etape.toString().contains("temps")){
                            etapeIG = new ActiviteIG(jetape.getString("nom"), jetape.getString("identifiant"),
                                    jetape.getInt("posX"),jetape.getInt("posY"),
                                    jetape.getInt("largeur"),jetape.getInt("hauteur"),
                                    false,false,jetape.getInt("temps"),jetape.getInt("ecartTemps")
                                    ,false);
                            this.monde.ajouter(etapeIG);
                            //System.out.println("Act="+etape);

                        }else if(etape.toString().contains("nbJetons")){
                            etapeIG = new GuichetIG(jetape.getString("nom"),jetape.getString("identifiant"),
                                    jetape.getInt("posX"),jetape.getInt("posY"),
                                    jetape.getInt("largeur"),jetape.getInt("hauteur"),
                                    false,false, jetape.getInt("nbJetons"), jetape.getBoolean("sensGaucheDroite") );
                            this.monde.ajouter(etapeIG);
                            //System.out.println("Gui="+etape);
                        }
                        nbEtape++;
                    }

                    //On crée tout les arcs ce qui crée automatiquement les sucesseurs de chaque étape
                    JSONArray arcs = obj.getJSONArray("ListeArc");
                    for(Object arc : arcs){
                        //System.out.println("Arc="+arc);
                        JSONObject jarc = (JSONObject) arc;
                        JSONObject jpt1 = jarc.getJSONObject("pt1");
                        JSONObject jpt2 = jarc.getJSONObject("pt2");
                        JSONObject jetape1 = jpt1.getJSONObject("etapeLiee");
                        JSONObject jetape2 = jpt2.getJSONObject("etapeLiee");
                        try {
                            this.monde.ajouterArc(new PointDeControleIG(jpt1.getInt("posX"),jpt1.getInt("posY"),jpt1.getString("id"),this.monde.getListeEtape().get(jetape1.getString("identifiant"))),
                                    new PointDeControleIG(jpt2.getInt("posX"),jpt2.getInt("posY"),jpt2.getString("id"),this.monde.getListeEtape().get(jetape2.getString("identifiant"))));
                        } catch (ArcException e) {
                            e.printStackTrace();
                        }
                    }

                    //On ajoute maintenant les entrées et les sorties
                    JSONArray entrees = obj.getJSONArray("ListeEntrees");
                    for(Object entree : entrees){
                        this.monde.getListeEtape().get(entree).setEntree();
                    }

                    JSONArray sorties = obj.getJSONArray("ListeSorties");
                    for(Object sortie : sorties){
                        this.monde.getListeEtape().get(sortie).setSortie();
                    }

                    FabriqueIdentifiant.getInstance().setIdentifiantEtape(nbEtape+1);
                    this.monde.notifierObservateurs();
                }else{
                    throw new FileException("Erreur: le format du fichier n'est pas correct");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui ouvre un monde donné en paramètre
     *
     * @param path chemin du fichier
     */
    public void ouvrirFichier(String path){
        try {
            this.monde.clear();
            String content = Files.readString(Path.of(path));
            //System.out.println(content);
            JSONObject obj = new JSONObject(content);

            //System.out.println(obj.toString());
            JSONArray etapes = obj.getJSONArray("ListeEtape");

            //On crée toutes les étapes
            int nbEtape = 0;
            for (Object etape : etapes) {
                JSONObject jetape = (JSONObject) etape;
                EtapeIG etapeIG = null;
                if (etape.toString().contains("temps")) {
                    etapeIG = new ActiviteIG(jetape.getString("nom"), jetape.getString("identifiant"),
                            jetape.getInt("posX"), jetape.getInt("posY"),
                            jetape.getInt("largeur"), jetape.getInt("hauteur"),
                            false, false, jetape.getInt("temps"), jetape.getInt("ecartTemps")
                            , false);
                    this.monde.ajouter(etapeIG);
                    //System.out.println("Act="+etape);

                } else if (etape.toString().contains("nbJetons")) {
                    etapeIG = new GuichetIG(jetape.getString("nom"), jetape.getString("identifiant"),
                            jetape.getInt("posX"), jetape.getInt("posY"),
                            jetape.getInt("largeur"), jetape.getInt("hauteur"),
                            false, false, jetape.getInt("nbJetons"), jetape.getBoolean("sensGaucheDroite"));
                    this.monde.ajouter(etapeIG);
                    //System.out.println("Gui="+etape);
                }
                nbEtape++;
            }

            //On crée tout les arcs ce qui crée automatiquement les sucesseurs de chaque étape
            JSONArray arcs = obj.getJSONArray("ListeArc");
            for (Object arc : arcs) {
                //System.out.println("Arc="+arc);
                JSONObject jarc = (JSONObject) arc;
                JSONObject jpt1 = jarc.getJSONObject("pt1");
                JSONObject jpt2 = jarc.getJSONObject("pt2");
                JSONObject jetape1 = jpt1.getJSONObject("etapeLiee");
                JSONObject jetape2 = jpt2.getJSONObject("etapeLiee");
                try {
                    this.monde.ajouterArc(new PointDeControleIG(jpt1.getInt("posX"), jpt1.getInt("posY"), jpt1.getString("id"), this.monde.getListeEtape().get(jetape1.getString("identifiant"))),
                            new PointDeControleIG(jpt2.getInt("posX"), jpt2.getInt("posY"), jpt2.getString("id"), this.monde.getListeEtape().get(jetape2.getString("identifiant"))));
                } catch (ArcException e) {
                    e.printStackTrace();
                }
            }

            //On ajoute maintenant les entrées et les sorties
            JSONArray entrees = obj.getJSONArray("ListeEntrees");
            for (Object entree : entrees) {
                this.monde.getListeEtape().get(entree).setEntree();
            }

            JSONArray sorties = obj.getJSONArray("ListeSorties");
            for (Object sortie : sorties) {
                this.monde.getListeEtape().get(sortie).setSortie();
            }

            FabriqueIdentifiant.getInstance().setIdentifiantEtape(nbEtape + 1);
            this.monde.notifierObservateurs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fonction qui convertie une HashMap en une liste
     * @param hashMap une map
     * @return une liste
     */
    private List<EtapeIG> mapToList(HashMap<String, EtapeIG> hashMap){
        List<EtapeIG> list = new ArrayList<>();
        for(EtapeIG etapeIG: hashMap.values()){
            list.add(etapeIG);
        }
        return list;
    }
}
