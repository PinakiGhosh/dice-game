package com.pinaki.dicegame;

import com.pinaki.dicegame.pojo.Game;
import com.pinaki.dicegame.pojo.Player;
import com.pinaki.dicegame.utils.GameUtil;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int numberOfPlayers = Integer.parseInt(args[0]);
        int scoreToBeat = Integer.parseInt(args[1]);
        Game game = GameUtil.createGame(numberOfPlayers,scoreToBeat);
        int playerTurn = 0, diceRoll;
        Scanner sc = new Scanner(System.in);
        System.out.println("Order of play is");
        System.out.println(game.getActivePlayers().stream().map(Player::getName).collect(Collectors.joining(",")));
        while (!game.getActivePlayers().isEmpty()){
            Player currentPlayer = game.getActivePlayers().get(playerTurn);

            if(currentPlayer.isSkipTurn()){
                currentPlayer.setSkipTurn(false);
                playerTurn = (playerTurn + 1) % game.getActivePlayers().size();
            }else{
                System.out.printf("%s its your turn (press 'r' to roll dice) \n",currentPlayer.getName());
                while (!sc.next().equalsIgnoreCase("r")){
                    System.out.println("Please enter r to roll dice");
                }

                diceRoll = GameUtil.rollDice();
                System.out.printf("%s rolled %d \n",currentPlayer.getName(),diceRoll);
                currentPlayer.addRoll(diceRoll);

                boolean playerCompleted = GameUtil.updateGame(game, playerTurn);
                if(playerCompleted){
                    System.out.printf("Congraturations %s you have completed the game!! Your rank is %s \n",currentPlayer.getName(),
                            formatRank(game.getPlayersCompleted().size()));
                    if(game.getActivePlayers().size() > 0){
                        playerTurn = playerTurn % game.getActivePlayers().size();
                    }
                }else {
                    if(diceRoll == 6){
                        System.out.printf("Its a 6!! %s roll again \n",currentPlayer.getName());
                    }else {
                        playerTurn = (playerTurn + 1) % game.getActivePlayers().size();
                    }
                    if(currentPlayer.isSkipTurn()){
                        System.out.printf("Oh oh! %s rolled two consecutive 1s. You will miss your next turn. \n",currentPlayer.getName());
                    }
                }
                GameUtil.printLeaderBoard(game);
            }
        }
    }

    private static String formatRank(int rank){
        switch (rank){
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            default:
                return rank+"th";
        }
    }
}
