/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.ArrayList;

/**
 *
 * @author hidden
 */
public class UpdateThread extends Thread {
    private int playerId;
    private int gameId;
    private TicTacToe tictac;
    private DataBase db;
    public UpdateThread(TicTacToe tictac){
        this.tictac = tictac;
        playerId = tictac.getplayerId();
        gameId = tictac.getGameId();
        this.db = new DataBase();
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            // format move[0] = playerID ; move[1] = x; move[2] = y
            ArrayList<int[]> moves = db.getMoves(gameId);
            if (moves.isEmpty())
                continue;
            
            for (int[] move : moves)
                tictac.markField(playerId == move[0], move[1], move[2]);
            
            if (moves.get(moves.size() - 1)[0] != playerId)
                tictac.enableButtons();
            
            try {
                Thread.sleep(2500);
            } catch(Exception e){
                
            }
        }
    }
}