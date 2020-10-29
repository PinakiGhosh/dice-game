package com.pinaki.dicegame.utils;

import com.pinaki.dicegame.pojo.Constants;
import com.pinaki.dicegame.pojo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerUtil {

    private PlayerUtil(){}

    public static List<Player> createPlayers(int count){
        List<Player> players = new ArrayList<>();
        for(int i=1;i<=count;i++){
            players.add(Player.builder()
                    .name(Constants.PLAYER_NAME_PREFIX+i)
                    .currentScore(0)
                    .build()
            );
        }
        Collections.shuffle(players);
        return players;
    }

}
