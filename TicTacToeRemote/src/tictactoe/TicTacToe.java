/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.lang.UnsupportedOperationException;

/**
 *
 * @author hidden
 */
public class TicTacToe extends JFrame implements ActionListener{
    private JButton[][] squares = new JButton[3][3];
    private JButton newGame;
    private JButton login;
    private JLabel results;
    private JLabel curPlayerName;
    private GridLayout wholeScreen;
    private GridLayout board;
    private JPanel boardPanel;
    private JPanel menuPanel = new JPanel();
    private JPanel screenPanel;
    private int gameId;
    private int playerId;
    private boolean p1;
    
    public TicTacToe(int gameId, int playerId, boolean p1) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.p1 = p1;
        setTitle("TicTacToe Game");
        setBounds(50, 50, 500, 450);
        wholeScreen = new GridLayout(1, 2);
        board = new GridLayout(3, 3);
        boardPanel = new JPanel();
        screenPanel = new JPanel();
        DataBase db = new DataBase();
        boardPanel.setLayout(board);
        screenPanel.setLayout(wholeScreen);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squares[i][j] = new JButton();
                // adpated from https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing
                squares[i][j].putClientProperty("x", i);
                squares[i][j].putClientProperty("y", j);
                squares[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // https://stackoverflow.com/questions/20818927/how-to-access-a-jframe-from-anonymous-actionlistener-for-adding-a-panel-in-frame
                        JButton button = (JButton)e.getSource();
                        int x = (int) button.getClientProperty("x");
                        int y = (int) button.getClientProperty("y");
                        if(db.playMove(gameId, playerId, x, y)) {
                            markField(p1, x, y);
                            curPlayerName.setText("It's the opponents turn.");
                            disableButtons();
                        }
                    }
                });    
                squares[i][j].setSize(200, 200);
                boardPanel.add(squares[i][j]);
            }            
        }
        screenPanel.add(boardPanel);
        //add(boardPanel);
        menuPanel.setLayout(new BorderLayout());
        newGame = new JButton("Resign/Restart");
        newGame.setSize(50, 50);
        if(p1)
            curPlayerName = new JLabel("It's your turn.");
        else
            curPlayerName = new JLabel("It's the opponents turn.");
        curPlayerName.setSize(50, 50);
        results = new JLabel(" : : ");
        menuPanel.add(newGame, BorderLayout.NORTH);
        menuPanel.add(results, BorderLayout.CENTER);
        menuPanel.add(curPlayerName, BorderLayout.SOUTH);
        screenPanel.add(menuPanel);
        add(screenPanel);
        pack();
        if(!p1)
            disableButtons();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UpdateThread updThread = new UpdateThread(this);
        updThread.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        throw new UnsupportedOperationException("asd");
    }
    
    public int getGameId(){
        return this.gameId;
    }
    
    public boolean getP1() {
        return p1;
    }
    
    public int getplayerId(){
        return this.playerId;
    }
     
    public void markField(boolean p1, int x, int y) {
        if (p1) {
            squares[x][y].setText("X");
        } else {
            squares[x][y].setText("O");
        }
    }
    
    public void setCurPlayerText(String t) {
        curPlayerName.setText(t);
    }
    
    public void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squares[i][j].setEnabled(false);
            }
        }
    }
    
    public void enableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(squares[i][j].getText().equals(""))
                    squares[i][j].setEnabled(true);
            }
        }
    }
    
    public boolean hasFinished() {
        if (hasWon(true)) {
            System.out.println("P1 won");
            return true;
        }
        if (hasWon(false)) {
            System.out.println("P2 won");
            return true;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(squares[i][j].getText().equals("")){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasWon(boolean p1) {
        String p = p1 ? "X" : "O";
        // check lines
        for (int i = 0; i < 3; i++) {
            boolean winningLine = true;
            for (int j = 0; j < 3; j++) {
                if(!squares[i][j].getText().equals(p)){
                    winningLine = false;
                    break;
                }
            }
            if (winningLine)
                return true;
        }
        // check columns
        for (int i = 0; i < 3; i++) {
            boolean winningCol = true;
            for (int j = 0; j < 3; j++) {
                if(!squares[j][i].getText().equals(p)){
                    winningCol = false;
                    break;
                }
            }
            if (winningCol)
                return true;
        }
        // check diagonal
        for (int i = 0; i < 3; i++) {
            boolean winningDiag = true;
            if(!squares[i][i].getText().equals(p)){
                winningDiag = false;
                break;
            }
            if (winningDiag)
                return true;
        }
        // check other diag
        for (int i = 0; i < 3; i++) {
            boolean winningDiag = true;
            if(!squares[i][2-i].getText().equals(p)){
                winningDiag = false;
                break;
            }
            if (winningDiag)
                return true;
        }
        return false;
    }
}
