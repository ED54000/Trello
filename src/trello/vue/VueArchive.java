package trello.vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trello.composant.ColonneListe;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.controleur.ControleurDesarchiver;
import trello.modele.ModeleTrello;
import trello.modele.Sujet;


/**
 * Classe VueArchive, qui estune HBox et un Observateur
 * Actualise l'affichage de la fenêtre où sont affichés les éléments archivés
 */
public class VueArchive extends HBox implements Observateur {

    /**
     * Methode actualiser qui actualise l'affichage des éléments archivés du tableau.
     * @param s Modèle stockant les informations à afficher et actualiser.
     */
    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();
        ModeleTrello mod = (ModeleTrello) s;
        Label titreListe;
        ColonneListe colonneListe;
        VBox listeTaches;
        //Graphique liste
        // On parcourt chaque liste du modèle
        for (Liste l : mod.getArchive().getListeArchive()) {
            // On construit un label contenant le titre de la liste
            titreListe = new Label(l.getNom());

            titreListe.setStyle("-fx-font-weight: bold");
            titreListe.setPadding(new Insets(10));
            titreListe.setAlignment(Pos.TOP_CENTER);

            colonneListe = new ColonneListe(l);
            colonneListe.setMinHeight(Liste.MIN_HEIGHT);
            colonneListe.setMinWidth(Liste.MIN_WIDTH);
            colonneListe.setMaxWidth(Liste.MIN_WIDTH);

            colonneListe.setPadding(new Insets(10));
            colonneListe.setSpacing(5);
            colonneListe.setStyle(
                    "    -fx-hgap: 20px;\n" +
                            "    -fx-background-radius: 15px;\n" +
                            "    -fx-border-radius: 15px;\n" +
                            "    -fx-border-width: 1px;\n" +
                            "    -fx-border-color: " + l.getColor() + ";\n" +
                            "-fx-background-color: " + l.getColor() + ";");

            listeTaches = new VBox();
            listeTaches.setSpacing(5);

            colonneListe.getChildren().add(titreListe);

            //Container du titre de la liste et du bouton "Editer la liste"
            BorderPane container_liste_Title = new BorderPane();

            container_liste_Title.setLeft(titreListe);
            if(l.getId() != mod.getArchive().getListeArchive().get(0).getId()) {
                MenuBar menu = new MenuBar();
                Menu listeOptions = new Menu(". . .");
                MenuItem desarchive = new MenuItem("Desarchiver");

                desarchive.setOnAction(new ControleurDesarchiver(mod, l));
                listeOptions.getItems().add(desarchive);
                menu.getMenus().add(listeOptions);

                listeOptions.setId("menuOptions");
                menu.setId("menu");
                desarchive.setId("menuItems");
                container_liste_Title.setRight(menu);
            }

            colonneListe.getChildren().add(container_liste_Title);
            // On parcourt chaque tâche de la liste courante parcourue
            for (ComposantTache t : l.getTaches()) {

                AffichageBureau tache1 = AffichageBureau.buildTache(t, t.getNom(), mod);

                AffichageDetailleTache tache2 = AffichageDetailleTache.buildTache(t, t.getNom(), mod);

                // Chaque tâche est un bouton (pour des fonctionnalités supplémentaires dans les prochaines itérations
                if (ModeleTrello.VUE_TACHE) {
                    listeTaches.getChildren().add(tache1);
                } else {
                    listeTaches.getChildren().add(tache2);
                }
            }

            colonneListe.getChildren().add(listeTaches);
            this.getChildren().addAll(colonneListe);
        }
    }
}
