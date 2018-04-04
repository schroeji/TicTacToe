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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import tictactoe.TTTWebService;
import tictactoe.TTTWebService_Service;

/**
 *
 * @author hidden
 */
public class LoginTicTacToe extends JFrame implements ActionListener{
    private JButton clear;
    private JButton submit;
    private JTextField username;
    private JPasswordField password;
    private GridLayout wholeScreen;
    private JPanel screenPanel;
    TTTWebService_Service service = new TTTWebService_Service();
    TTTWebService proxy = service.getTTTWebServicePort();

    public LoginTicTacToe() {
        setTitle("Login");
        setBounds(50, 50, 500, 450);
        wholeScreen = new GridLayout(2, 2);
        screenPanel = new JPanel();
        screenPanel.setLayout(wholeScreen);
        //add(boardPanel);
        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override   
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                password.setText("");
            }
        });    

        submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override   
            public void actionPerformed(ActionEvent e) {
                int id = proxy.login(username.getText(), password.getText());
                if (id > 0) {
                    GameList gameList = new GameList(id, username.getText());
                    //gameList.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });  
        username = new JTextField("Username");
        password = new JPasswordField();
        screenPanel.add(username);
        screenPanel.add(password);
        screenPanel.add(clear);
        screenPanel.add(submit);
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
