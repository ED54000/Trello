package trello.composant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TacheMere permet de créer une tâche mère qui a des sous-tâches.
 */
public class TacheMere extends ComposantTache {

    /**
     * La liste des sous-tâches
     */
    protected List<ComposantTache> sousTaches;

    /**
     * Constructeur de la classe TacheMere
     *
     * @param n     le nom de la tâche
     * @param id    l'id de la liste où se trouve la tâche
     * @param debut la date de début de la tâche
     * @param fin   la date de fin de la tâche
     */
    public TacheMere(String n, int id, LocalDate debut, LocalDate fin) {
        super(n, id, debut, fin);
        this.sousTaches = new ArrayList<ComposantTache>();
    }

    /**
     * Méthode pour récupérer la liste des sous-tâches
     *
     * @return la liste des sous-tâches
     */
    public List<ComposantTache> getSousTaches() {
        return sousTaches;
    }

    /**
     * Méthode pour ajouter une sous-tâche
     *
     * @param tache la sous-tâche à ajouter
     * @return un booléen pour savoir si la sous-tâche a été ajoutée
     */
    @Override
    public boolean ajouterSousTache(ComposantTache tache) {
        return sousTaches.add(tache);
    }

}
