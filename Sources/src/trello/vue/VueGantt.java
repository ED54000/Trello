package trello.vue;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import trello.composant.ComposantTache;
import trello.composant.Liste;
import trello.composant.Tableau;
import trello.modele.ModeleTrello;
import trello.modele.Sujet;

import java.time.temporal.ChronoUnit;

/**
 * Classe Vue Gantt qui est observateur et aussi ScrollPane
 * Cette classe permet d'actualiser la visualisation du diagramme de Gantt (nouvelle fenêtre)
 */

public class VueGantt extends ScrollPane implements Observateur {
    private final int LARGEUR_RECTANGLE = 50;
    private final int LARGEUR_CANVAS = 4096;
    private final int HAUTEUR_CANVAS = 400;

    /**
     * Méthode chargé de généré un diagramme de Gantt selon le tableau selectionné
     * @param s Modèle stockant les informations à afficher et actualiser.
     */
    @Override
    public void actualiser(Sujet s) {
        // Création d'un canvas de dimensions 800x800
        Canvas canvas = new Canvas(LARGEUR_CANVAS, HAUTEUR_CANVAS);

        ModeleTrello mod = (ModeleTrello) s;
        Tableau tabSelec = mod.getTabSelectionne();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (!tabSelec.getListes().isEmpty()) {
            double y = 15;
            double xTrait = 190;
            double epaisseur_rectangle = 15;
            double axeOrdonnee = 50;

            // Variables calculant la position en pixels entre le début et la fin d'une tâche
            double xDebTache, xFinTache;
            // Parcourt des différentes listes
            for (Liste l : tabSelec.getListes()) {
                gc.setFill(Color.BLACK);
                // Ecriture du nom de la liste dans l'axe des ordonnées
                gc.strokeText(l.getNom(), 10, y);
                // Parcourt des tâches
                for (ComposantTache tache : l.getTaches()) {
                    // On "descent" de quelques pixels pour écrire les tâches qui seront indentées par rapport aux listes
                    y += 30;
                    // Ecriture du nom de la tâche dans l'axe des ordonnées
                    gc.strokeText(tache.getNom(), 17, y);
                    if (tache.isUrgent()) {
                        gc.setFill(Color.RED);
                    }else {
                        gc.setFill(Color.BLUE);
                    }
                    // Création d'un rectangle dont la taille correspond à la durée de la tâche
                    xDebTache = axeOrdonnee + xTrait + LARGEUR_RECTANGLE * ChronoUnit.DAYS.between(tabSelec.getDateMin(), tache.getDateDebut());

                    xFinTache = LARGEUR_RECTANGLE * tache.getNbJour();
                    gc.fillRect(xDebTache, y - epaisseur_rectangle, xFinTache, epaisseur_rectangle);


                }
                // On "redescent" en laissant un écart pour écrire la nouvelle liste
                y += 50;
            }
            long nbJourTotal = tabSelec.getDateMin().until(tabSelec.getDateMax(), ChronoUnit.DAYS);
            // Coordonnée x de la fin de l'abscisse
            double finAxeX = xTrait + axeOrdonnee + nbJourTotal * LARGEUR_RECTANGLE;
            // Création de l'axe des ordonnées
            gc.strokeLine(xTrait, 5, xTrait, y - 20);
            // Création de l'axe des abscisses
            gc.strokeLine(xTrait, y - 20, finAxeX, y - 20);


            // Ecriture de la date minimale au début de l'axe des abscisses
            gc.strokeText(tabSelec.getDateMinString(), xTrait, y - 2);

            // Ecriture de la date maximale à la fin de l'axe des abscisses
            gc.strokeText(tabSelec.getDateMaxString(), finAxeX, y - 2);
            // Graduation de l'axe des abscisses
            while (xTrait < 190 + axeOrdonnee + nbJourTotal * LARGEUR_RECTANGLE) {
                xTrait += LARGEUR_RECTANGLE;
                gc.strokeLine(xTrait, y - 25, xTrait, y - 15);
            }
        } else {
            gc.strokeText("AUCUNE DONNÉE A AFFICHER", LARGEUR_CANVAS / 2, HAUTEUR_CANVAS / 2);
        }


        this.setContent(canvas);
    }
}