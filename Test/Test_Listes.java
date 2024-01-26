import org.junit.jupiter.api.Test;
import trello.composant.Liste;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_Listes {
    // Il faudra modifier la classe lors de différentes itérations

    @Test
    public void test_nom_liste(){
        // Teste le cas pour savoir si la liste à bien le nom rentré
        Liste liste = new Liste("NomListe", 0);
        String attendu = liste.getNom();
        assertEquals("NomListe",attendu,"La liste n'a pas le bon nom");
    }

    @Test
    public void test_liste_vide(){
        // Teste si la liste est bien vide lors de la création de cette dernière
        Liste liste = new Liste("NomListe" , 0);
        assertEquals(0,liste.getTaches().size(),"La liste devrait être vide");
    }

    @Test
    public void test_incrementation_id(){
        // Teste si les listes sont bien décrémentés
        Liste liste1 = new Liste("Liste1", 0);
        assertEquals(1,liste1.getId(),"La liste devrait avoit un id égal à un");
        Liste liste2 = new Liste("Liste2", 0);
        assertEquals(2,liste2.getId(),"La liste devrait avoir un id égal à 2");
        liste2= new Liste("Liste3", 0);
        assertEquals(3,liste2.getId(),"La liste devrait avoir un id égal à 3");
        // Il faudra modifier ce test lorsque l'on prendra en compte le fait de supprimer une liste
    }


}
