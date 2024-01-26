import org.junit.jupiter.api.Test;
import trello.composant.TacheFeuille;
import trello.composant.TacheMere;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Test_Taches {

    // Les tests seront complétés lors des itérations suivantes

    @Test
    public void test_tache_mere_nom(){
        // Test pour la création d'une tâche mère
        TacheMere tacheMere = new TacheMere("Tache1", 0,null,null);
        assertEquals("Tache1", tacheMere.getNom(), "La tâche mère n'a pas de nom");
        assertEquals(0,tacheMere.getSousTaches().size(),"La tache mère ne devrait avoir aucune tâche feuille");
    }

    @Test
    public void test_tache_feuille_nom(){
        // Test pour la création d'une tâche feuille
        TacheFeuille tachefeuille = new TacheFeuille("Tache1", 0,null,null);
        assertEquals("Tache1", tachefeuille.getNom(), "La tâche feuille n'a pas de nom");
    }



}
