/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.GridLayout;
import java.util.ArrayList;

/**
 *
 * @author hidden
 */
public class GameListThread extends Thread {
    private GameList gameList;
    public GameListThread(GameList gl){
        this.gameList = gl;
    }
    
    @Override
    public void run() {
        // call refresh every 10 seconds
        while (!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(10000);
            } catch(Exception e){}
            gameList.refreshGames();
        }
    }
}
