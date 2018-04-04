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
public class GameThread extends Thread {
    private int playerId;
    private int gameId;
    private boolean p1;
    private TicTacToe tictac;
    TTTWebService_Service service = new TTTWebService_Service();
    TTTWebService proxy = service.getTTTWebServicePort();
    public GameThread(TicTacToe tictac){
        this.tictac = tictac;
        playerId = tictac.getplayerId();
        gameId = tictac.getGameId();
        p1 = tictac.getP1();
    }
    
    @Override
    public void run() {
        boolean end = false;
        while (!Thread.currentThread().isInterrupted() && !end){
            try {
                Thread.sleep(1000);
            } catch(Exception e){
                
            }
            // format move[0] = playerID ; move[1] = x; move[2] = y
            String board = proxy.getBoard(gameId);
            if (board.equals("ERROR-NOMOVES") || 
                board.equals("ERROR-DB"))
                continue;
            
            String[] board_arr = board.split("\\n");
            
            for (String move_str : board_arr) {
                String[] move = move_str.split(",");
                if (playerId == Integer.valueOf(move[0]))
                    tictac.markField(p1, Integer.valueOf(move[1]), Integer.valueOf(move[2]));
                else
                    tictac.markField(!p1, Integer.valueOf(move[1]), Integer.valueOf(move[2]));
            }
            // find id of player who played the last move
            String[] last_move = board_arr[board_arr.length - 1].split(",");
            int last_player = Integer.valueOf(last_move[0]);
            if (tictac.hasFinished()){
                tictac.setCurPlayerText("Game Over");
                tictac.disableButtons();
                end = true;
                if (tictac.hasWon(true))
                    proxy.setGameState(gameId, 1);
                else if (tictac.hasWon(false))
                    proxy.setGameState(gameId, 2);
                else 
                    proxy.setGameState(gameId, 3);
            } else if (last_player != playerId){   
                tictac.enableButtons();
                tictac.setCurPlayerText("It's your turn.");
            }
            
        }
    }
}
