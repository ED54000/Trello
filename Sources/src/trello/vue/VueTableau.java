package trello.vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import trello.composant.BoutonGantt;
import trello.modele.ModeleTrello;
import trello.modele.Sujet;
import trello.composant.Tableau;
import trello.controleur.*;

/**
 * Classe Vue Tableau qui est observateur et aussi VBox
 * Cette classe permet d'actualiser la partie de gestions des tableaux (partie gauche de l'application)
 */
public class VueTableau extends VBox implements Observateur {

    public VueTableau() {
        super();
    }

    /**
     * Méthode qui génère la vue des différents tableaux créés par l'utilisateur
     * @param s Modèle stockant les informations à afficher et actualiser.
     */
    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();
        ModeleTrello mod = (ModeleTrello) s;
        Label titre = new Label("TABLEAUX");
        titre.getStyleClass().add("titre");
        titre.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");
        this.getChildren().add(titre);
        this.setAlignment(Pos.TOP_CENTER);

        for (Tableau t : mod.getTableaux()) {
            Button tabCourant = new Button(t.getNomTableau());
            tabCourant.getStyleClass().add("bouton");
            tabCourant.setMinWidth(Tableau.MIN_WIDTH);
            tabCourant.setMaxWidth(Tableau.MAX_WIDTH);
            tabCourant.setOnAction(new ControleurSelectionneTableau(mod));
            this.getChildren().add(tabCourant);
        }

        TextField nomTableau = new TextField(); // Permet d'entrer le nom d'un nouveau tableau
        nomTableau.setPromptText("Entrez le nom du tableau");
        nomTableau.setOnAction(new ControleurAjoutTableau(mod)); // Controleur qui créer le tableau
        nomTableau.getStyleClass().add("textfield");

        Pane espace = new Pane();
        espace.setMinWidth(Tableau.MIN_WIDTH);
        espace.setMaxWidth(Tableau.MAX_WIDTH);
        espace.setId("bar");

        Button sauvegarder = new Button("Sauvegarder"); // Permet de sauvegarder le bouton
        sauvegarder.setMinWidth(Tableau.MIN_WIDTH);
        sauvegarder.setMaxWidth(Tableau.MAX_WIDTH);
        sauvegarder.setOnAction(new ControleurSauvegarder(mod)); // Controleur

        Button charger = new Button("Charger"); // Bouton qui d'appeler le controleur qui charge le sujet
        charger.setMinWidth(Tableau.MIN_WIDTH);
        charger.setMaxWidth(Tableau.MAX_WIDTH);
        charger.setOnAction(new ControleurCharger(mod)); // Controleur

        Button showArchive = new Button("Archive"); // Bouton permettant d'ouvrir la liste des éléments archivées
        showArchive.setMinWidth(Tableau.MIN_WIDTH);
        showArchive.setMaxWidth(Tableau.MAX_WIDTH);
        showArchive.setOnAction(new ControleurAfficherArchive(mod));

        BoutonGantt showGantt =  BoutonGantt.getInstance(); //Partie permettant d'ouvrir une fenetre avec le diagramme de Gantt
        showGantt.setMinWidth(Tableau.MIN_WIDTH);
        showGantt.setMaxWidth(Tableau.MAX_WIDTH);
        showGantt.setOnAction(new ControleurGantt(mod));

        String textDarkMode = "Light Mode"; // Gestion du DarkMod / LightMod
        if (!ModeleTrello.DARK_MODE) {
            textDarkMode = "Dark Mode";
        }
        Button darkMode = new Button(textDarkMode);
        darkMode.setMinWidth(Tableau.MIN_WIDTH);
        darkMode.setMaxWidth(Tableau.MAX_WIDTH);
        darkMode.setOnAction(new ControleurDarkMode(mod));

        darkMode.getStyleClass().add("bouton");
        sauvegarder.getStyleClass().add("bouton");
        charger.getStyleClass().add("bouton");
        showArchive.getStyleClass().add("bouton");
        showGantt.getStyleClass().add("bouton");

        Button vueListe;
        if (ModeleTrello.VUE_TACHE) { // gestion de l'affichage des tâches
           vueListe = new Button("Affichage sous-tâche");
          vueListe.setMinWidth(Tableau.MIN_WIDTH);
          vueListe.setMaxWidth(Tableau.MAX_WIDTH);
          vueListe.setOnAction(new ControleurVueListe(mod));
      }else{
           vueListe = new Button("Affichage tâche");
          vueListe.setMinWidth(Tableau.MIN_WIDTH);
          vueListe.setMaxWidth(Tableau.MAX_WIDTH);
          vueListe.setOnAction(new ControleurVueListe(mod));
                }
        vueListe.getStyleClass().add("bouton");

        this.getChildren().addAll(nomTableau,espace, sauvegarder,charger,showArchive,showGantt,vueListe, darkMode);
    }

}