package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import trello.composant.ComposantTache;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant de mettre une tâche en urgent
 */
public class ControleurUrgent implements EventHandler<ActionEvent> {
    //Attributs
    private ModeleTrello mod;

    private ComposantTache t;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     * @param t la tâche à mettre en urgent
     */
    public ControleurUrgent(ModeleTrello m, ComposantTache t) {
        this.mod = m;
        this.t = t;
    }

    /***
     * Méthode permettant de mettre une tâche en urgent
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        t.setIsUrgent();
        this.mod.notifierObservateurs();
    }
}
