package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import trello.composant.Liste;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant de modifier les couleurs d'une liste
 */
public class ControleurModifierCouleur implements EventHandler<ActionEvent> {

    //Attributs
    private ModeleTrello mod;
    private Liste liste;
    private String color;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     * @param l la liste dont on modifie la couleur
     * @param color la couleur que l'on applique
     */
    public ControleurModifierCouleur(ModeleTrello m, Liste l, String color) {
        this.mod = m;
        this.liste = l;
        this.color = color;
    }

    /***
     * Méthode permettant de changer la couleur d'une liste
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        liste.setColor(this.color);
        mod.notifierObservateurs();
    }
}
