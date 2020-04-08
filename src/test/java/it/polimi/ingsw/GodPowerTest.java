package it.polimi.ingsw;

import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.GodInterface;
import it.polimi.ingsw.model.GodPower;
import org.junit.Test;

public class GodPowerTest {
        GodInterface god=new GodPower(God.STANDARD,"abc");


        @Test
        public void getNameTest(){
                assertTrue("abc".equals(god.getName()));
        }

        @Test
        public void setGetStatusTest(){
                god.activate(true);
                boolean status=god.getStatus();
                assertTrue(status&&god.getStatus());
                status=false;
                assertTrue(!status&&god.getStatus());
        }
}
