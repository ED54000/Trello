package trello.composant;

import javafx.scene.control.TreeItem;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * La classe abstraite ComposantTache qui est la base d'une tâche.
 * Elle implémente l'interface Serializable pour permettre la sérialisation.
 * Elle contient les méthodes communes à une tâche mère et feuille.
 */
public abstract class ComposantTache implements Serializable {

    /**
     * Constantes pour les dimensions minimales d'une tâche
     */
    public static final int MIN_HEIGHT = 25;
    /**
     * Constantes pour le nombre de caractères maximum du titre d'une tâche
     */
    public static final int MAX_LENGTH_TITLE = 255;

    /**
     * Le nom de la tâche
     */
    private String nom;
    /**
     * L'id de la liste où se trouve la tâche
     */
    private int id_liste;
    /**
     * La tâche parente
     */
    private ComposantTache parent;
    /**
     * La date de début et de la fin de la tâche
     */
    private LocalDate dateDebut, dateFin;
    /**
     * Un booléen pour savoir si la tâche est urgente
     */
    private boolean isUrgent;
    /**
     * Un booléen pour savoir si la tâche est réduite
     */
    public boolean TACHE_REDUITE = false;
    /**
     * Un booléen pour savoir si la tâche est archivée
     */
    private boolean isArchive;

    /**
     * Constructeur de la classe ComposantTache
     *
     * @param n     le nom de la tâche
     * @param id    l'id de la liste où se trouve la tâche
     * @param debut la date de début de la tâche
     * @param fin   la date de fin de la tâche
     */
    public ComposantTache(String n, int id, LocalDate debut, LocalDate fin) {
        this.id_liste = id;
        this.nom = n;
        this.dateDebut = debut;
        this.dateFin = fin;
        this.isUrgent = false;
        this.isArchive = false;
    }

    /**
     * Méthode pour récupérer le nom de la tâche
     *
     * @return le nom de la tâche
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Méthode pour récupérer l'id de la liste où se trouve la tâche
     *
     * @return l'id de la liste où se trouve la tâche
     */
    public int getId() {
        return this.id_liste;
    }

    /**
     * Méthode pour savoir si la tâche est urgente
     *
     * @return un booléen pour savoir si la tâche est urgente
     */
    public boolean isUrgent() {
        return this.isUrgent;
    }

    /**
     * Méthode pour savoir si la tâche est archivée
     *
     * @return un booléen pour savoir si la tâche est archivée
     */
    public boolean isArchive() {
        return this.isArchive;
    }

    /**
     * Méthode pour récupérer la tâche parente
     *
     * @return la tâche parente
     */
    public ComposantTache getParentTache() {
        return parent;
    }

    /**
     * Méthode pour récupérer la date de début de la tâche
     *
     * @return la date de début de la tâche
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Méthode pour récupérer la date de fin de la tâche
     *
     * @return la date de fin de la tâche
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * Méthode pour récupérer le nombre de jours entre la date de début et la date de fin de la tâche
     *
     * @return le nombre de jours entre la date de début et la date de fin de la tâche
     */
   public long getNbJour() {
       return ChronoUnit.DAYS.between(dateDebut,dateFin);
      //  return this.dateDebut.until(this.dateFin).getDays();
    }

    /**
     * Méthode pour récupérer la date de début de la tâche au format String
     *
     * @return la date de début de la tâche au format String
     */
    public String getDateDebutString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateDebut.format(formatter);
    }

    /**
     * Méthode pour récupérer la date de fin de la tâche au format String
     *
     * @return la date de fin de la tâche au format String
     */
    public String getDateFinString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateFin.format(formatter);
    }

    /**
     * Méthode pour modifier le nom de la tâche
     *
     * @param nom le nouveau nom de la tâche
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Méthode pour modifier l'id de la liste où se trouve la tâche
     *
     * @param id le nouvel id de la liste où se trouve la tâche
     */
    public void setId(int id) {
        this.id_liste = id;
    }

    /**
     * Méthode pour modifier l'état d'urgence de la tâche
     */
    public void setIsUrgent() {
        this.isUrgent = !this.isUrgent;
    }

    /**
     * Méthode pour l'état d'archivage de la tâche
     */
    public void setIsArchive() {
        this.isArchive = !this.isArchive;
    }

    /**
     * Méthode pour modifier la tâche parente
     *
     * @param parent la nouvelle tâche parente
     */
    public void setParent(ComposantTache parent) {
        this.parent = parent;
    }

    /**
     * Méthode pour modifier la date de début de la tâche
     *
     * @param dateDebut la nouvelle date de début de la tâche
     */
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Méthode pour modifier la date de fin de la tâche
     *
     * @param dateFin la nouvelle date de fin de la tâche
     */
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Méthode pour ajouter une tâche à une tâche mère
     *
     * @param tache la tâche à ajouter
     * @return un booléen pour savoir si la tâche a été ajoutée
     */
    public abstract boolean ajouterSousTache(ComposantTache tache);

    /**
     * Méthode générer l'arbre des tâches grâce à la classe TreeItem
     *
     * @return la racine de l'arbre qui contient toutes les autres tâches
     */
    public TreeItem<ComposantTache> genererArbreTache() {
        TreeItem<ComposantTache> tRacine = new TreeItem<>(this);
        if (this instanceof TacheMere) {
            for (ComposantTache t : ((TacheMere) this).getSousTaches()) {
                TreeItem<ComposantTache> tache = t.genererArbreTache();
                tRacine.getChildren().add(tache);
            }
        }
        return tRacine;
    }

    /**
     * Méthode equals pour comparer deux tâches
     *
     * @param obj la tâche à comparer
     * @return un booléen pour savoir si les deux tâches sont égales
     */
    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj instanceof ComposantTache) {
            ComposantTache t = (ComposantTache) obj;
            if (this.nom.equals(((ComposantTache) obj).getNom())) {
                res = true;
            }
        }
        return res;
    }


}
