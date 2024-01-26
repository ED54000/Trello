package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import trello.modele.ModeleTrello;
import trello.composant.Tableau;

/***
 * Contrôleur permettant de sélectionner un tableau
 */
public class ControleurSelectionneTableau implements EventHandler<ActionEvent> {
    //Attributs
    private ModeleTrello mod;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     */
    public ControleurSelectionneTableau(ModeleTrello m) {
        this.mod = m;
    }

    /***
     * Méthode permettant de sélectionner un tableau
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        String nomTableau = b.getText();
        this.mod.selectionnerTableau(new Tableau(nomTableau));
    }
}
