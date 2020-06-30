
package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class ReportActionTest {
    @Test
    public void init() {
        ReportAction report = new ReportAction(StatusPlayer.IDLE);
        assertEquals(StatusPlayer.IDLE, report.getStatusPlayer());
        assertEquals(God.STANDARD, report.getGod());

        report = new ReportAction(StatusPlayer.IDLE, God.APOLLO);
        assertEquals(StatusPlayer.IDLE, report.getStatusPlayer());
        assertEquals(God.APOLLO, report.getGod());
    }

    @Test
    public void initNull() {
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(null);
        });
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(StatusPlayer.IDLE, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(null, God.APOLLO);
        });
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(null, null);
        });

    }
}