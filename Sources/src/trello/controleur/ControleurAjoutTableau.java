package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import trello.modele.ModeleTrello;
import trello.composant.Tableau;

/***
 * Contrôleur permettant d'ajouter un tableau
 */
public class ControleurAjoutTableau implements EventHandler<ActionEvent> {
//Attribut
    private ModeleTrello mod;

    /**
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     */
    public ControleurAjoutTableau(ModeleTrello m) {
        this.mod = m;
    }

    /***
     * Méthode qui permet d'ajouter un tableau
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        TextField txf = (TextField) actionEvent.getSource();
        String nomTableau = txf.getText();
        if (!nomTableau.equals("") && !this.mod.getTableaux().contains(new Tableau(nomTableau))) {
            Tableau t = new Tableau(nomTableau, true);
            VBox parent = (VBox) txf.getParent();
            parent.getChildren().remove(txf);
            this.mod.ajouterTableau(t);
            txf.setText("");
        }
    }
}
