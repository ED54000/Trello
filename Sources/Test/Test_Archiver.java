import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trello.composant.Archive;
import trello.composant.Liste;
import trello.composant.TacheFeuille;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_Archiver {

    Liste l;
    TacheFeuille t1, t2, t3;
    Archive archive;

    @BeforeEach
    public void init() {
        archive = new Archive();
        l = new Liste("test", 0);
        t1 = new TacheFeuille("tache1", l.getId(),null,null);
        t2 = new TacheFeuille("tache2", l.getId(),null,null);
        t3 = new TacheFeuille("tache3", l.getId(),null,null);
        l.ajouterTache(t1);
        l.ajouterTache(t2);
        l.ajouterTache(t3);
    }

    @Test
    public void test_archiver_liste() {
        Liste attendu = new Liste("test", 0);
        attendu.ajouterTache(new TacheFeuille("tache1", attendu.getId(),null,null));
        attendu.ajouterTache(new TacheFeuille("tache2", attendu.getId(),null,null));
        attendu.ajouterTache(new TacheFeuille("tache3", attendu.getId(),null,null));

        archive.archiverListe(attendu);
        assertEquals(attendu.getTaches().size(), archive.getListeArchive().get(1).getTaches().size());
    }

    @Test
    public void test_archiver_tache() {
        TacheFeuille t = new TacheFeuille("tache1", 0,null,null);
        TacheFeuille[] attendu = {t};

        archive.archiverTache(t1);

        assertArrayEquals(attendu, archive.getListeArchive().toArray(), "Listes différentes");
    }
}
