package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import trello.composant.ComposantTache;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant de masquer les tâches
 */
public class ControleurReduireTache implements EventHandler<ActionEvent> {
  //Attributs
    private ModeleTrello mod;
    private ComposantTache tache;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     * @param t la tâche à masquer
     */
    public ControleurReduireTache(ModeleTrello m, ComposantTache t) {
        this.mod = m;
        this.tache = t;
    }

    /***
     *  Méthode permettant de masquer les tâches
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // passer d'un état à un autre
        this.tache.TACHE_REDUITE =!this.tache.TACHE_REDUITE;
        this.mod.notifierObservateurs();
    }
}
