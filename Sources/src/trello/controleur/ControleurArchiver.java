package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant d'archiver les tâches ou des listes
 */
public class ControleurArchiver implements EventHandler<ActionEvent> {

    // Attributs
    private Liste liste;
    private ComposantTache tache;
    private ModeleTrello mod;

    /***
     * Constructeur du contrôleur dans le cas ou une liste est archivée
     * @param m le modèle utilisé pour notifier les observateurs
     * @param l la liste archivée
     */
    public ControleurArchiver(ModeleTrello m, Liste l) {
        this.mod = m;
        this.liste = l;
    }

    /***
     * Constructeur du contrôleur dans le cas ou une tâche est archivée
     * @param m le modèle utilisé pour notifier les observateurs
     * @param t la tâche archivée
     */
    public ControleurArchiver(ModeleTrello m, ComposantTache t) {
        this.mod = m;
        this.tache = t;
    }

    /**
     * Méthode qui permet d'archiver uen tâche ou une liste
     *
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Cas ou il s'agit d'une liste
        if (this.liste != null) {
            this.mod.archiverListe(this.liste);
        } else if (this.tache != null) {
            // Cas ou il s'agit d'une tâche
            this.mod.archiverTache(this.tache);
        }
    }
}
