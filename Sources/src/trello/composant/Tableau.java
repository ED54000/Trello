package trello.composant;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * La classe Tableau qui représente un tableau.
 * Elle implémente l'interface Serializable pour permettre la sérialisation.
 */
public class Tableau implements Serializable {

    /**
     * Constantes pour la largeur minimales d'un tableau
     */
    public final static int MIN_WIDTH = 50;
    /**
     * Constantes pour la largeur maximales d'un tableau
     */
    public final static int MAX_WIDTH = 500;
    /**
     * Constantes pour la hauteur minimales d'un tableau
     */
    public final static int MIN_HEIGHT = 500;
    /**
     * Id du tableau
     */
    private int idTableau;
    /**
     * Le nom du tableau
     */
    private String nomTableau;
    /**
     * La liste des listes du tableau
     */
    private ArrayList<Liste> listes;
    /**
     * Le nombre de tableaux total
     */
    public static int nbTableaux = 0;
    /**
     * La date minimal du tableau (date de début la plus petite)
     */
    private LocalDate dateMin;
    /**
     * La date maximal du tableau (date de fin la plus grande)
     */
    private LocalDate dateMax;

    /**
     * Constructeur de la classe Tableau pour les tests
     *
     * @param nomTableau le nom du tableau
     */
    public Tableau(String nomTableau) {
        this.nomTableau = nomTableau;
        this.listes = new ArrayList<Liste>();
        this.dateMax = null;
        this.dateMin = null;
    }

    /**
     * Constructeur de la classe Tableau pour le programme
     *
     * @param nomTableau le nom du tableau
     * @param b pour savoir si le tableau est utilisé pour les tests ou non
     */
    public Tableau(String nomTableau, boolean b) {
        this.nomTableau = nomTableau;
        this.idTableau = ++nbTableaux;
        this.listes = new ArrayList<Liste>();
        this.dateMin = null;
        this.dateMax = null;
    }

    /**
     * Méthode pour ajouter une liste à la liste des listes du tableau
     *
     * @param l la liste à ajouter
     */
    public void ajouterListe(Liste l) {
        this.listes.add(l);
    }

    /**
     * Méthode setter pour l'id du tableau
     *
     * @param id l'id du tableau
     */
    public void setIdTableau(int id) {
        this.idTableau = id;
    }

    /**
     * Méthode getter pour l'id du tableau
     *
     * @return l'id du tableau
     */
    public int getIdTableau() {
        return this.idTableau;
    }

    /**
     * Méthode getter pour le nom du tableau
     *
     * @return le nom du tableau
     */
    public String getNomTableau() {
        return nomTableau;
    }

    /**
     * Méthode getter pour la liste des listes du tableau
     *
     * @return la liste des listes du tableau
     */
    public ArrayList<Liste> getListes() {
        return listes;
    }

    /**
     * Méthode getter pour l'ecart entre la date de début la plus petite et la date de début en paramètre
     *
     * @return l'ecart entre la date de début la plus petite et la date de début en paramètre
     */
    public int getEcartDebut(LocalDate date) {
        return this.dateMin.until(date).getDays();
    }

    /**
     * Méthode getter pour l'ecart entre la date de fin la plus grande et la date de fin en paramètre
     *
     * @return l'ecart entre la date de fin la plus grande et la date de fin en paramètre
     */
    public int getEcartFin(LocalDate date) {
        return date.until(this.dateMax).getDays();
    }

    /**
     * Méthode pour récupérer la date minimal du tableau sous forme de String (date de début la plus petite)
     *
     * @return la date minimal du tableau (date de début la plus petite)
     */
    public String getDateMinString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.getDateMin().format(formatter);
    }

    /**
     * Méthode pour récupérer la date maximal du tableau sous forme de String (date de fin la plus grande)
     *
     * @return la date maximal du tableau (date de fin la plus grande)
     */
    public String getDateMaxString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.getDateMax().format(formatter);
    }

    /**
     * Méthode pour récupérer la date minimal du tableau (date de début la plus petite)
     *
     * @return la date minimal du tableau (date de début la plus petite)
     */
    public LocalDate getDateMin() {
        LocalDate dateMin = this.dateMin;
        if (!this.listes.isEmpty()) {
            dateMin = this.listes.get(0).getDateMin();
            for (Liste l : this.listes) {
                if (dateMin.isAfter(l.getDateMin())) {
                    dateMin = l.getDateMin();
                }
            }
        }
        return dateMin;
    }

    /**
     * Méthode pour récupérer la date maximal du tableau (date de fin la plus grande)
     *
     * @return la date maximal du tableau (date de fin la plus grande)
     */
    public LocalDate getDateMax() {
        LocalDate dateMax = this.dateMax;
        if (!this.listes.isEmpty()) {
            dateMax = this.listes.get(0).getDateMax();
            for (Liste l : this.listes) {
                if (dateMax.isBefore(l.getDateMax())) {
                    dateMax = l.getDateMax();
                }
            }
        }
        return dateMax;
    }

    /**
     * Méthode qui permet de set la date minimal et la date maximal du tableau en fonction de la date en paramètre
     * @param date la date à comparer
     */
    public void checkDateMinAndMax(LocalDate date) {
        // A l'initialisation, dateMin et dateMax sont séparées de 1 jour
        if (this.dateMin == null && this.dateMax == null) {
            this.dateMin = date;
            // Pour que la dateMax soit différente on l'incrémente de 1 jour
            date.plus(1, ChronoUnit.DAYS);
            this.dateMax = date;
        }
        // Si aucune des deux dates est null
        else {
            if (date.isBefore(this.getDateMax())) {
                this.dateMax = date;
            }
            else if (date.isAfter(this.getDateMin())) {
                this.dateMin = date;
            }
        }
    }

    /**
     * Méthode equals pour comparer deux tableaux
     *
     * @param obj le tableau à comparer
     * @return true si les deux tableaux sont égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        boolean egal = false;
        if (obj instanceof Tableau) {
            Tableau t = (Tableau) obj;
            egal = this.nomTableau.equals(t.getNomTableau());
        }
        return egal;
    }

    /**
     * Méthode toString pour afficher un tableau
     *
     * @return le tableau sous forme de String
     */
    public String toString() {
        String s = this.nomTableau + " " + this.getIdTableau() + "\n";
        for (Liste l : this.listes) {
            s += "      * " + l.getNom() + "\n";
        }
        return s;
    }
}
