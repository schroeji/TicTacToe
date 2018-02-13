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

    public TicTacToe() {
        setTitle("TicTacToe Game");
        setBounds(50, 50, 500, 450);
        
        wholeScreen = new GridLayout(1, 2);
        board = new GridLayout(3, 3);
        boardPanel = new JPanel();
        screenPanel = new JPanel();

        boardPanel.setLayout(board);
        screenPanel.setLayout(wholeScreen);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squares[i][j] = new JButton();
                // adpated from https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing
                squares[i][j].addActionListener(new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton)e.getSource();
                        button.setText("X");
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
        curPlayerName = new JLabel("It's Players move");
        curPlayerName.setSize(50, 50);
        results = new JLabel(" : : ");
        menuPanel.add(newGame, BorderLayout.NORTH);
        menuPanel.add(results, BorderLayout.CENTER);
        menuPanel.add(curPlayerName, BorderLayout.SOUTH);
        screenPanel.add(menuPanel);
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
