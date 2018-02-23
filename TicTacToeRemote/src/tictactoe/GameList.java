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
import java.util.ArrayList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author hidden
 */
public class GameList extends JFrame implements ActionListener{
    private JButton createGame;
    private GridLayout wholeScreen;
    private GridLayout labelList;
    private JPanel screenPanel;
    private JPanel labelsPanel;
    private int playerId;
    
    public GameList(int playerId) {
        this.playerId = playerId;
        DataBase db = new DataBase();
        setTitle("Open Games:");
        setBounds(50, 50, 500, 450);
        ArrayList<int[]> games = db.getGames();
        wholeScreen = new GridLayout(1, 2);
        labelList = new GridLayout(games.size() + 1, 2);
        labelsPanel = new JPanel();
        labelsPanel.setLayout(labelList);
        if (games.size() == 0){
            JLabel gameLabel = new JLabel("No open games found.");
            labelsPanel.add(gameLabel);
        }
        for (int[] game : games) {
            JLabel gameLabel = new JLabel("Game:" + String.valueOf(game[0]) +
                    " Player1:" + String.valueOf(game[1]) + " Player2:" + String.valueOf(game[2]));
            JButton join = new JButton("Join");
            join.addActionListener(new ActionListener() {
                @Override   
                public void actionPerformed(ActionEvent e) {
                    if (db.joinGame(game[0], playerId)) {
                        TicTacToe tic = new TicTacToe(game[0], playerId);
                        dispose();
                    }
                }
            });
            labelsPanel.add(gameLabel);
            labelsPanel.add(join);
        }
        screenPanel = new JPanel();
        screenPanel.setLayout(wholeScreen);
        //add(boardPanel);
        createGame = new JButton("Create Game");
        createGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int gameId = db.newGame(playerId);
                if (gameId > 0) {
                    TicTacToe tic = new TicTacToe(gameId, playerId);                    
                    dispose();
                }
            }
        });
        labelsPanel.add(createGame);
        screenPanel.add(labelsPanel);
        add(screenPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        throw new UnsupportedOperationException("asd");
    }
}
