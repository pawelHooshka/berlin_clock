package com.phuszcza.data;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClockTest {

    @Mock
    private ChangeListener changeListener;

    @Test
    public void testTimeDidNotPass() {
        Clock clock = new Clock();
        clock.observeSeconds(changeListener);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testSecondsElapsed(clock, localDateTime, 0, 5, 1);
        testSecondsElapsed(clock, localDateTime, 0, 5, 1);
    }

    @Test
    public void testSecondsElapsed() {
        Clock clock = new Clock();
        clock.observeSeconds(changeListener);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testSecondsElapsed(clock, localDateTime, 0, 5, 1);
        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 6);
        testSecondsElapsed(clock, localDateTime, 5, 6, 2);
        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 7);
        testSecondsElapsed(clock, localDateTime, 6, 7, 3);
    }

    @Test
    public void testMinutesElapsed() {
        Clock clock = new Clock();
        clock.observeMinutes(changeListener);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testSecondsElapsed(clock, localDateTime, 0, 31, 1);
        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 32, 5);
        testSecondsElapsed(clock, localDateTime, 31, 32, 2);
        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 33, 5);
        testSecondsElapsed(clock, localDateTime, 32, 33, 3);
    }

    @Test
    public void testHoursElapsed() {
        Clock clock = new Clock();
        clock.observeHours(changeListener);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testSecondsElapsed(clock, localDateTime, 0, 10, 1);
        localDateTime = LocalDateTime.of(2018, 1, 1, 11, 31, 5);
        testSecondsElapsed(clock, localDateTime, 10, 11, 2);
        localDateTime = LocalDateTime.of(2018, 1, 1, 12, 31, 5);
        testSecondsElapsed(clock, localDateTime, 11, 12, 3);
    }

    private void testSecondsElapsed(Clock clock, LocalDateTime localDateTime, int oldValue, int newValue, int expectedInvocations) {
        ArgumentCaptor<SimpleIntegerProperty> observableArgumentCaptor = ArgumentCaptor.forClass(SimpleIntegerProperty.class);
        ArgumentCaptor<Integer> oldArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> newArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        updateClock(localDateTime, clock);
        verify(changeListener, times(expectedInvocations)).changed(observableArgumentCaptor.capture(),
                oldArgumentCaptor.capture(), newArgumentCaptor.capture());

        assertArgumentCaprors(observableArgumentCaptor, oldArgumentCaptor, newArgumentCaptor, oldValue, newValue);
    }

    private void assertArgumentCaprors(ArgumentCaptor<SimpleIntegerProperty> observableArgumentCaptor,
        ArgumentCaptor<Integer> oldArgumentCaptor, ArgumentCaptor<Integer> newArgumentCaptor, int oldValue, int newValue) {

        assertThat(observableArgumentCaptor.getValue(), instanceOf(SimpleIntegerProperty.class));
        assertThat(oldArgumentCaptor.getValue(), is(oldValue));
        assertThat(newArgumentCaptor.getValue(), is(newValue));
    }

    public static void updateClock(LocalDateTime localDateTime, Clock clock) {
        clock.updateTime(localDateTime);
    }
}
