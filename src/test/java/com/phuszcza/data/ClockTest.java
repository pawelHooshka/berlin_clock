package com.phuszcza.data;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClockTest {

    private static final int CHANGE_LISTENER_WAS_NOT_INVOKED_AFTER_TIME_SET_FIRST_TIME = 1;

    @Mock
    private ChangeListener secondsChangeListener;
    @Mock
    private ChangeListener minutesChangeListener;
    @Mock
    private ChangeListener hoursChangeListener;

    private Clock clock;

    @Before
    public void init() {
        clock = new Clock();

        clock.observeSeconds(secondsChangeListener);
        clock.observeMinutes(minutesChangeListener);
        clock.observeHours(hoursChangeListener);
    }

    @Test
    public void testTimeDidNotPass() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testTimeElapsed(secondsChangeListener, clock, localDateTime, 0, 5, 1);
        testTimeElapsed(secondsChangeListener, clock, localDateTime, 0, 5, 1);

        verifyChangeListenerCalls(CHANGE_LISTENER_WAS_NOT_INVOKED_AFTER_TIME_SET_FIRST_TIME,
                minutesChangeListener, hoursChangeListener);
    }

    @Test
    public void testTimeElapsed() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testTimeElapsed(secondsChangeListener, clock, localDateTime, 0, 5, 1);

        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 6);
        testTimeElapsed(secondsChangeListener, clock, localDateTime, 5, 6, 2);

        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 7);
        testTimeElapsed(secondsChangeListener, clock, localDateTime, 6, 7, 3);

        verifyChangeListenerCalls(CHANGE_LISTENER_WAS_NOT_INVOKED_AFTER_TIME_SET_FIRST_TIME,
                minutesChangeListener, hoursChangeListener);
    }

    @Test
    public void testMinutesElapsed() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testTimeElapsed(minutesChangeListener, clock, localDateTime, 0, 31, 1);

        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 32, 5);
        testTimeElapsed(minutesChangeListener, clock, localDateTime, 31, 32, 2);

        localDateTime = LocalDateTime.of(2018, 1, 1, 10, 33, 5);
        testTimeElapsed(minutesChangeListener, clock, localDateTime, 32, 33, 3);

        verifyChangeListenerCalls(CHANGE_LISTENER_WAS_NOT_INVOKED_AFTER_TIME_SET_FIRST_TIME,
                secondsChangeListener, hoursChangeListener);
    }

    @Test
    public void testHoursElapsed() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        testTimeElapsed(hoursChangeListener, clock, localDateTime, 0, 10, 1);

        localDateTime = LocalDateTime.of(2018, 1, 1, 11, 31, 5);
        testTimeElapsed(hoursChangeListener, clock, localDateTime, 10, 11, 2);

        localDateTime = LocalDateTime.of(2018, 1, 1, 12, 31, 5);
        testTimeElapsed(hoursChangeListener, clock, localDateTime, 11, 12, 3);

        verifyChangeListenerCalls(CHANGE_LISTENER_WAS_NOT_INVOKED_AFTER_TIME_SET_FIRST_TIME,
                secondsChangeListener, minutesChangeListener);
    }

    @Test
    public void testSecondsHoursMinutesElapsed() {
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 31, 5);
        updateClock(localDateTime, clock);
        verifyChangeListenerCalls(1, secondsChangeListener, minutesChangeListener, hoursChangeListener);

        localDateTime = LocalDateTime.of(2018, 1, 1, 11, 32, 6);
        updateClock(localDateTime, clock);
        verifyChangeListenerCalls(2, secondsChangeListener, minutesChangeListener, hoursChangeListener);

        localDateTime = LocalDateTime.of(2018, 1, 1, 12, 33, 7);
        updateClock(localDateTime, clock);
        verifyChangeListenerCalls(3, secondsChangeListener, minutesChangeListener, hoursChangeListener);
    }

    private void verifyChangeListenerCalls(int expectedInvocations, ChangeListener... changeListeners) {
        Arrays.stream(changeListeners)
                .forEach(changeListener ->
                        verify(changeListener,
                                times(expectedInvocations))
                                .changed(any(), anyInt(), anyInt()));
    }

    private void testTimeElapsed(ChangeListener changeListenerInvoked, Clock clock, LocalDateTime localDateTime, int oldValue,
                                 int newValue, int expectedInvocations) {

        ArgumentCaptor<SimpleIntegerProperty> observableArgumentCaptor = ArgumentCaptor.forClass(SimpleIntegerProperty.class);
        ArgumentCaptor<Integer> oldArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> newArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        updateClock(localDateTime, clock);
        verify(changeListenerInvoked, times(expectedInvocations)).changed(observableArgumentCaptor.capture(),
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
