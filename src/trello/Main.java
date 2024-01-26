package trello;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import trello.composant.Tableau;
import trello.modele.ModeleTrello;
import trello.vue.VueListe;
import trello.vue.VueTableau;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        ModeleTrello modele = new ModeleTrello();
        ScrollPane root = new ScrollPane();
        BorderPane pane = new BorderPane();
        root.setContent(pane);
        scene = new Scene(root, 1000, 500);

        pane.getStyleClass().add("pane");
        root.getStyleClass().add("pane");
        
        ModeleTrello.DARK_MODE = true;

        if(ModeleTrello.DARK_MODE){
            scene.setFill(Color.rgb(40, 41, 42));
            scene.getStylesheets().add("darkmode.css");
        } else {
            scene.getStylesheets().add("lightmode.css");
            scene.setFill(Color.rgb(255, 255, 255));
        }

        VueTableau vueTableau = new VueTableau();
        VueListe vueListe = new VueListe();
        vueTableau.setPadding(new Insets(10));
        vueTableau.getStyleClass().add("vTab");

        vueTableau.setSpacing(20);
        vueTableau.setMinHeight(Tableau.MIN_HEIGHT);
        vueTableau.setMinWidth(Tableau.MIN_WIDTH + 150);
        vueTableau.setMaxWidth(Tableau.MAX_WIDTH);

        vueListe.setPadding(new Insets(10));
        vueListe.setSpacing(10);

        pane.setLeft(vueTableau);
        pane.setCenter(vueListe);

        modele.ajouterObservateur(vueTableau);
        modele.ajouterObservateur(vueListe);
        modele.notifierObservateurs();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("TreBas");
        stage.getIcons().add(new Image("trebas.png"));
        stage.show();
    }
}