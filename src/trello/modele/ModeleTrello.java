package trello.modele;

import javafx.scene.control.Alert;
import trello.composant.Archive;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.composant.Tableau;
import trello.vue.Observateur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Cette classe est le cœur de l'application.
 * Elle implémente Serializable pour la sauvegarde et Sujet pour pouvoir notifier les observateurs
 */
public class ModeleTrello implements Sujet, Serializable {
    // Attributs

    /***
     * La liste des observateurs
     */
    private transient List<Observateur> observateurs;
    /**
     * La liste des tableaux que l'on peut créer
     */
    private List<Tableau> tableaux;
    /***
     * La classe qui gère les archives
     */
    private Archive archive;
    /***
     * Le tableau qui est sélectionné
     */
    private Tableau tabSelectionne;
    /**
     * La variable gérant le dark mode
     */
    public static boolean DARK_MODE = false;
    /***
     * La variable gérant l'affichage des tâches (vue bureau ou vue détaillée)
     */
    public static boolean VUE_TACHE = false;

    /***
     * Constructeur de la classe ModeleTrello
     */
    public ModeleTrello() {
        this.observateurs = new ArrayList<Observateur>();
        this.tableaux = new ArrayList<Tableau>();
        this.tabSelectionne = null;
        this.archive = new Archive();
    }

