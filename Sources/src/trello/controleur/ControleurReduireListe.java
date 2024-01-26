package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import trello.composant.Liste;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant de masquer les listes
 */
public class ControleurReduireListe implements EventHandler<ActionEvent> {
    // Attributs
    private ModeleTrello mod;
    private Liste liste;


    /***
     * Constructeur permettant de créer le contrôleur
     * @param m le modèle utilisé pour pouvoir notifier les observateurs
     * @param l la liste a masquer
     */
    public ControleurReduireListe(ModeleTrello m, Liste l) {
        this.mod = m;
        this.liste = l;
    }

    /***
     * Méthode permettant de masquer une liste
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // passer d'un état à un autre
        this.liste.LISTE_REDUITE = !this.liste.LISTE_REDUITE;
        this.mod.notifierObservateurs();
    }
}

