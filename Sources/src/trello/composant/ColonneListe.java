package trello.composant;

import javafx.scene.layout.VBox;

/**
 * La classe ColonneListe permet de créer une colonne pour une liste.
 */
public class ColonneListe extends VBox {

    /**
     * La liste
     */
    private Liste liste;

    /**
     * Constructeur de la classe ColonneListe
     *
     * @param l la liste
     */
    public ColonneListe(Liste l) {
        this.liste = l;
    }

    /**
     * Méthode pour récupérer la liste
     *
     * @return la liste
     */
    public Liste getListe() {
        return this.liste;
    }
}
