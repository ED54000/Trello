package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import trello.modele.ModeleTrello;
import static trello.Main.scene;

/***
 * Contrôleur permettant de passer du light-mode au dark-mode et inversement
 */
public class ControleurDarkMode implements EventHandler<ActionEvent> {
    //Attribut
    private ModeleTrello mod;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     */
    public ControleurDarkMode(ModeleTrello m) {
        this.mod = m;
    }

    /***
     * Méthode permettant de change de mode d'affichage
     * On enlève la feuille de style présente, et on ajoute l'autre
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        this.mod.setDarkMode();
        if (ModeleTrello.DARK_MODE){
            // Cas ou l'affichage est en light mode
            scene.setFill(Color.rgb(40, 41, 42));
            scene.getStylesheets().clear();
            scene.getStylesheets().add("darkmode.css");
        } else {
            // Cas ou l'affichage est en dark mode
            scene.setFill(Color.rgb(255, 255, 255));
            scene.getStylesheets().clear();
            scene.getStylesheets().add("lightmode.css");
        }
        this.mod.notifierObservateurs();
    }
}
