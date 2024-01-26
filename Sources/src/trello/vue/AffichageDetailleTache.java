package trello.vue;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import trello.composant.BoutonSousTache;
import trello.composant.ColonneListe;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.controleur.*;
import trello.modele.ModeleTrello;

import static trello.Main.scene;

/**
 * Classe Graphique étant un affichage spécifique d'une tache
 * ici c'est un affichage détaillé de la tache racine
 * où l'on peut voir ses sous taches
 */
public class AffichageDetailleTache extends TreeView {

    ModeleTrello mod;
    ComposantTache tache;

    /**
     * Constructeur de la classe
     *
     * @param t    Tache relié
     * @param text Nom
     * @param mod  Modèle du trello
     */
    private AffichageDetailleTache(ComposantTache t, String text, ModeleTrello mod) {
        super(t.genererArbreTache());
        this.setMinWidth(Liste.MIN_WIDTH - 20);
        this.setMaxWidth(Liste.MIN_WIDTH - 20);
        //Décoration de la tâche
        this.setPadding(new Insets(7));
        String color = "   -fx-background-color:  rgba(100,100,100,0.1);\n";
        String darkColor = "   -fx-background-color:  rgba(100,100,100,0.2);\n";

        this.setStyle(
                "    -fx-hgap: 20px;\n" +
                        "    -fx-background-radius: 8px;\n" +
                        "    -fx-border-radius: 8px;\n" +
                        "    -fx-border-width: 0px;\n" +
                        color);

        this.mod = mod;

        //Si notre souris est sur une tâche, alors on lance un event
        //qui change la couleur de fond de la tâche pour mieux voir qu'elle est selectionnée
        //Et on affiche les options de la tâche
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                setStyle(
                        "    -fx-hgap: 20px;\n" +
                                "    -fx-background-radius: 8px;\n" +
                                "    -fx-border-radius: 8px;\n" +
                                "    -fx-border-width: 0px;\n" +
                                darkColor);
            }
        });

        //Si notre souris n'est plus sur la tâche, alors on lance un event
        //qui remet la couleur de base de la tâche
        //Et on cache les options de la tâche
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                setStyle(
                        "    -fx-hgap: 20px;\n" +
                                "    -fx-background-radius: 8px;\n" +
                                "    -fx-border-radius: 8px;\n" +
                                "    -fx-border-width: 0px;\n" +
                                color);
            }
        });
        this.tache = t;
    }

    /**
     * Permet de récupérer la tache associé à l'élément
     *
     * @return la tache assossié
     */
    public ComposantTache getTache() {
        return this.tache;
    }


    /**
     * Methode permettant de creer un objet de la classe avec les proprietes demandés
     *
     * @param t    Tache
     * @param text Titre
     * @param mod  Modèle du trello
     * @return un objet avec les propriétés demandés comme drag and drop
     */
    public static AffichageDetailleTache buildTache(ComposantTache t, String text, ModeleTrello mod) {

        //VueTache tache = new VueTache(t, text, mod, isArchive);
        //Le boolean isArchive permet de savoir si on construit la tâche depuis l'archive ou non
        //Cela nous permet de savoir si on peut drag and drop la tâche ou non
        //Ca permet également d'afficher un menu différent pour les tâches de l'archive
        AffichageDetailleTache treeView = new AffichageDetailleTache(t, text, mod);
        treeView.setMinHeight(Region.USE_PREF_SIZE);
        treeView.setMaxHeight(Region.USE_PREF_SIZE);
        treeView.setPrefHeight(Region.USE_COMPUTED_SIZE);
        treeView.prefHeightProperty().bind(treeView.expandedItemCountProperty().multiply(25).add(25));
        treeView.setCellFactory(new Callback<TreeView<ComposantTache>, TreeCell<ComposantTache>>() {
            @Override
            public TreeCell call(TreeView treeView) {
                TreeCell<ComposantTache> cell = new TreeCell<ComposantTache>() {
                    @Override
                    protected void updateItem(ComposantTache tache, boolean empty) {
                        // une TreeCell peut changer de tâche, donc changer de TreeItem
                        super.updateItem(tache, empty);
                        this.setPrefHeight(ComposantTache.MIN_HEIGHT);
                        if (tache != null) {
                            setText(tache.getNom());
                            if (tache.isUrgent()) {
                                setStyle("   -fx-background-color:  rgba(200,0,0,0.7);\n");
                            } else {
                                setStyle("   -fx-background-color:  rgba(100,100,100,0.1);\n");
                            }
                        } else {
                            setText("");
                            setStyle("");
                        }
                    }
                };
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        TreeCell<ComposantTache> tache = (TreeCell<ComposantTache>) mouseEvent.getSource();
                        Stage popupStart = new Stage();
                        ComposantTache t = tache.getItem();

                        if (t != null) {
                            AffichageDetailleTache vue = (AffichageDetailleTache) tache.getTreeView();
                            VBox master = new VBox();
                            HBox boutons = new HBox();
                            Button archive;
                            if (!vue.getTache().isArchive()) {
                                archive = new Button("Archiver");
                                archive.setOnAction(actionEvent -> {
                                    new ControleurArchiver(vue.mod, t).handle(actionEvent);
                                    popupStart.close();
                                });
                                Button edit = new Button("Editer le nom");
                                Button urgent = new Button("Urgent");
                                urgent.setOnAction(new ControleurUrgent(vue.mod, t));
                                edit.setOnAction(new ControleurRenommer(vue.mod, t));
                                Button date = new Button("Modifier les dates");
                                date.setOnAction(new ControleurModifierDate(vue.mod, t));
                                edit.getStyleClass().add("bouton");
                                urgent.getStyleClass().add("bouton");
                                date.getStyleClass().add("bouton");
                                boutons.getChildren().addAll(edit, urgent, date);
                            } else {
                                archive = new Button("Desarchiver");
                                archive.setOnAction(actionEvent -> {
                                    new ControleurDesarchiver(vue.mod, t).handle(actionEvent);
                                    popupStart.close();
                                });
                            }
                            archive.getStyleClass().add("bouton");
                            boutons.getChildren().add(archive);
                            BoutonSousTache addSousTache = new BoutonSousTache("Ajouter une sous tache", ((ColonneListe) vue.getParent().getParent()).getListe());
                            addSousTache.setOnAction(new ControleurAjoutSousTache(vue.mod, t));
                            addSousTache.getStyleClass().add("bouton");
                            boutons.setAlignment(Pos.CENTER);
                            boutons.setSpacing(10);
                            if (!t.isArchive())
                                master.getChildren().addAll(boutons, addSousTache);
                            else
                                master.getChildren().add(boutons);
                            master.setSpacing(10);
                            master.setAlignment(Pos.CENTER);
                            master.setPadding(new Insets(10));
                            Scene popup = new Scene(master);
                            popup.getRoot().getStyleClass().add("pane");

                            if (ModeleTrello.DARK_MODE) {
                                popup.setFill(Color.rgb(40, 41, 42));
                                popup.getStylesheets().add("darkmode.css");
                            } else {
                                popup.getStylesheets().add("lightmode.css");
                                popup.setFill(Color.rgb(255, 255, 255));
                            }

                            popupStart.initModality(Modality.WINDOW_MODAL);
                            popupStart.initOwner(scene.getWindow());
                            popupStart.setScene(popup);
                            popupStart.setTitle(t.getNom());
                            popupStart.getIcons().add(new Image("trebas.png"));
                            popupStart.showAndWait();
                        }
                    }
                });
                return cell;
            }
        });
        if (!treeView.getTache().isArchive()) {
            treeView.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Dragboard db = treeView.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(t.getNom());
                    db.setContent(content);
                    mouseEvent.consume();
                }
            });
        }
        return treeView;
    }
}
