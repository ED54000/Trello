package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import trello.composant.Liste;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant d'ajouter des listes
 */
public class ControleurAjoutListe implements EventHandler<ActionEvent> {
    //Attributs
    private ModeleTrello mod;
    private TextField txf;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     * @param txf le titre de la liste
     */
    public ControleurAjoutListe(ModeleTrello m, TextField txf) {
        this.mod= m;
        this.txf = txf;
    }

    /***
     * Méthode qui permet d'ajouter une liste
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        if (this.txf.getText().equals("")) {
            this.txf.setPromptText("Veuillez entrer un nom de colonne");
        } else {
            // Afin que le controleur fonctionne qu'il soit appliqué à un bouton ou un TextField
            //On recupère l'élément qui a déclenché l'événement
            Parent element = (Parent) actionEvent.getSource();
            //On recupère la HBox parent du bouton

            HBox parent = (HBox) element.getParent();
            HBox container = (HBox) element.getParent();
            // Teste si le message n'est pas trop long
            if (this.txf.getText().length() > Liste.MAX_LENGTH_TITLE) {
                //Envoie une erreur et ne modifie pas le message
                Alert alert = new Alert(Alert.AlertType.WARNING, "Trop long (max " + Liste.MAX_LENGTH_TITLE + " caractères)");
                alert.show();
                this.txf.setPromptText("Veuillez entrer un nom de colonne");
            } else {
                parent.getChildren().removeAll(element, this.txf);

                Liste l = new Liste(this.txf.getText(), this.mod.getTabSelectionne().getIdTableau());

                this.mod.ajouterListe(l);
            }
        }
    }
}