package com.pinaki.dicegame.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Game {
    private int noOfPlayers;
    private int scoreToBeat;
    private List<Player> activePlayers;
    private List<Player> playersCompleted;
}
