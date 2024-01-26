package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.modele.ModeleTrello;

/***
 * Controleur permettant de renommer une tâche ou une liste
 */
public class ControleurRenommer implements EventHandler<ActionEvent> {

    // Attributs
    private ModeleTrello mod;
    private ComposantTache composantTache;
    private Liste liste;

    /***
     * Constructeur du contrôleur dans le cas ou l'élément à renommer est une tâche
     * @param m le modèle utilisé pour notifier les observateurs
     * @param ct la tâche qui va être renommée
     */
    public ControleurRenommer(ModeleTrello m, ComposantTache ct) {
        this.mod = m;
        this.composantTache = ct;
    }

    /***
     * Constructeur du contrôleur dans le cas ou l'élément à renommer est une liste
     * @param m le modèle utilisé pour notifier les observateurs
     * @param l la liste qui va être renommée
     */
    public ControleurRenommer(ModeleTrello m, Liste l) {
        this.mod = m;
        this.liste = l;
    }

    /***
     * Méthode appelée lors de l'appui du bouton et qui renomme la tâche ou la liste en attribut
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        //Créer un pop-up avec un champ remplissable
        TextInputDialog textInputDialog = new TextInputDialog("");

        //Test de l'élement modifié
        if (liste == null) {
            // Cas Tâche
            //Affichage du pop-up avec le message adapté
            textInputDialog.setHeaderText("Choisissez un nouveau nom pour votre tâche");
            textInputDialog.showAndWait();
            //Cas ou l'utilisateur ferme la fenêtre
            if (textInputDialog.getResult() == null) {
                return;
            }
            //Teste si le resultat est vide
            if (textInputDialog.getResult().isEmpty()) {
                //Envoie une pop-up d'erreur et ne modifie pas le nom
                Alert alert = new Alert(Alert.AlertType.ERROR, "La tâche ne peut pas avoir une nom vide");
                alert.show();
                return;
            } else {
                //Teste si le résultat est plus long que le nombre max
                if (textInputDialog.getResult().length() > ComposantTache.MAX_LENGTH_TITLE) {
                    //Envoie une erreur
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Trop long (max " + ComposantTache.MAX_LENGTH_TITLE + " caractères)");
                    alert.show();
                    //Ne change pas le nom
                    return;
                }
                //Change le nom
                this.composantTache.setNom(textInputDialog.getResult());
            }
        } else {
            // Cas liste
            textInputDialog.setHeaderText("Choisissez un nouveau nom pour votre liste");
            textInputDialog.showAndWait();
            //Cas ou l'utilisateur ferme la fenêtre
            if (textInputDialog.getResult() == null) {
                return;
            }
            //Teste si le résultat n'est pas vide
            if (textInputDialog.getResult().isEmpty()) {
                // Envoie une erreur et ne modifie pas le message
                Alert alert = new Alert(Alert.AlertType.ERROR, "Le titre ne peut pas être vide");
                alert.show();
                return;
            } else {
                // Teste si le message n'est pas trop long
                if (textInputDialog.getResult().length() > Liste.MAX_LENGTH_TITLE) {
                    //Envoie une erreur et ne modifie pas le message
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Trop long (max " + Liste.MAX_LENGTH_TITLE + " caractères)");
                    alert.show();
                    return;
                }
                //Modifie le message
                this.liste.setNom(textInputDialog.getResult());
            }

        }
        //Notifie tous les observateurs
        this.mod.notifierObservateurs();
    }
}