    /***
     *Méthode qui archive une tâche
     * @param t La tâche à archiver
     */
    public void archiverTache(ComposantTache t) {
        // Archive la tâche
        this.archive.archiverTache(t);
        // Marque la tâche comme archivée
        t.setIsArchive();
        // Retire la tâche de la liste
        ArrayList<Liste> l = this.getTabSelectionne().getListes();
        for (Liste list : l) {
            list.getTaches().remove(t);
        }
        // Notifie les obsrevateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui archive une liste.
     *
     * @param l La liste à archiver.
     */
    public void archiverListe(Liste l) {
        // Archive la liste
        this.archive.archiverListe(l);

        // Retire la liste du tableau sélectionné
        ArrayList<Liste> liste = this.getTabSelectionne().getListes();
        liste.remove(l);

        // Notifie les observateurs
        this.notifierObservateurs();
    }


    /**
     * Méthode qui désarchive une liste.
     *
     * @param l La liste à désarchiver.
     */
    public void desarchiverListe(Liste l) {
        // Désarchive la liste
        this.archive.desarchiverListe(l);

        // Sélectionne le tableau correspondant à la liste
        this.setSelectionnerTableau(l.getIdTableau());

        // Ajoute la liste désarchivée au tableau sélectionné
        this.tabSelectionne.getListes().add(l);

        // Notifie les observateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui désarchive une tâche.
     *
     * @param t La tâche à désarchiver.
     */
    public void desarchiverTache(ComposantTache t) {
        //Si on desarchive une tâche
        //Boolean pour savoir si la liste de la tâche existe, par défaut false
        boolean archivable = false;

        //On parcour l'ensemble des tableaux et des listes pour trouver la liste ou était la tâche
        for (Tableau tableau : this.getTableaux()) {
            for (Liste liste : tableau.getListes()) {
                if (liste.getId() == t.getId()) {
                    //Si on trouve la liste, on ajoute la tâche à la liste et on set le boolean à true
                    liste.ajouterTache(t);
                    archivable = true;
                }
            }
        }

        if (archivable) {
            //On desarchive la tâche (on l'enlève de l'archive)
            this.archive.desarchiverTache(t);
            t.setIsArchive();
        } else {
            //Si on ne trouve pas la liste, on affiche une erreur
            Alert alert = new Alert(Alert.AlertType.ERROR, "La tâche ne peut pas être désarchiver");
            alert.show();
        }
        this.notifierObservateurs();
    }

    /**
     * Méthode qui ajoute une liste au tableau sélectionné.
     *
     * @param l La liste à ajouter.
     */
    public void ajouterListe(Liste l) {
        // Ajoute la liste au tableau sélectionné
        this.tabSelectionne.getListes().add(l);

        // Notifie les observateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui ajoute un tableau à la liste des tableaux du projet.
     *
     * @param t Le tableau à ajouter.
     */
    public void ajouterTableau(Tableau t) {
        // Ajoute le tableau à la liste des tableaux du projet
        this.tableaux.add(t);

        // Notifie les observateurs
        this.notifierObservateurs();
    }


    /**
     * Méthode qui sélectionne un tableau spécifique.
     *
     * @param t Le tableau à sélectionner.
     */
    public void selectionnerTableau(Tableau t) {
        // Teste si la liste de tableau contient celui sélectionné
        if (this.tableaux.contains(t)) {
            this.tabSelectionne = this.tableaux.get(this.tableaux.indexOf(t));
        }
        // Notifie les observateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui ajoute un observateur au modèle.
     *
     * @param o L'observateur à ajouter.
     */
    @Override
    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    /**
     * Méthode qui supprime un observateur du modèle.
     *
     * @param o L'observateur à supprimer.
     */
    @Override
    public void supprimerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    /**
     * Méthode qui notifie tous les observateurs du changement dans le modèle.
     */
    @Override
    public void notifierObservateurs() {
        for (Observateur o : this.observateurs) {
            o.actualiser(this);
        }
    }

    /**
     * Obtient la liste des tableaux dans le modèle.
     *
     * @return La liste des tableaux.
     */
    public List<Tableau> getTableaux() {
        return tableaux;
    }


    /**
     * Obtient le tableau actuellement sélectionné dans le modèle.
     *
     * @return Le tableau actuellement sélectionné.
     */
    public Tableau getTabSelectionne() {
        return tabSelectionne;
    }

    /**
     * Définit le tableau actuellement sélectionné en fonction de son identifiant.
     *
     * @param id L'identifiant du tableau à sélectionner.
     */
    public void setSelectionnerTableau(int id) {
        for (Tableau t : this.tableaux) {
            if (t.getIdTableau() == id) {
                this.tabSelectionne = t;
            }
        }
    }

    /**
     * Obtient l'archive du modèle qui contient les listes et les tâches archivées.
     *
     * @return L'archive du modèle.
     */
    public Archive getArchive() {
        return this.archive;
    }

    /**
     * Active ou désactive le mode sombre de l'application.
     */
    public void setDarkMode() {
        DARK_MODE = !DARK_MODE;
    }

    /**
     * Retourne une représentation textuelle du modèle Trello
     *
     * @return Une chaîne représentant le modèle Trello.
     */
    @Override
    public String toString() {
        // Initialisation de la chaîne avec le titre du modèle
        String s = "ModeleTrello [\n";

        // Ajout des tableaux au modèle
        for (Tableau t : this.tableaux) {
            s += " - " + t.toString() + "\n";
        }

        // Ajout de la section d'archive au modèle
        s = s + "] \nArchive : \n";
        for (Liste l : this.archive.getListeArchive()) {
            s += l.getNom() + "\n";
            for (ComposantTache t : l.getTaches()) {
                s += " - " + t.getNom() + "\n";
            }
        }

        // Retourne la chaîne complète représentant le modèle Trello
        return s;
    }

    /**
     * Méthode qui sauvegarde le modèle dans un fichier binaire.
     *
     * @throws IOException S'il y a une erreur lors de l'écriture dans le fichier.
     */
    public void sauvegarder(String nom) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nom+".bin"));
        oos.writeObject(this);
    }

    /**
     * Méthode qui charge le modèle à partir d'un fichier binaire.
     *
     * @param f Le fichier à partir duquel charger le modèle.
     * @throws IOException            S'il y a une erreur lors de la lecture depuis le fichier.
     * @throws ClassNotFoundException Si la classe du modèle n'est pas trouvée lors de la désérialisation.
     */
    public void charger(File f) throws IOException, ClassNotFoundException {
        if (f != null) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.getName()));
            ModeleTrello modeleCharge = (ModeleTrello) ois.readObject();
            this.tableaux = modeleCharge.getTableaux();
            this.archive = modeleCharge.getArchive();
            this.tabSelectionne = modeleCharge.getTabSelectionne();
            this.notifierObservateurs();
        }
    }

}

