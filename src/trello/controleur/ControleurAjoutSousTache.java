package trello.controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import trello.composant.*;
import trello.modele.ModeleTrello;

/***
 * Contrôleur permettant d'ajouter de sous-tâches
 */
public class ControleurAjoutSousTache implements EventHandler<ActionEvent> {
    // Attributs
    private ComposantTache tache;
    private ModeleTrello modeleTrello;

    /***
     * Constructeur du contrôleur
     * @param m le modèle utilisé pour notifier les observateurs
     * @param t la tâche qui possédera une sous-tâche
     */
    public ControleurAjoutSousTache(ModeleTrello m, ComposantTache t) {
        this.tache = t;
        this.modeleTrello = m;
    }

    /**
     * Méthode permettant d'ajouter une sous-tâche
     * @param actionEvent évènement qui a déclenché le contrôleur
     */
    @Override
    public void handle(ActionEvent actionEvent) {

        if (this.tache instanceof TacheFeuille) { // Passage de TacheFeuille à TacheMere
            ComposantTache oldTache = this.tache;
            BoutonSousTache b = (BoutonSousTache) actionEvent.getSource();
            Liste l = b.getListe();
            this.tache = new TacheMere(this.tache.getNom(), this.tache.getId(), this.tache.getDateDebut(), this.tache.getDateFin());
            if (l.getTaches().contains(oldTache)) {
                l.getTaches().set(l.getTaches().indexOf(oldTache), this.tache);
            } else {
                TacheMere parent = (TacheMere) oldTache.getParentTache();
                parent.getSousTaches().set(parent.getSousTaches().indexOf(oldTache), this.tache);
            }
        }

        TextInputDialog textInputDialog = new TextInputDialog("");
        textInputDialog.setHeaderText("Choisissez le nouveau nom de la sous tache");
        textInputDialog.showAndWait();

        if (textInputDialog.getResult() == null) {
            return;
        }
        if (textInputDialog.getResult() == null) {
            return;
        }
        //Teste si le resultat est vide
        if (textInputDialog.getResult().isEmpty()) {
            //Envoie une pop-up d'erreur et ne modifie pas le nom
            Alert alert = new Alert(Alert.AlertType.ERROR, "La tâche ne peut pas être vide");
            alert.show();
            return;
        } else {
            //Teste si le résultat est plus long que le nombre max
            if (textInputDialog.getResult().length() > ComposantTache.MAX_LENGTH_TITLE) {
                //Envoie une erreur
                Alert alert = new Alert(Alert.AlertType.WARNING, "Trop long (max " + ComposantTache.MAX_LENGTH_TITLE + " caractères)");
                alert.show();
                //Ne change pas le nom
                return;
            }
            // Création d'une nouvelle sous tache
            ComposantTache sousTache = new TacheFeuille(textInputDialog.getResult(), this.tache.getId(), this.tache.getDateDebut(), this.tache.getDateFin());
            sousTache.setParent(this.tache);
            TacheMere tacheMere = (TacheMere) this.tache;
            tacheMere.ajouterSousTache(sousTache);
            this.modeleTrello.notifierObservateurs();
        }
    }
}
