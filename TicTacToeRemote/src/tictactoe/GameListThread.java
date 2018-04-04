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
        boolean end = false;
        while (!Thread.currentThread().isInterrupted() && !end){
            try {
                Thread.sleep(1000);
            } catch(Exception e){}
            gameList.refreshGames();
        }
    }
}
