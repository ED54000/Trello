package trello.vue;

import trello.modele.Sujet;

/**
 * Interface d'Observateur, qui fait partie du patron de conception Observateur
 * permettant une facilité du modèle MVC, les observateurs gèrent les vues
 * et sont actualiser par le modèle en appelant l'ensemble de ses observateurs.
 */

public interface Observateur {

    /**
     * Méthode actualiser, actualise l'interface graphique de la classe implémenter et ajouter au modèle.
     * @param s Modèle stockant les informations à afficher et actualiser.
     */
    public void actualiser(Sujet s);
}
