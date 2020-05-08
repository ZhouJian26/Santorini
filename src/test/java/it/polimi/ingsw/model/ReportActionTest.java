
package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ReportActionTest {
    @Test
    public void init() {
        ReportAction report = new ReportAction(StatusPlayer.END);
        assertEquals(StatusPlayer.END, report.statusPlayer);
        assertEquals(God.STANDARD, report.god);

        report = new ReportAction(StatusPlayer.END, God.APOLLO);
        assertEquals(StatusPlayer.END, report.statusPlayer);
        assertEquals(God.APOLLO, report.god);
    }

    @Test
    public void initNull() {
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(null);
        });
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(StatusPlayer.END, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(null, God.APOLLO);
        });
        assertThrows(NullPointerException.class, () -> {
            new ReportAction(null, null);
        });

    }
}