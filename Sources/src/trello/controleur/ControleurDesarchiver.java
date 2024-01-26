package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.modele.ModeleTrello;

/***
 * Controleur permettant de désarchiver les tâches ou des listes
 */
public class ControleurDesarchiver implements EventHandler<ActionEvent> {
    // Attributs
    private Liste liste;
    private ComposantTache tache;
    private ModeleTrello mod;

    /***
     * Constructeur du controleur dans le cas ou une liste est archivée
     * @param m le modèle utilisé pour notifier les observateurs
     * @param l la liste archivée
     */
    public ControleurDesarchiver(ModeleTrello m, Liste l) {
        this.mod = m;
        this.liste = l;
    }

    /***
     * Constructeur du controleur dans le cas ou une tâche est archivée
     * @param m le modèle utilisé pour notifier les observateurs
     * @param t la tâche archivée
     */
    public ControleurDesarchiver(ModeleTrello m, ComposantTache t) {
        this.mod = m;
        this.tache = t;
    }

    /**
     * Méthode qui permet de désarchiver une tâche ou une liste.
     *
     * @param actionEvent événement qui a déclenché le contrôleur.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        //Si on desarchive une liste
        if (this.liste != null) {
            this.mod.desarchiverListe(this.liste);
        } else if (this.tache != null) {
            this.mod.desarchiverTache(this.tache);
        }
    }
}
