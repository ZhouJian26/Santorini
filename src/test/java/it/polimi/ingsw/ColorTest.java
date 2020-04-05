package it.polimi.ingsw;

import it.polimi.ingsw.model.Color;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ColorTest {

    @Test
    public void strConvertTest () {

        String white = "wHite";
        String brown = "BrOWn";
        String blue = "bluE";


        assertEquals(Color.strConverter(white), Color.WHITE);
        assertEquals(Color.strConverter(brown), Color.BROWN);
        assertEquals(Color.strConverter(blue), Color.BLUE);


    }

}
