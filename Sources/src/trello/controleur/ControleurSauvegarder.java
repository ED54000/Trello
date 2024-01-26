package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import trello.modele.ModeleTrello;

import java.io.IOException;

/***
 * Contrôleur permettant de sauvegarder les données du tableau
 */
public class ControleurSauvegarder implements EventHandler<ActionEvent> {
    //Attribut
    private ModeleTrello mod;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     */
    public ControleurSauvegarder(ModeleTrello m) {
        this.mod = m;
    }

    /***
     * Méthode appelée lors de l'appui sur le bouton "Sauvegarder"
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Crée une pop-up de type information
        Alert popup = new Alert(Alert.AlertType.INFORMATION);
        popup.setHeaderText("Information");
        try {
            // Tester s'il y a des tableaux à sauver
            if (mod.getTableaux().isEmpty()) {
                popup.setContentText("Sauvegarde annulée : il n'y a aucune données à sauver");
            } else {
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setHeaderText("Choisissez un nom pour votre sauvegarde");
                textInputDialog.showAndWait();
                String nom;
                //Cas ou l'utilisateur ferme la fenêtre
                if (textInputDialog.getResult() == null) {
                    nom = "sauvegarde_Trebas";
                }
                //Teste si le resultat est vide
                if (textInputDialog.getResult().isEmpty()) {
                    nom = "sauvegarde_Trebas";
                }else{
                    nom = textInputDialog.getResult();
                }
                this.mod.sauvegarder(nom);
                popup.setContentText("Sauvegarde effectuée");
            }
            // Afficher la pop-up
            popup.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            popup.setContentText("Erreur, sauvegarde non effectuée");
            popup.show();
        }
    }
}
