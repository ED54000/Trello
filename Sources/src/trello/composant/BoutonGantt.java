package trello.composant;

import javafx.scene.control.Button;

/***
 * Classe BoutonGantt permet de créer un bouton unique
 */
public class BoutonGantt extends Button {
    // Instance unique de la classe BoutonGantt
    private static BoutonGantt instance;

    /***
     * Constructeur privé pour empêcher l'instanciation directe depuis l'extérieur de la classe
     */
    private BoutonGantt() {
        // Initialisation du bouton Gantt
        super("Diagramme de Gantt");
        // Autres initialisations si nécessaires
    }

    /***
     * Méthode pour obtenir l'instance unique de BoutonGantt
     */
    public static BoutonGantt getInstance() {
        // Crée l'instance si elle n'existe pas encore
        if (instance == null) {
            instance = new BoutonGantt();
        }
        return instance;
    }

}
