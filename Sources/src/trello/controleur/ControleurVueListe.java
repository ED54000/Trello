package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant de passer de l'affichage détaillé à l'affichage en bureau (et inversement)
 */
public class ControleurVueListe implements EventHandler<ActionEvent> {
    //Attribut
    private ModeleTrello mod;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     */
    public ControleurVueListe(ModeleTrello m) {
        this.mod = m;
    }

    /***
     * Méthode permettant de passer de l'affichage détaillé à l'affichage en bureau (et inversement)
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        ModeleTrello.VUE_TACHE = !ModeleTrello.VUE_TACHE;
        this.mod.notifierObservateurs();
    }
}
