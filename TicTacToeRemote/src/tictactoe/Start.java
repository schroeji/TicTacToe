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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author hidden
 */
public class Start extends JFrame implements ActionListener{
    private JButton register;
    private JButton login;

    private GridLayout wholeScreen;
    private JPanel screenPanel;

    public Start() {
        setTitle("TicTacToe");
        setBounds(50, 50, 500, 450);
        DataBase db = new DataBase();
        wholeScreen = new GridLayout(1, 2);
        screenPanel = new JPanel();
        screenPanel.setLayout(wholeScreen);
        //add(boardPanel);
        register = new JButton("Register");
        register.addActionListener(new ActionListener() {
            @Override   
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                dispose();
            }
        });    

        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override   
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                dispose();
            }
        });  
        screenPanel.add(login);
        screenPanel.add(register);
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
