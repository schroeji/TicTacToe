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
    private String uname;
    TTTWebService_Service service = new TTTWebService_Service();
    TTTWebService proxy = service.getTTTWebServicePort();
    
    
    public GameThread(TicTacToe tictac){
        this.tictac = tictac;
        this.uname = tictac.getUname();
        playerId = tictac.getplayerId();
        gameId = tictac.getGameId();
        p1 = tictac.getP1();
    }
    
    @Override
    public void run() {
        boolean end = false;
        while (!Thread.currentThread().isInterrupted() && !end){
            try {
                Thread.sleep(500);
            } catch(Exception e){
                
            }
            // get all moves played so fat
            String board = proxy.getBoard(gameId);
            if (board.equals("ERROR-NOMOVES") || 
                board.equals("ERROR-DB"))
                continue;
            // iterate through all moves and draw them on the board
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
            String end_str = proxy.checkWin(gameId);
            if (end_str.equals("ERROR-NOGAME") || 
                end_str.equals("ERROR-RETRIEVE")|| 
                end_str.equals("ERROR-DB")){
                System.err.println(end_str);
                continue;
                
            }
            int endState = Integer.valueOf(end_str);
            
            if (endState != 0) {
                // if game is over: set game state and show message
                tictac.disableButtons();
                end = true;
                proxy.setGameState(gameId, endState);
                if ((endState == 1 && p1) || (endState == 2 && !p1)) 
                    tictac.setCurPlayerText("Game Over: You won!");
                else if ((endState == 2 && p1) || (endState == 1 && !p1)) 
                    tictac.setCurPlayerText("Game Over: You Lost!");
                else if (endState == 3)
                    tictac.setCurPlayerText("Game Over: It's a tie!");
            } else if (last_player != playerId){   
                // if not over and other player has played a move 
                tictac.enableButtons();
                tictac.setCurPlayerText(this.uname + ": It's your turn.");
            }
            // if other player has not played a move wait longer
            
        }
    }
}
