package com.phuszcza;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

import java.time.LocalDateTime;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.phuszcza.data.Clock;
import com.phuszcza.data.ClockTest;
import com.phuszcza.data.Styles;

import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.framework.junit.ApplicationTest;

public class MainTest extends ApplicationTest {

    private ClockController clockController;
    private Clock clock;

    private static final String BLINKER = "#secondsBlinker";
    private static final String FIVE_HOUR1 = "#fiveHour1";
    private static final String FIVE_HOUR2 = "#fiveHour2";
    private static final String FIVE_HOUR3 = "#fiveHour3";
    private static final String FIVE_HOUR4 = "#fiveHour4";
    private static final String ONE_HOUR1 = "#oneHour1";
    private static final String ONE_HOUR2 = "#oneHour2";
    private static final String ONE_HOUR3 = "#oneHour3";
    private static final String ONE_HOUR4 = "#oneHour4";
    private static final String FIVE_MINUTE1 = "#fiveMinute1";
    private static final String FIVE_MINUTE2 = "#fiveMinute2";
    private static final String FIVE_MINUTE3 = "#fiveMinute3";
    private static final String FIVE_MINUTE4 = "#fiveMinute4";
    private static final String FIVE_MINUTE5 = "#fiveMinute5";
    private static final String FIVE_MINUTE6 = "#fiveMinute6";
    private static final String FIVE_MINUTE7 = "#fiveMinute7";
    private static final String FIVE_MINUTE8 = "#fiveMinute8";
    private static final String FIVE_MINUTE9 = "#fiveMinute9";
    private static final String FIVE_MINUTE10 = "#fiveMinute10";
    private static final String FIVE_MINUTE11 = "#fiveMinute11";
    private static final String ONE_MINUTE1 = "#oneMinute1";
    private static final String ONE_MINUTE2 = "#oneMinute2";
    private static final String ONE_MINUTE3 = "#oneMinute3";
    private static final String ONE_MINUTE4 = "#oneMinute4";


    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clock.fxml"));
        Parent root = loader.load();
        this.clock = new Clock();
        this.clockController = loader.getController();
        clockController.setClock(clock);
        clock.stop();
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    @Before
    public void prepare() {
        clockController.start();
        clockController.stop();
    }

    @Test
    public void testBlinkerSecondsOdd() {
        assertStyle(GuiTest.find(BLINKER), Styles.OFF_BACKGROUND);

        LocalDateTime localDateTime = getTime(10, 30, 5);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(GuiTest.find(BLINKER), Styles.OFF_BACKGROUND);
    }

    @Test
    public void testBlinkerBlinks() {
        assertStyle(GuiTest.find(BLINKER), Styles.OFF_BACKGROUND);

        LocalDateTime localDateTime = getTime(10, 30, 4);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(GuiTest.find(BLINKER), Styles.YELLOW_BACKGROUND);

        localDateTime = getTime(10, 30, 5);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(GuiTest.find(BLINKER), Styles.OFF_BACKGROUND);

        localDateTime = getTime(10, 30, 6);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(GuiTest.find(BLINKER), Styles.YELLOW_BACKGROUND);
    }

    @Test
    public void testMinutesChangeFiveAndOneMinteIntervals() {
        Label[] fiveMinuteIntervalLabels = getFiveMinuteIntervalLabes();
        Label[] oneMinuteIntervalLabels = getOneMinuteIntervalLabes();

        assertStyle(fiveMinuteIntervalLabels, Styles.OFF_BACKGROUND);
        assertStyle(oneMinuteIntervalLabels, Styles.OFF_BACKGROUND);

        LocalDateTime localDateTime = getTime(10, 30, 5);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(fiveMinuteIntervalLabels, Styles.YELLOW_BACKGROUND, Styles.YELLOW_BACKGROUND, Styles.RED_BACKGROUND,
                Styles.YELLOW_BACKGROUND, Styles.YELLOW_BACKGROUND, Styles.RED_BACKGROUND,
                Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND);
        assertStyle(oneMinuteIntervalLabels, Styles.OFF_BACKGROUND);

        localDateTime = getTime(10, 31, 5);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(fiveMinuteIntervalLabels, Styles.YELLOW_BACKGROUND, Styles.YELLOW_BACKGROUND, Styles.RED_BACKGROUND,
                Styles.YELLOW_BACKGROUND, Styles.YELLOW_BACKGROUND, Styles.RED_BACKGROUND,
                Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND);
        assertStyle(oneMinuteIntervalLabels, Styles.YELLOW_BACKGROUND,
                Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND);

        localDateTime = getTime(10, 32, 5);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(fiveMinuteIntervalLabels, Styles.YELLOW_BACKGROUND, Styles.YELLOW_BACKGROUND, Styles.RED_BACKGROUND,
                Styles.YELLOW_BACKGROUND, Styles.YELLOW_BACKGROUND, Styles.RED_BACKGROUND,
                Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND);
        assertStyle(oneMinuteIntervalLabels, Styles.YELLOW_BACKGROUND, Styles.YELLOW_BACKGROUND,
                Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND);
    }

