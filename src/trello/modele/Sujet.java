package trello.modele;

import trello.vue.Observateur;

/**
 * Interface utilisée pour les observateurs (MVC)
 */
public interface Sujet {

    /**
     * Ajoute un observateur à la liste des observateurs.
     *
     * @param o l'observateur à ajouter
     */
    public void ajouterObservateur(Observateur o);

    /**
     * Supprime un observateur de la liste des observateurs.
     *
     * @param o l'observateur à supprimer
     */
    public void supprimerObservateur(Observateur o);

    /**
     * Notifie tous les observateurs enregistrés d'un changement dans l'état du sujet.
     */
    public void notifierObservateurs();
}