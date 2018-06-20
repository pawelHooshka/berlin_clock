package com.phuszcza.data;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.util.Duration;

import java.time.LocalDateTime;

public class Clock {

    private final SimpleIntegerProperty hour;
    private final SimpleIntegerProperty minute;
    private final SimpleIntegerProperty second;

    private final Timeline time;

    public Clock() {
        this.hour = new SimpleIntegerProperty(0);
        this.minute = new SimpleIntegerProperty(0);
        this.second = new SimpleIntegerProperty(0);

        this.time = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        tick -> {
                            tick();
                        }
                )
        );

        time.setCycleCount(Animation.INDEFINITE);
    }

    public void start() {
        time.play();
    }

    public void stop() {
        time.stop();
    }

    private void tick() {
        LocalDateTime localDateTime = LocalDateTime.now();
        updateTime(localDateTime);
    }

    protected void updateTime(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();

        setField(this.hour, hour);
        setField(this.minute, minute);
        setField(this.second, second);
    }

    private void setField(SimpleIntegerProperty field, int value) {
        if (field.get() != value) {
            field.set(value);
        }
    }

    public void observeHours(ChangeListener hoursListener) {
        this.hour.addListener(hoursListener);
    }

    public void observeMinutes(ChangeListener minutesListener) {
        this.minute.addListener(minutesListener);
    }

    public void observeSeconds(ChangeListener secondsListener) {
        this.second.addListener(secondsListener);
    }
}
