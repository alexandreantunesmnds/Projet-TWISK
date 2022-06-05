package twisk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.vues.VueMenu;
import twisk.vues.VueMondeIG;
import twisk.vues.VueOutils;
import twisk.vues.VueRenommerEtape;

public class MainTwisk extends Application {
    @Override
    public void start(Stage primaryStage){
        TailleComposants constantes = TailleComposants.getInstance();
        MondeIG monde = new MondeIG();

        primaryStage.setTitle("TwiskIG");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icone.png"),100,150,true,true));

        BorderPane root = new BorderPane() ;
        root.setTop(new VueMenu(monde));
        VueOutils vueOutils = new VueOutils(monde);
        root.setBottom(vueOutils) ;
        root.setCenter(new VueMondeIG(monde,vueOutils));

        primaryStage.setScene(new Scene(root, constantes.getLargeurFenetre(), constantes.getHauteurFenetre()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
