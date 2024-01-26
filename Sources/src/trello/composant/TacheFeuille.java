package trello.composant;

import java.time.LocalDate;

/**
 * La classe TacheFeuille permet de créer une tâche feuille qui n'a pas de sous-tâche.
 */
public class TacheFeuille extends ComposantTache {

    /**
     * Constructeur de la classe TacheFeuille
     *
     * @param n     le nom de la tâche
     * @param id    l'id de la liste où se trouve la tâche
     * @param debut la date de début de la tâche
     * @param fin   la date de fin de la tâche
     */
    public TacheFeuille(String n, int id, LocalDate debut, LocalDate fin) {
        super(n, id, debut, fin);
    }

    /**
     * Méthode pour ajouter une sous-tâche
     *
     * @param tache la sous-tâche à ajouter
     * @return un booléen pour savoir si la sous-tâche a été ajoutée
     */
    @Override
    public boolean ajouterSousTache(ComposantTache tache) {
        return false;
    }
}
