package com.pinaki.dicegame.utils;

import com.pinaki.dicegame.pojo.Game;
import com.pinaki.dicegame.pojo.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GameUtilTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void createGame_valid(){
        int noOfPlayers = 3;
        int scoreToBeat = 10;
        Game game= GameUtil.createGame(noOfPlayers,scoreToBeat);
        assertNotNull(game);
        assertEquals(noOfPlayers,game.getNoOfPlayers());
        assertEquals(scoreToBeat,game.getScoreToBeat());
        assertEquals(noOfPlayers,game.getActivePlayers().size());
        assertNotNull(game.getPlayersCompleted());
        assertEquals(0,game.getPlayersCompleted().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGame_invalidInput(){
        int noOfPlayers = -1;
        int scoreToBeat = 0;
        GameUtil.createGame(noOfPlayers,scoreToBeat);
    }

    @Test
    public void rollDice_limits(){
        for(int i=0;i<1000;i++){
            int z = GameUtil.rollDice();
            assertTrue(z > 0);
            assertTrue(z < 7);
        }
    }

    @Test
    public void printLeaderBoard_allPlaying(){
        Game game = createDummyGame();
        String expectedLog = "Current Leader Board\nRank 1. P1\nRank 2. P2\nRank 3. P3\n";
        GameUtil.printLeaderBoard(game);
        assertEquals(expectedLog,systemOutRule.getLog());
    }

    @Test
    public void printLeaderBoard_someCompleted(){
        Game game = GameUtil.createGame(3,10);
        Player p1 = Player.builder().name("P1").build();
        Player p2 = Player.builder().name("P2").build();
        Player p3 = Player.builder().name("P3").build();

        List<Player> players = Arrays.asList(p3,p1);
        game.setActivePlayers(players);
        players = Collections.singletonList(p2);
        game.setPlayersCompleted(players);

        String expectedLog = "Current Leader Board\nRank 1. P2\nRank 2. P3\nRank 3. P1\n";
        GameUtil.printLeaderBoard(game);
        assertEquals(expectedLog,systemOutRule.getLog());
    }

    @Test
    public void updateGame_noWin(){
        Game game = createDummyGame();
        assertFalse(GameUtil.updateGame(game,0));
    }

    @Test
    public void updateGame_win(){
        Game game = createDummyGame();
        Player playerInTest = game.getActivePlayers().get(0);
        playerInTest.setCurrentScore(12);
        assertTrue(GameUtil.updateGame(game,0));
    }

    private static Game createDummyGame(){
        Game game = GameUtil.createGame(3,10);
        Player p1 = Player.builder().name("P1").build();
        Player p2 = Player.builder().name("P2").build();
        Player p3 = Player.builder().name("P3").build();
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        game.setActivePlayers(players);
        game.setPlayersCompleted(new ArrayList<>());
        return game;
    }
}
