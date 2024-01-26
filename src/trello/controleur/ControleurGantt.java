package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import trello.vue.VueGantt;
import trello.modele.ModeleTrello;

/**
 * Contrôleur permettant l'affichage de la fenêtre contenant le diagramme de Gantt
 */
public class ControleurGantt implements EventHandler<ActionEvent> {
    //Attributs
    ModeleTrello mod;

    /**
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     */
    public ControleurGantt(ModeleTrello m) {
        this.mod = m;
    }

    /***
     * Méthode qui affiche le diagramme de Gantt
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        //Si aucun tableau n'est sélectionné
        if (mod.getTabSelectionne() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Impossible de générer le diagramme de Gantt, aucun tableau sélectionné");
            alert.show();
            return;
        }

        Stage stage = new Stage();
        VueGantt vueGantt = new VueGantt();

        stage.initModality(Modality.WINDOW_MODAL);

        vueGantt.getStylesheets().clear();

        if (ModeleTrello.DARK_MODE) {
            vueGantt.getStylesheets().add("darkmode.css");
        } else {
            vueGantt.getStylesheets().add("lightmode.css");
        }

        mod.ajouterObservateur(vueGantt);
        mod.notifierObservateurs();
        stage.setTitle("Diagramme de Gantt");
        stage.getIcons().add(new Image("trebas.png"));
        Scene scene = new Scene(vueGantt, 1000, 500);
//        scene.setFill(Color.YELLOW);
        stage.setScene(scene);


        b.setDisable(true);

        stage.show();

        stage.setOnCloseRequest(event -> {
            b.setDisable(false);
        });

    }

}