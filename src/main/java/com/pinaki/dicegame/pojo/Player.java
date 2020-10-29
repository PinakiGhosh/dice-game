package com.pinaki.dicegame.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player implements Comparable<Player>{
    private String name;
    private int currentScore;
    private int lastRoll;
    private boolean skipTurn = false;

    public void addRoll(int rollValue){
        currentScore+=rollValue;
        skipTurn = (rollValue == 1) && (lastRoll == 1);
        lastRoll = rollValue;
    }

    @Override
    public int compareTo(Player player) {
        return Integer.compare(currentScore,player.getCurrentScore());
    }
}
