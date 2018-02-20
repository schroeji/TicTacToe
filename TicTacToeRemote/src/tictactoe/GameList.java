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
    private ArrayList<JButton> joinButtons = new ArrayList<JButton>();
    private GridLayout wholeScreen;
    private GridLayout labelList;
    private JPanel screenPanel;
    private JPanel labelsPanel;

    private ArrayList<JLabel> gameLabels = new ArrayList<JLabel>();
    public GameList() {
        setTitle("Open Games:");
        setBounds(50, 50, 500, 450);
        
        wholeScreen = new GridLayout(1, 2);
        screenPanel = new JPanel();
        screenPanel.setLayout(wholeScreen);
        //add(boardPanel);
        createGame = new JButton("Create Game");           
        screenPanel.add(createGame);
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