    @Test
    public void testHoursChange() {
        Label[] fiveHourIntervalLabels = getFiveHourIntervalLabels();
        Label[] oneHourIntervalLabels = getOneHourIntervalLabels();

        assertStyle(fiveHourIntervalLabels, Styles.OFF_BACKGROUND);
        assertStyle(oneHourIntervalLabels, Styles.OFF_BACKGROUND);

        LocalDateTime localDateTime = getTime(10, 30, 5);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(fiveHourIntervalLabels, Styles.RED_BACKGROUND, Styles.RED_BACKGROUND, Styles.OFF_BACKGROUND,
                Styles.OFF_BACKGROUND);
        assertStyle(oneHourIntervalLabels, Styles.OFF_BACKGROUND);

        localDateTime = getTime(11, 30, 5);
        ClockTest.updateClock(localDateTime, clock);

        assertStyle(fiveHourIntervalLabels, Styles.RED_BACKGROUND, Styles.RED_BACKGROUND, Styles.OFF_BACKGROUND,
                Styles.OFF_BACKGROUND);
        assertStyle(oneHourIntervalLabels, Styles.RED_BACKGROUND, Styles.OFF_BACKGROUND, Styles.OFF_BACKGROUND,
                Styles.OFF_BACKGROUND);
    }

    private LocalDateTime getTime(int hour, int minute, int second) {
        return LocalDateTime.of(2018, 1, 1, hour, minute, second);
    }

    private void assertStyle(Label field, String expectedStyle) {
        String currentStyle = field.getStyle();
        assertThat(currentStyle, is(expectedStyle));
    }

    private void assertStyle(Label[] fields, String... styles) {
        for (int i = 0; i < fields.length; i++) {
            assertStyle(fields[i], styles[i % styles.length]);
        }
    }

    private Label[] getFiveMinuteIntervalLabes() {
        return new Label[] {
                GuiTest.find(FIVE_MINUTE1),
                GuiTest.find(FIVE_MINUTE2),
                GuiTest.find(FIVE_MINUTE3),
                GuiTest.find(FIVE_MINUTE4),
                GuiTest.find(FIVE_MINUTE5),
                GuiTest.find(FIVE_MINUTE6),
                GuiTest.find(FIVE_MINUTE7),
                GuiTest.find(FIVE_MINUTE8),
                GuiTest.find(FIVE_MINUTE9),
                GuiTest.find(FIVE_MINUTE10),
                GuiTest.find(FIVE_MINUTE11)
        };
    }

    private Label[] getOneMinuteIntervalLabes() {
        return new Label[] {
                GuiTest.find(ONE_MINUTE1),
                GuiTest.find(ONE_MINUTE2),
                GuiTest.find(ONE_MINUTE3),
                GuiTest.find(ONE_MINUTE4)
        };
    }

    private Label[] getFiveHourIntervalLabels() {
        return new Label[] {
                GuiTest.find(FIVE_HOUR1),
                GuiTest.find(FIVE_HOUR2),
                GuiTest.find(FIVE_HOUR3),
                GuiTest.find(FIVE_HOUR4)
        };
    }

    private Label[] getOneHourIntervalLabels() {
        return new Label[] {
                GuiTest.find(ONE_HOUR1),
                GuiTest.find(ONE_HOUR2),
                GuiTest.find(ONE_HOUR3),
                GuiTest.find(ONE_HOUR4)
        };
    }
}