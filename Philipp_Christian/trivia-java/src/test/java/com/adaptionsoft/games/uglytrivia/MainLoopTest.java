package com.adaptionsoft.games.uglytrivia;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class MainLoopTest {

    private Random randy = Mockito.mock(Random.class);
    private Game game = Mockito.mock(Game.class);

    private MainLoop mainLoop;

    @Before
    public void setup() {
        mainLoop = new MainLoop(game, randy);
    }

    @Test
    public void addUsers() {
        Mockito.when(randy.nextInt(5))
                .thenReturn(3);

        Mockito.when(randy.nextInt(9))
                .thenReturn(7);

        mainLoop.runGame();

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(game, Mockito.times(3)).add(argumentCaptor.capture());

        List<String> users = argumentCaptor.getAllValues();
        assertEquals(3, new HashSet<>(users).size());
        assertEquals("Chet", users.get(0));
        assertEquals("Pat", users.get(1));
        assertEquals("Sue", users.get(2));
    }

    @Test
    public void runGameWrongAnswer() {
        Mockito.when(randy.nextInt(5))
                .thenReturn(3);

        Mockito.when(randy.nextInt(9))
                .thenReturn(7);

        mainLoop.runGame();

        Mockito.verify(game, Mockito.times(1)).wrongAnswer();
        Mockito.verify(game, Mockito.never()).wasCorrectlyAnswered();
    }

    @Test
    public void runGameWasCorrectlyAnswered() {
        Mockito.when(randy.nextInt(5))
                .thenReturn(3);

        Mockito.when(randy.nextInt(9))
                .thenReturn(5);

        mainLoop.runGame();

        Mockito.verify(game, Mockito.times(1)).wasCorrectlyAnswered();
        Mockito.verify(game, Mockito.never()).wrongAnswer();
    }
}