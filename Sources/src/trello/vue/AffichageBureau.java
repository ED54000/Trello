package trello.vue;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import trello.controleur.*;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.modele.ModeleTrello;

/**
 * Classe Graphique étant un affichage spécifique d'une tache
 * ici c'est un affichage simple de la tache racine
 * avec les dates juste en-desssous.
 */
public class AffichageBureau extends BorderPane {

    ModeleTrello mod;
    ComposantTache tache;
    Text label;
    MenuBar menu;

    /**
     * Constructeur de l'objet
     * @param t Tache relié
     * @param text Nom
     * @param mod Modèle
     */
    private AffichageBureau(ComposantTache t, String text, ModeleTrello mod) {

        super();
        this.setMinWidth(Liste.MIN_WIDTH - 20);
        this.setMaxWidth(Liste.MIN_WIDTH - 20);
        this.setMinHeight(ComposantTache.MIN_HEIGHT);
        //Décoration de la tâche
        this.setPadding(new Insets(7));

        String color;
        String darkColor;

        //Si la tâche est urgente, on change la couleur de fond
        if (t.isUrgent()) {
            color = "   -fx-background-color:  rgba(200,0,0,0.7);\n";
            darkColor = "   -fx-background-color:  rgba(150,0,0,0.7);\n";
        } else {
            color = "   -fx-background-color:  rgba(100,100,100,0.1);\n";
            darkColor = "   -fx-background-color:  rgba(100,100,100,0.2);\n";
        }

        this.setStyle(
                "    -fx-hgap: 20px;\n" +
                        "    -fx-background-radius: 8px;\n" +
                        "    -fx-border-radius: 8px;\n" +
                        "    -fx-border-width: 0px;\n" +
                        color);

        this.mod = mod;
        label = new Text(text);
        label.setWrappingWidth(125); // Ajustez la largeur en conséquence


        menu = new MenuBar();
        menu.setId("menu");
        Menu listeOptions = new Menu("   ");
        listeOptions.setId("listeOptions");

        //Si notre souris est sur une tâche, alors on lance un event
        //qui change la couleur de fond de la tâche pour mieux voir qu'elle est selectionnée
        //Et on affiche les options de la tâche
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                listeOptions.setText(". . .");
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
                listeOptions.setText("   ");
                setStyle(
                        "    -fx-hgap: 20px;\n" +
                                "    -fx-background-radius: 8px;\n" +
                                "    -fx-border-radius: 8px;\n" +
                                "    -fx-border-width: 0px;\n" +
                                color);
            }
        });

        MenuItem archive;
        //Si on est dans l'archive, on affiche un menu différent
        //Archiver, editer et urgent
        //Sinon on affiche un autre menu
        //Desarchiver et editer
        if (!t.isArchive()) {
            archive = new MenuItem("Archiver");
            archive.setOnAction(new ControleurArchiver(this.mod, t));
            MenuItem edit = new MenuItem("Editer le nom");
            edit.setId("menuItems");
            MenuItem urgent = new MenuItem("Urgent");
            urgent.setId("menuItems");
            urgent.setOnAction(new ControleurUrgent(this.mod, t));
            MenuItem date = new MenuItem("Modifier les dates");
            date.setId("menuItems");
            date.setOnAction(new ControleurModifierDate(this.mod, t));
            edit.setOnAction(new ControleurRenommer(this.mod, t));
            listeOptions.getItems().addAll(edit, urgent, date);
        } else {
            archive = new MenuItem("Desarchiver");
            archive.setOnAction(new ControleurDesarchiver(this.mod, t));
        }
        archive.setId("menuItems");
        MenuItem masque;
        if (!t.TACHE_REDUITE) {
            masque = new MenuItem("Réduire");
        } else {
            masque = new MenuItem("Déployer");
        }
        masque.setId("menuItems");
        masque.setOnAction(new ControleurReduireTache(this.mod, t));
        listeOptions.getItems().addAll(archive, masque);
        menu.getMenus().add(listeOptions);
        if (!t.TACHE_REDUITE) {
            this.setLeft(label);
            this.setRight(menu);
            Label date = new Label("Du " + t.getDateDebutString() + " au " + t.getDateFinString());
            this.setBottom(date);
        } else {
            this.setLeft(label);
            this.setRight(menu);
        }

        this.tache = t;
    }

    public ComposantTache getTache() {
        return this.tache;
    }

    /**
     * Classe permettant de construire un objet de cette classe avec les propriétés demandés
     * comme le drag and drop ect...
     * @param t Tache utilisé
     * @param text Nom
     * @param mod Modèle
     * @return objet AffichageBureau avec les propriétés demandés
     */
    public static AffichageBureau buildTache(ComposantTache t, String text, ModeleTrello mod) {
        AffichageBureau tache = new AffichageBureau(t, text, mod);

        //Le boolean isArchive permet de savoir si on construit la tâche depuis l'archive ou non
        //Cela nous permet de savoir si on peut drag and drop la tâche ou non
        //Ca permet également d'afficher un menu différent pour les tâches de l'archive
        if (!tache.getTache().isArchive()) {
            tache.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Dragboard db = tache.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(t.getNom());
                    db.setContent(content);
                    mouseEvent.consume();
                }
            });
        }
        return tache;

    }
}
