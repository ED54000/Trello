import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trello.composant.Liste;
import trello.composant.Tableau;
import trello.composant.TacheFeuille;
import trello.controleur.ControleurArchiver;
import trello.controleur.ControleurDesarchiver;
import trello.modele.ModeleTrello;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_Desarchiver {

    ModeleTrello m;
    Tableau t1, t2;
    Liste l1, l2, l3;
    TacheFeuille ta1, ta2, ta3;

    @BeforeEach
    public void init() {
        m = new ModeleTrello();

        t1 = new Tableau("Tableau1", true);
        m.ajouterTableau(t1);
        t2 = new Tableau("Tableau2", true);
        m.ajouterTableau(t2);

        l1 = new Liste("Liste1", t1.getIdTableau());
        l2 = new Liste("Liste2", t1.getIdTableau());
        l3 = new Liste("Liste3", t1.getIdTableau());

        m.selectionnerTableau(t1);
        m.ajouterListe(l1);
        m.selectionnerTableau(t2);
        m.ajouterListe(l2);
        m.ajouterListe(l3);


        ta1 = new TacheFeuille("tache1", l1.getId(),null,null);
        ta2 = new TacheFeuille("tache2", l2.getId(),null,null);
        ta3 = new TacheFeuille("tache3", l3.getId(),null,null);

        l1.ajouterTache(ta1);
        l2.ajouterTache(ta2);
        l3.ajouterTache(ta3);

    }

    @Test
    public void test_archiver_desarchiver_liste() {

        m.getArchive().archiverListe(l1);
        //Il faut mettre 2 et non 1 car il y a toujours une liste pour les tâches archiver dans l'archive
        assertEquals(2, m.getArchive().getListeArchive().size());

        m.getArchive().desarchiverListe(l1);
        //Il faut mettre 1 et non 0 car il y a toujours une liste pour les tâches archiver dans l'archive
        assertEquals(1, m.getArchive().getListeArchive().size());
    }

    @Test
    public void test_desarchiver_retourne_dans_bon_tableau() {

        //On archive la liste l1
        //On set le tableau 1 comme tableau selectionné
        m.setSelectionnerTableau(t1.getIdTableau());
        assertEquals(1, t1.getListes().size());
        assertEquals(2, t2.getListes().size());

        ControleurArchiver c = new ControleurArchiver(m, l1);
        c.handle(new ActionEvent());

        //On regarde que le tableau 1 n'a plus de liste et que le tableau 2 en a bien 2
        assertEquals(0, t1.getListes().size());
        assertEquals(2, m.getArchive().getListeArchive().size());

        //On desarchive la liste l1
        ControleurDesarchiver c2 = new ControleurDesarchiver(m, l1);
        c2.handle(new ActionEvent());

        //On regarde que le tableau 1 a bien récupéré la liste l1 et que l'archive n'a plus que 1 liste (la liste pour les tâches archivées)
        assertEquals(1, t1.getListes().size());
        assertEquals(1, m.getArchive().getListeArchive().size());
    }
}
