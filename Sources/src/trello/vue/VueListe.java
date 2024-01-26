package trello.vue;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import trello.composant.ColonneListe;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.controleur.*;
import trello.modele.ModeleTrello;
import trello.modele.Sujet;

/**
 * Classe de la VueListe, étant un observateur, et une HBox
 * Celle-ci actualise l'ensemble des listes d'un tableau à chaque appels du sujet.
 */
public class VueListe extends HBox implements Observateur {


    /**
     * Méthode actualiser qui Override de l'interface Observateur,
     * à chaque notification faites au observateur par le sujet
     * cette méthode est appelé, elle permet d'actualiser l'interface graphique de l'ensemble des listes du tableau
     * en fonction des données du sujet
     *
     * @param s Sujet possédant l'ensemble des données
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
        if (mod.getTabSelectionne() != null) {
            for (Liste l : mod.getTabSelectionne().getListes()) {
                // On construit un label contenant le titre de la liste
                titreListe = new Label(l.getNom());


                MenuBar menu = new MenuBar();
                Menu listeOptions = new Menu(". . .");
                MenuItem edit = new MenuItem("Editer");
                MenuItem masquerL;

                if (l.LISTE_REDUITE) {
                    masquerL = new MenuItem("Réduire liste");
                } else {
                    masquerL = new MenuItem("Deployer liste");
                }

                MenuItem archive = new MenuItem("Archiver");
                archive.setOnAction(new ControleurArchiver(mod, l));
                masquerL.setOnAction(new ControleurReduireListe(mod, l));
                edit.setOnAction(new ControleurRenommer(mod, l));
                Menu colorPicker = new Menu("Couleur fond");

                menu.setStyle("-fx-background-color: rgba(0,0,0,0)");


                // Gestion de la couleur des listes par le menu

                MenuItem red = new MenuItem("ROUGE");
                red.setOnAction(new ControleurModifierCouleur(mod, l, "rgba(250,128,114)"));
                red.setId("menuItems");

                MenuItem green = new MenuItem("VERT");
                green.setOnAction(new ControleurModifierCouleur(mod, l, "rgb(0,255,127)"));
                green.setId("menuItems");

                MenuItem blue = new MenuItem("BLEU");
                blue.setOnAction(new ControleurModifierCouleur(mod, l, "rgb(0,206,209)"));
                blue.setId("menuItems");

                MenuItem lightBlue = new MenuItem("BLEU CLAIR");
                lightBlue.setOnAction(new ControleurModifierCouleur(mod, l, "rgb(175,238,238)"));
                lightBlue.setId("menuItems");

                MenuItem orange = new MenuItem("ORANGE");
                orange.setOnAction(new ControleurModifierCouleur(mod, l, "rgb(255,127,80)"));
                orange.setId("menuItems");

                MenuItem purple = new MenuItem("VIOLET");
                purple.setOnAction(new ControleurModifierCouleur(mod, l, "rgb(148,0,211)"));
                purple.setId("menuItems");

                MenuItem pink = new MenuItem("ROSE");
                pink.setOnAction(new ControleurModifierCouleur(mod, l, "rgb(255,105,180)"));
                pink.setId("menuItems");

                MenuItem yellow = new MenuItem("JAUNE");
                yellow.setOnAction(new ControleurModifierCouleur(mod, l, "rgb(255,255,0)"));
                yellow.setId("menuItems");

                MenuItem gray = new MenuItem("GRIS");
                gray.setOnAction(new ControleurModifierCouleur(mod, l, "#E2E4E6"));
                gray.setId("menuItems");


                listeOptions.getItems().addAll(edit, archive, masquerL, colorPicker);
                colorPicker.getItems().addAll(red, orange, yellow, green, lightBlue, blue, purple, pink, gray);

                menu.getMenus().add(listeOptions);

                listeOptions.setId("menuOptions");
                menu.setId("menu");
                edit.setId("menuItems");
                archive.setId("menuItems");
                masquerL.setId("menuItems");
                colorPicker.setId("menuItems");

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
                container_liste_Title.setRight(menu);
                colonneListe.getChildren().add(container_liste_Title);
                if (l.LISTE_REDUITE) {
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

                    // Création du bouton "Ajouter une tâche"
                    Button ajouterTache = new Button(" + ");
                    ajouterTache.setMinWidth(75);
                    ajouterTache.getStyleClass().add("bouton");

                    TextArea nomTache = new TextArea();
                    nomTache.getStyleClass().add("textarea");
                    nomTache.setPromptText("Nom de la tâche");
                    ajouterTache.setOnAction(new ControleurAjoutTache(mod, l, nomTache));
                    listeTaches.getChildren().addAll(nomTache, ajouterTache);
                    listeTaches.setAlignment(Pos.CENTER);
                }else{
                    colonneListe.setMinHeight(50);
                    colonneListe.setMaxHeight(50);
                    colonneListe.setMinWidth(Liste.MIN_WIDTH);
                    colonneListe.setMaxWidth(Liste.MIN_WIDTH);
                }
                    // Ajout de la liste des tâches à la liste courante
                    colonneListe.getChildren().add(listeTaches);
                    this.getChildren().addAll(colonneListe);

                colonneListe.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        dragEvent.acceptTransferModes(TransferMode.MOVE);
                        dragEvent.consume();
                    }
                });

                ColonneListe finalColonneListe = colonneListe;

                colonneListe.setOnDragDropped(new EventHandler<DragEvent>() { // Ajout du drag and drop
                    @Override
                    public void handle(DragEvent dragEvent) { // Dès qu'on pose un item drag and drop
                        Dragboard db = dragEvent.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            success = true;
                            ColonneListe c;
                            if (ModeleTrello.VUE_TACHE) {
                                AffichageBureau v = (AffichageBureau) dragEvent.getGestureSource();
                                c = (ColonneListe) v.getParent().getParent();
                                c.getListe().supprimerTache(v.getTache()); // On supprime de la liste initial la tache
                                finalColonneListe.getListe().ajouterTache(v.getTache()); // On ajoute la tache à la nouvelle liste
                                v.getTache().setId(finalColonneListe.getListe().getId());

                            } else {
                                AffichageDetailleTache v = (AffichageDetailleTache) dragEvent.getGestureSource();
                                c = (ColonneListe) v.getParent().getParent();
                                c.getListe().supprimerTache(v.getTache()); // On supprime de la liste initial la tache
                                finalColonneListe.getListe().ajouterTache(v.getTache());  // On ajoute la tache à la nouvelle liste
                                v.getTache().setId(finalColonneListe.getListe().getId());
                            }
                        }
                        dragEvent.setDropCompleted(success);
                        dragEvent.consume();
                        s.notifierObservateurs();
                    }

                });
            }

            // Commandes pour ajouter une nouvelle liste
            TextField nomColonne = new TextField(); // Permet d'entrer le nom de la nouvelle liste
            nomColonne.getStyleClass().add("textfield");
            nomColonne.setPromptText("Entrez un nom de colonne");
            nomColonne.setOnAction(new ControleurAjoutListe(mod, nomColonne));
            Button creerListe = new Button("Créer"); // Permet de créer la nouvelle liste
            creerListe.getStyleClass().add("bouton");
            creerListe.setOnAction(new ControleurAjoutListe(mod, nomColonne));
            this.getChildren().addAll(nomColonne, creerListe);
        }
    }
}
