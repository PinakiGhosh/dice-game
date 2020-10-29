package com.pinaki.dicegame.utils;

import com.pinaki.dicegame.pojo.Constants;
import com.pinaki.dicegame.pojo.Game;
import com.pinaki.dicegame.pojo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameUtil {

    private GameUtil(){}

    public static Game createGame(int noOfPlayers,int scoreToBeat){
        if(noOfPlayers > 0 && scoreToBeat > 0){
            return Game.builder()
                    .noOfPlayers(noOfPlayers)
                    .scoreToBeat(scoreToBeat)
                    .activePlayers(PlayerUtil.createPlayers(noOfPlayers))
                    .playersCompleted(new ArrayList<>())
                    .build();
        }else{
            throw new IllegalArgumentException("Arguments need to be positive integers");
        }
    }

    public static int rollDice(){
        Random random = new Random();
        return random.ints(Constants.DICE_MIN_VALUE, Constants.DICE_MAX_VALUE+1)
                .findFirst()
                .getAsInt();

    }

    public static void printLeaderBoard(Game game){
        int count = 1;
        System.out.println("Current Leader Board");
        for(Player player : game.getPlayersCompleted()){
            System.out.printf("Rank %d. %s\n",count++,player.getName());
        }
        List<Player> activePlayers = new ArrayList<>(game.getActivePlayers());
        activePlayers.sort(Collections.reverseOrder());
        for(Player player : activePlayers){
            System.out.printf("Rank %d. %s\n",count++,player.getName());
        }
    }

    public static boolean updateGame(Game game, int i){
        List<Player> activePlayers = game.getActivePlayers();
        Player currentPlayer = activePlayers.get(i);
        boolean playerCompleted = false;
        if(currentPlayer.getCurrentScore() >= game.getScoreToBeat()){
            activePlayers.remove(i);
            game.setActivePlayers(activePlayers);
            game.getPlayersCompleted().add(currentPlayer);
            playerCompleted = true;
        }
        return playerCompleted;
    }
}
