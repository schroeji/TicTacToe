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
import javax.swing.JOptionPane;
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
    JLabel gameLabel;
    private int playerId;
    TTTWebService proxy = new TTTWebService_Service().getTTTWebServicePort();
    
    public GameList(int playerId) {
        this.playerId = playerId;
        setTitle("Open Games:");
        setBounds(50, 50, 500, 450);
        wholeScreen = new GridLayout(1, 2);

        String games_string = proxy.showOpenGames();
        System.out.println(games_string);
        labelsPanel = new JPanel();
        if (games_string.equals("ERROR-NOGAMES")) {
            labelList = new GridLayout(1, 2);     
            labelsPanel.setLayout(labelList);
            gameLabel = new JLabel("No open games found");
            labelsPanel.add(gameLabel);
        } else if (games_string.equals("ERROR-DB")) {
            labelList = new GridLayout(1, 2);     
            labelsPanel.setLayout(labelList);
            gameLabel = new JLabel("Database Error");
            labelsPanel.add(gameLabel);
        } else {
            String[] games_arr = games_string.split("\\n");
            labelList = new GridLayout(games_arr.length + 1, 2);     
            labelsPanel.setLayout(labelList);
            for (String game : games_arr) {
                String[] game_spl = game.split(",");
                int gid = Integer.valueOf(game_spl[0]);
                JLabel gameLabel = new JLabel("Game:" + gid +
                        " Player:" + game_spl[1] + " State:" + game_spl[2]);
                JButton join = new JButton("Join");
                join.addActionListener(new ActionListener() {
                    @Override   
                    public void actionPerformed(ActionEvent e) {
                        if (proxy.joinGame(playerId, Integer.valueOf(gid)).equals("1")) {
                            TicTacToe tic = new TicTacToe(gid, playerId, false);
                            dispose();
                        } 
                    }
                });
                labelsPanel.add(gameLabel);
                labelsPanel.add(join);
            }
        }
        screenPanel = new JPanel();
        screenPanel.setLayout(wholeScreen);
        //add(boardPanel);
        createGame = new JButton("Create Game");
        createGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_str = proxy.newGame(playerId);
                if (id_str.equals("ERROR-NOTFOUND")){
                    JOptionPane.showMessageDialog(null, "Could not find game id.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (id_str.equals("ERROR-INSERT")) {
                    JOptionPane.showMessageDialog(null, "Could not insert game.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (id_str.equals("ERROR-DB")) {
                    JOptionPane.showMessageDialog(null, "Could not connect to database.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (id_str.equals("ERROR-RETRIEVE")) {
                    JOptionPane.showMessageDialog(null, "Could not access games table.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int gid = Integer.valueOf(id_str);
                if (gid > 0) {
                    TicTacToe tic = new TicTacToe(gid, playerId, true);                    
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
