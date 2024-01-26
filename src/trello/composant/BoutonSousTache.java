package trello.composant;

import javafx.scene.control.Button;

/**
 * La classe BoutonSousTache permet de créer un bouton pour ajouter une sous-tâche à une tâche mère.
 */
public class BoutonSousTache extends Button {

    /**
     * La tâche mère
     */
    private ComposantTache composantTache;

    /**
     * La liste parente
     */
    private Liste listeParent;

    /**
     * Constructeur de la classe BoutonSousTache
     *
     * @param titre le titre du bouton
     * @param l     la liste parente
     */
    public BoutonSousTache(String titre, Liste l) {
        super(titre);
        this.listeParent = l;
    }

    /**
     * Méthode pour récupérer la liste parente
     *
     * @return la liste parente
     */
    public Liste getListe() {
        return listeParent;
    }
}
