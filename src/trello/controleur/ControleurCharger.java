package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import trello.modele.ModeleTrello;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * Contrôleur permettant de charger un fichier
 */
public class ControleurCharger implements EventHandler<ActionEvent> {
    // Attribut
    private ModeleTrello mod;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     */
    public ControleurCharger(ModeleTrello m) {
        this.mod = m;
    }


    /***
     * Méthode que charge un fichier et crée les tableaux et les listes nécessaires
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner sauvegarde");
        // L'explorateur de fichier s'ouvre à la racine du projet
        fileChooser.setInitialDirectory(new File("C:"));

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null) {
            // Cas ou il n'y a aucun fichier
            Alert popup = new Alert(Alert.AlertType.INFORMATION);
            popup.setHeaderText("Information");
            popup.setContentText("Aucun fichier selectionné, chargement impossible");
            popup.show();
        }
        try {
            this.mod.charger(selectedFile);
        } catch (IOException | ClassNotFoundException e) {
            Alert popup = new Alert(Alert.AlertType.WARNING);
            popup.setHeaderText("Information");
            popup.setContentText("Une erreur est apparue, chargement impossible");
            popup.show();
            System.out.println(e.getMessage());
        }
    }
}
