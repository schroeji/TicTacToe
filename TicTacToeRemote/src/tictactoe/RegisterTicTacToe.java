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

/**
 *
 * @author hidden
 */
public class RegisterTicTacToe extends JFrame implements ActionListener{
    private JButton clear;
    private JButton submit;
    private JTextField name;
    private JTextField surname;
    private JTextField email;
    private JTextField username;
    private JPasswordField password;
    private GridLayout wholeScreen;
    private JPanel screenPanel;
    TTTWebService_Service service = new TTTWebService_Service();
    TTTWebService proxy = service.getTTTWebServicePort();
    public RegisterTicTacToe() {
        setTitle("Register new account");
        setBounds(50, 50, 500, 450);
        wholeScreen = new GridLayout(2, 4);
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
                String id_str = proxy.register(username.getText(), password.getText(),
                                               name.getText(), surname.getText());
                if (id_str.equals("ERROR-REPEAT")){
                    JOptionPane.showMessageDialog(null, "Username already exists.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (id_str.equals("ERROR-INSERT")) {
                    JOptionPane.showMessageDialog(null, "Could not insert user.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (id_str.equals("ERROR-DB")) {
                    JOptionPane.showMessageDialog(null, "Could not connect to database.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (id_str.equals("ERROR-RETRIEVE")) {
                    JOptionPane.showMessageDialog(null, "Could not retrieve user.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int id = Integer.valueOf(id_str);
                if (id > 0) {
                    GameList gl = new GameList(id);
                    dispose();
                }
            }
        }); 
        name = new JTextField("Name");
        surname = new JTextField("Surname");
        email = new JTextField("E-Mail");
        username = new JTextField("Username");
        password = new JPasswordField();
        screenPanel.add(name);
        screenPanel.add(surname);
        screenPanel.add(email);
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
