import org.junit.jupiter.api.Test;
import trello.composant.Tableau;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class Test_Tableau {

    // Les tests seront complétés lors des itérations suivantes 


    @Test
    public void test_tableau_vide(){
        Tableau tab = new Tableau("MonTableau");
        assertEquals(0,tab.getListes().size(),"Le tableau devrait être vide");
    }
}
