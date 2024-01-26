package trello.composant;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Liste représente une liste de tâches.
 * Elle implémente l'interface Serializable pour permettre la sérialisation.
 */
public class Liste implements Serializable {

    /**
     * Constantes pour la largeur minimales d'une liste
     */
    public static final int MIN_WIDTH = 200;

    /**
     * Constantes pour la hauteur minimales d'une liste
     */
    public static final int MIN_HEIGHT = 400;
    /**
     * Constantes pour le nombre de caractères maximum du titre d'une liste
     */
    public static final int MAX_LENGTH_TITLE = 20;
    /**
     * Un booléen pour savoir si la liste est masquée
     */
    public boolean LISTE_REDUITE = true;

    /**
     * Le nom de la liste
     */
    private String nom;
    /**
     * L'id de la liste
     */
    private int id;
    /**
     * L'id du tableau où se trouve la liste
     */
    private int id_tableau;
    /**
     * La couleur de fond de la liste
     */
    private String color;
    /**
     * La liste des tâches de la liste
     */
    private List<ComposantTache> taches;
    /**
     * Le nombre de listes total
     */
    public static int nbListes = 0;

    /**
     * Constructeur de la classe Liste
     *
     * @param n      le nom de la liste
     * @param id_tab l'id du tableau où se trouve la liste
     */
    public Liste(String n, int id_tab) {
        this.nom = n;
        this.taches = new ArrayList<ComposantTache>();
        this.id = ++nbListes;
        this.color = "#E2E4E6";
        this.id_tableau = id_tab;
    }

    /**
     * Méthode pour ajouter une tâche à la liste
     *
     * @param tache la tâche à ajouter
     */
    public void ajouterTache(ComposantTache tache) {
        this.taches.add(tache);
    }

    /**
     * Méthode pour supprimer une tâche de la liste
     *
     * @param tache la tâche à supprimer
     */
    public void supprimerTache(ComposantTache tache) {
        this.taches.remove(tache);
    }

    /**
     * Méthode pour récupérer le nom de la liste
     *
     * @return le nom de la liste
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode pour récupérer la couleur de la liste
     *
     * @return la couleur de la liste
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Méthode pour récupérer l'id de la liste
     *
     * @return l'id de la liste
     */
    public int getId() {
        return id;
    }

    /**
     * Méthode pour récupérer l'id du tableau où se trouve la liste
     *
     * @return l'id du tableau où se trouve la liste
     */
    public int getIdTableau() {
        return this.id_tableau;
    }

    /**
     * Méthode pour récupérer la liste des tâches de la liste
     *
     * @return la liste des tâches de la liste
     */
    public List<ComposantTache> getTaches() {
        return taches;
    }

    /**
     * Méthode pour modifier le nom de la liste
     *
     * @param nom le nouveau nom de la liste
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Méthode pour modifier l'id de la liste
     *
     * @param id le nouvel id de la liste
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode pour modifier la couleur de fond de la liste
     *
     * @param color la nouvelle couleur de fond de la liste
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Méthode qui calcule la date maximale de la liste
     * @return la date maximale de la liste
     */
    public LocalDate getDateMax() {
        LocalDate dateMax = LocalDate.now();
        if (!this.taches.isEmpty()) {
            dateMax = this.taches.get(0).getDateFin();
            for (ComposantTache t : this.taches) {
                if (dateMax.isBefore(t.getDateFin())) {
                    dateMax = t.getDateFin();
                }
            }
        }
        return dateMax;
    }

    /**
     * Méthode qui calcule la date minimale de la liste
     * @return la date minimale de la liste
     */
    public LocalDate getDateMin() {
        LocalDate dateMin = LocalDate.now();
        if (!this.taches.isEmpty()) {
            dateMin = this.taches.get(0).getDateDebut();
            for (ComposantTache t : this.taches) {
                if (dateMin.isAfter(t.getDateDebut())) {
                    dateMin = t.getDateDebut();
                }
            }
        }
        return dateMin;
    }

}
