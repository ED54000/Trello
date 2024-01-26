package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import trello.composant.ComposantTache;
import trello.modele.ModeleTrello;
import java.time.LocalDate;

/***
 * Contrôleur permettant de modifier les dates des tâches
 */
public class ControleurModifierDate implements EventHandler<ActionEvent> {

    //Attributs
    private ModeleTrello mod;
    private ComposantTache tache;
    private DatePicker startDatePicker, endDatePicker; // Est utilisé pour les afficher lors du changement de date

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     * @param t la tâche qui doit avoir ses dates changées
     */
    public ControleurModifierDate(ModeleTrello m, ComposantTache t) {
        this.mod = m;
        this.tache = t;
        this.startDatePicker = new DatePicker();
        this.endDatePicker = new DatePicker();
    }

    /***
     * Méthode permettant de modifier les dates
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Création d'une nouvelle scène
        Stage popupStage = new Stage();
        popupStage.setTitle("Sélectionnez les dates");
        // Création des éléments utiles
        VBox popupLayout = new VBox(10);
        Label labDebut = new Label("Date de début");
        Label labFin = new Label("Date de fin");
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.setPadding(new Insets(10));
        // Les DatePickers sont mis à null pour éviter le fait que les tâches gardent la date qu'elles possédaient avant
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        Button closeButton = new Button("Modifier les dates");
        // Création de la méthode utilisée lors du click sur le bouton
        closeButton.setOnAction(e -> {
            // Teste si les DatePickers ne sont pas null et que la date de fin ne soit pas avant la date de début
            if (endDatePicker.getValue() != null && startDatePicker.getValue() != null
                    && endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "La date de fin ne doit pas être antérieure à la date de début");
                alert.show();
            } else {
                // Teste si la date de début est nulle
                if (startDatePicker.getValue() == null) {
                    // Teste si la date de fin est nulle
                    if (endDatePicker.getValue() == null) {
                        // Les deux dates ne peuvent pas être nulls
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Les dates ne doivent pas être nulls");
                        alert.show();
                    } else {
                        // Cas ou la date de fin n'est pas null mais la date debut l'est
                        startDatePicker.setValue(LocalDate.now());
                        // On met la date de début au jour
                    }
                } else {
                    // La date de début n'est pas null
                    // Teste si la date de fin est nulle
                    if (endDatePicker.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "La date de fin ne doit pas être nulle");
                        alert.show();
                    } else {
                        popupStage.close();
                    }
                }

            }


        });

        //Ajout de tous les élements dans la V-box
        popupLayout.getChildren().addAll(labDebut, startDatePicker, labFin, endDatePicker, closeButton);

        Scene popupScene = new Scene(popupLayout, 250, 175);
        popupStage.setScene(popupScene);
        // Affichage et attends que la fenêtre soit refermée
        popupStage.getIcons().add(new Image("trebas.png"));
        popupStage.showAndWait();

        LocalDate debut;
        LocalDate fin;
        // Si les Dates pickers sont null (fermeture de la fenêtre sans rentrer de date) on met les deux dates à ce jour
        if (startDatePicker.getValue() == null) {
            debut = LocalDate.now();
        } else {
            debut = startDatePicker.getValue();
        }
        if (endDatePicker.getValue() == null) {
            fin = LocalDate.now();
        } else {
            fin = endDatePicker.getValue();
        }

        // Modifie les dates de la tâche
        tache.setDateDebut(debut);
        tache.setDateFin(fin);
        // Notifie les observateurs
        this.mod.notifierObservateurs();
    }
}
