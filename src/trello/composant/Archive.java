package trello.composant;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe Archive représente l'archive des listes et des tâches archivées.
 * Elle implémente l'interface Serializable pour permettre la sérialisation.
 */
public class Archive implements Serializable {

    /**
     * Une liste pour les tâches archivées
     */
    private Liste tacheArchive;
    /**
     * Une liste pour les listes archivées
     */
    private ArrayList<Liste> listeArchive;

    /**
     * Constructeur de la classe Archive
     */
    public Archive() {
        this.listeArchive = new ArrayList<Liste>();
        tacheArchive = new Liste("Tâche archiver", -999);
        tacheArchive.setColor("rgb(125, 125, 125)");
        this.listeArchive.add(tacheArchive);
    }

    /**
     * Méthode pour ajouter une liste à la liste des listes archivées
     *
     * @param l la liste à ajouter
     */
    public void archiverListe(Liste l) {
        this.listeArchive.add(l);
    }

    /**
     * Méthode pour ajouter une tache à la liste des tâches archivées
     *
     * @param t la tâche à ajouter
     */
    public void archiverTache(ComposantTache t) {
        this.tacheArchive.ajouterTache(t);
    }

    /**
     * Méthode pour retirer une liste à la liste des listes archivées
     *
     * @param l la liste à retirer
     */
    public void desarchiverListe(Liste l) {
        this.listeArchive.remove(l);
    }

    /**
     * Méthode pour retirer une tâche à la liste des tâches archivées
     *
     * @param t la tâche à retirer
     */
    public void desarchiverTache(ComposantTache t) {
        this.tacheArchive.supprimerTache(t);
    }

    /**
     * Méthode pour récuperer la liste des listes archivées
     *
     * @return La liste des listes archivées
     */
    public ArrayList<Liste> getListeArchive() {
        return this.listeArchive;
    }

}
