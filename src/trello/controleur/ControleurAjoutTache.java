package trello.controleur;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.composant.TacheFeuille;
import trello.modele.ModeleTrello;
import trello.vue.AffichageBureau;
import trello.vue.AffichageDetailleTache;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static trello.Main.scene;

/***
 * Contrôleur permettant d'ajouter des tâches
 */
public class ControleurAjoutTache implements EventHandler<ActionEvent> {

    private ModeleTrello mod;
    private Liste liste;
    private TextArea tacheTexte;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     * @param l la liste ou il faut ajouter la tâche
     * @param tf le nom de la tâche
     */
    public ControleurAjoutTache(ModeleTrello m, Liste l, TextArea tf) {
        this.mod = m;
        this.liste = l;
        this.tacheTexte = tf;
        this.tacheTexte.setPrefSize(150, 50);
        this.tacheTexte.setWrapText(true);

        this.startDatePicker = new DatePicker();
        this.endDatePicker = new DatePicker();
    }

    /***
     * Méthode pour ajouter une tâche à une liste
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // On récupère le bouton et le textfield qui ont déclenché l'événement
        Button b = (Button) actionEvent.getSource();
        // On bloque le bouton pour ne pas avoir de cas indésirables

        if (this.tacheTexte.getText().equals("")) {
            // Si l'utilisateur n'a pas rentré de nom
            this.tacheTexte.setPromptText("Veuillez entrer un nom de tâche");
        } else {
            // Création d'une nouvelle scène
            Stage popupStage = new Stage();
            popupStage.setTitle("Sélectionnez les dates");
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(scene.getWindow());
            b.setDisable(true);

            VBox popupLayout = new VBox(10);
            Label labDebut = new Label("Date de début");
            labDebut.setId("txt");
            Label labFin = new Label("Date de fin");
            labFin.setId("txt");
            popupLayout.setAlignment(Pos.CENTER);
            popupLayout.setPadding(new Insets(10));
            // Les DatePickers sont mis à null pour éviter le fait que les tâches gardent la date qu'elles possédaient avant
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            Button closeButton = new Button("Ajouter la tâche");

            closeButton.getStyleClass().add("bouton");

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
            if (ModeleTrello.DARK_MODE) {
                popupScene.setFill(Color.rgb(40, 41, 42));
                popupScene.getStylesheets().add("darkmode.css");
            } else {
                popupScene.getStylesheets().add("lightmode.css");
                popupScene.setFill(Color.rgb(255, 255, 255));
            }

            popupScene.getRoot().getStyleClass().add("pane");
            popupStage.setScene(popupScene);
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
                fin = debut.plus(1, ChronoUnit.DAYS);
            } else {
                fin = endDatePicker.getValue();
            }

            ComposantTache tache = new TacheFeuille(this.tacheTexte.getText(), this.liste.getId(), debut, fin);
            // Ajoute les dates à la tâche
            this.mod.getTabSelectionne().checkDateMinAndMax(debut);
            this.mod.getTabSelectionne().checkDateMinAndMax(fin);

            // On ajoute la tâche à la liste
            this.liste.ajouterTache(tache);

            // On récupère le parent du bouton pour pouvoir ajouter la tâche
            VBox parent = (VBox) b.getParent();

            // Création de la vue de la tâche
            Node affichageTache;

            if (ModeleTrello.VUE_TACHE) {
                affichageTache = AffichageBureau.buildTache(tache, tache.getNom(), mod);
                parent.getChildren().add(affichageTache);
            } else {
                affichageTache = AffichageDetailleTache.buildTache(tache, tache.getNom(), mod);
                parent.getChildren().add(affichageTache);
            }

            // Ajout de la tâche à la scène
            b.setDisable(false);
            // Création de la transition de mise à l'échelle
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), affichageTache);
            scaleTransition.setFromX(0.5);
            scaleTransition.setFromY(0.5);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);

            // Ajout d'une légère oscillation
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(1);
            scaleTransition.setByX(0.1);
            scaleTransition.setByY(0.1);

            // Création de la transition de fondu
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), affichageTache);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);

            // Création de la transition simultanée
            ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
            parallelTransition.play();

            // Suppression des anciens éléments
            parent.getChildren().removeAll(b, this.tacheTexte);

            // Réinitialisation du texte
            this.tacheTexte.setText("");

            // Ajout du bouton pour une nouvelle tâche
            parent.getChildren().addAll(this.tacheTexte, b);
            mod.notifierObservateurs();
        }
    }

}
