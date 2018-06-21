package com.phuszcza;

import com.phuszcza.data.Clock;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClockController {

    public static final String RED_BACKGROUND = "-fx-background-color: red;";
    public static final String YELLOW_BACKGROUND = "-fx-background-color: yellow;";
    public static final String OFF_BACKGROUND = "-fx-background-color: grey;";

    //-- HOURS --//
    @FXML private Label fiveHour1;
    @FXML private Label fiveHour2;
    @FXML private Label fiveHour3;
    @FXML private Label fiveHour4;

    @FXML private Label oneHour1;
    @FXML private Label oneHour2;
    @FXML private Label oneHour3;
    @FXML private Label oneHour4;
    private Label[] hourLabels;

    //-- MINUTES--//
    @FXML private Label fiveMinute1;
    @FXML private Label fiveMinute2;
    @FXML private Label fiveMinute3;
    @FXML private Label fiveMinute4;
    @FXML private Label fiveMinute5;
    @FXML private Label fiveMinute6;
    @FXML private Label fiveMinute7;
    @FXML private Label fiveMinute8;
    @FXML private Label fiveMinute9;
    @FXML private Label fiveMinute10;
    @FXML private Label fiveMinute11;
    private Label[] fiveMinutesIntervalLabels;

    @FXML private Label oneMinute1;
    @FXML private Label oneMinute2;
    @FXML private Label oneMinute3;
    @FXML private Label oneMinute4;
    private Label[] oneMinuteIntervalLabels;

    //-- SECONDS--//
    @FXML private Label secondsBlinker;

    private Clock clock;

    public ClockController() {
        this.clock = new Clock();
    }

    void setClock(Clock clock) {
        this.clock = clock;
    }

    @FXML
    public void initialize() {
        initialiseArrays();
        clock.start();
    }

    public void stop() {
        clock.stop();
    }

    public void start() {
        startBlinker();
        startMinutes();
        startHours();
        clock.start();
    }

    private void updateBlinker(Integer seconds) {
        if (seconds != null && seconds % 2 == 0) {
            secondsBlinker.setStyle(YELLOW_BACKGROUND);
        } else if (seconds != null && seconds % 2 != 0) {
            secondsBlinker.setStyle(OFF_BACKGROUND);
        }
    }

    private void updateMinutes(Integer minutes) {
        if (minutes != null) {
            int fiveMinuteIntervals = minutes / 5;
            int singleMinutes = minutes - (fiveMinuteIntervals * 5);
            colourFiveMinutes(fiveMinutesIntervalLabels, fiveMinuteIntervals);
            colourLabels(oneMinuteIntervalLabels, YELLOW_BACKGROUND, 0, singleMinutes);
        }
    }

    private void updateHours(Integer hours) {
        if (hours != null) {
            int fiveHoursIntervals = hours / 5;
            int singleHours = hours - (fiveHoursIntervals * 5);
            colourLabels(hourLabels, RED_BACKGROUND, 0, fiveHoursIntervals);
            colourLabels(hourLabels, RED_BACKGROUND, 4, singleHours);
        }
    }

    private void colourFiveMinutes(Label[] fiveMinutesIntervalLabels, int updateOnLength) {
        for (int i = 0; i < fiveMinutesIntervalLabels.length; i++) {
            if (i < updateOnLength && (i + 1) % 3 != 0) {
                fiveMinutesIntervalLabels[i].setStyle(YELLOW_BACKGROUND);
            } else if (i < updateOnLength && (i + 1) % 3 == 0) {
                fiveMinutesIntervalLabels[i].setStyle(RED_BACKGROUND);
            } else {
                fiveMinutesIntervalLabels[i].setStyle(OFF_BACKGROUND);
            }
        }
    }

    private void colourLabels(Label[] labels, String onStyle, int start, int length) {
        for (int i = start; i < labels.length; i++) {
            if (i < length + start) {
                labels[i].setStyle(onStyle);
            } else {
                labels[i].setStyle(OFF_BACKGROUND);
            }
        }
    }

    private void initialiseArrays() {
        this.fiveMinutesIntervalLabels = new Label[]{
                fiveMinute1,
                fiveMinute2,
                fiveMinute3,
                fiveMinute4,
                fiveMinute5,
                fiveMinute6,
                fiveMinute7,
                fiveMinute8,
                fiveMinute9,
                fiveMinute10,
                fiveMinute11
        };

        this.oneMinuteIntervalLabels = new Label[] {
                oneMinute1,
                oneMinute2,
                oneMinute3,
                oneMinute4
        };

        this.hourLabels = new Label[] {
                fiveHour1,
                fiveHour2,
                fiveHour3,
                fiveHour4,

                oneHour1,
                oneHour2,
                oneHour3,
                oneHour4
        };
    }

    /*
    If the new value is not different than the old value - update will NOT be triggered! We know therefore, that if any of
    these methods was called - then the new value must have been different then the old one.
     */

    private void startBlinker() {
        clock.observeSeconds((observable, oldValue, newValue) -> {
            updateBlinker((Integer) newValue);
        });
    }

    private void startMinutes() {
        clock.observeMinutes((observable, oldValue, newValue) -> {
            updateMinutes((Integer) newValue);
        });
    }

    private void startHours() {
        clock.observeHours((observable, oldValue, newValue) -> {
            updateHours((Integer) newValue);
        });
    }
}
