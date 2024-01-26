package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import trello.modele.ModeleTrello;
import trello.vue.VueArchive;

import static trello.Main.scene;

/***
 * Contrôleur permettant d'afficher les listes est les tâches archivées
 */
public class ControleurAfficherArchive implements EventHandler<ActionEvent> {
    //Attributs
    private ModeleTrello mod;

    /***
     * Constructeur du contrôleur
     * @param mod le modèle utilisé pour notifier les observateurs
     */
    public ControleurAfficherArchive(ModeleTrello mod) {
        this.mod = mod;
    }

    /***
     * Méthode qui permet d'afficher les listes est les tâches archivées
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        //On créer une nouvelle fenêtre
        BorderPane root = new BorderPane();
        Scene sceneArchive = new Scene(root, 800, 600);
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(scene.getWindow());

        root.getStyleClass().add("pane");
        sceneArchive.getStylesheets().clear();
        //Affichage
        if (ModeleTrello.DARK_MODE) {
            sceneArchive.setFill(Color.rgb(40, 41, 42));
            sceneArchive.getStylesheets().add("darkmode.css");
        } else {
            sceneArchive.getStylesheets().add("lightmode.css");
            sceneArchive.setFill(Color.rgb(255, 255, 255));
        }
        //Création d'une vue
        VueArchive vueArchive = new VueArchive();
        vueArchive.setSpacing(20);
        vueArchive.setPadding(new Insets(10));
        root.setCenter(vueArchive);

        this.mod.ajouterObservateur(vueArchive);
        this.mod.notifierObservateurs();

        stage.setTitle("Archives");
        stage.setScene(sceneArchive);
        stage.show();
    }
}
