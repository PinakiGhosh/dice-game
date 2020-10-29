package com.pinaki.dicegame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.*;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class MainTest {

    @Rule
    public TextFromStandardInputStream systemInMock = emptyStandardInputStream();
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test(expected = IllegalArgumentException.class)
    public void main_incorrectInput(){
        Main.main(new String[]{"1", "0"});
    }

    @Test
    public void main(){
        String []arr =new String[100];
        for(int i=0;i<100;i++){
            if(i%7 == 0){
                arr[i] = "Hello";
            }else{
                arr[i] = "r";
            }
        }
        int noOfPlayers = 10;
        systemInMock.provideLines(arr);
        Main.main(new String[]{noOfPlayers+"","15"});
        String []logs = systemOutRule.getLog().split("\n");
        int outputLines = logs.length;
        assertEquals("Current Leader Board",logs[outputLines-(noOfPlayers+1)]);
        for(int i=noOfPlayers;i>0;i--){
            assertTrue(logs[outputLines-i].startsWith("Rank "+(10-i+1)));
        }
    }
}
