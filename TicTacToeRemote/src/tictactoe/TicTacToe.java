/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author hidden
 */
public class TicTacToe extends javax.swing.JFrame {

    /**
     * Creates new form TicTacToe
     */
    TTTWebService proxy = new TTTWebService_Service().getTTTWebServicePort();
    private int gameId;
    private int playerId;
    private boolean p1;
    private String uname;
    private GridLayout board;
    private JButton[][] squares = new JButton[3][3];

    public TicTacToe(int gameId, int playerId, boolean p1, String uname) {
        initComponents();
        setTitle("TicTacToe Game");
        //setBounds(50, 50, 500, 450);
        board = new GridLayout(3, 3);
        boardPanel.setLayout(board);
        this.gameId = gameId;
        this.playerId = playerId;
        this.p1 = p1;
        this.uname = uname;
        // create 3x3 buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squares[i][j] = new JButton();
                squares[i][j].setSize(150, 150);
                // add a property for each button which contains the button location
                // i.e. row and column
                squares[i][j].putClientProperty("x", i);
                squares[i][j].putClientProperty("y", j);
                squares[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // https://stackoverflow.com/questions/20818927/how-to-access-a-jframe-from-anonymous-actionlistener-for-adding-a-panel-in-frame
                        JButton button = (JButton)e.getSource();
                        int x = (int) button.getClientProperty("x");
                        int y = (int) button.getClientProperty("y");
                        // try to take square and check for errors
                        String answer = proxy.takeSquare(x, y, gameId, playerId);
                        if (answer.equals("ERROR")) {
                            JOptionPane.showMessageDialog(null, "General error.",
                                    "Error", JOptionPane.INFORMATION_MESSAGE);
                        } else if (answer.equals("ERROR-TAKEN")) {
                            JOptionPane.showMessageDialog(null, "Square already taken.",
                                    "Error", JOptionPane.INFORMATION_MESSAGE);
                        } else if (answer.equals("ERROR-DB")) {
                            JOptionPane.showMessageDialog(null, "Could not connect to database.",
                                    "Error", JOptionPane.INFORMATION_MESSAGE);
                        } else if(answer.equals("1")) {
                            // if successfull mark the field
                            markField(p1, x, y);
                            curPlayerName.setText(uname + ": It's the opponents turn.");
                            disableButtons();
                        }
                    }
                });    
                squares[i][j].setSize(200, 200);
                boardPanel.add(squares[i][j]);
            }            
        }
        if(p1) {
            // if player is first player allow him to start
            curPlayerName.setText(uname + ": It's your turn.");
            enableButtons();
        } else {
            // if not block the game until opponent took a square
            curPlayerName.setText(uname + ": It's the opponents turn.");
            disableButtons();
        }
        GameThread updThread = new GameThread(this);
        updThread.start();
        setVisible(true);
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
     
    public String getUname(){
        return this.uname;
    }
    
    public void markField(boolean p1, int x, int y) {
        // take square
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
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardPanel = new javax.swing.JPanel();
        curPlayerName = new javax.swing.JLabel();
        leaveBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        boardPanel.setPreferredSize(new java.awt.Dimension(450, 450));

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        leaveBtn.setText("Leave");
        leaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(curPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(leaveBtn)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(boardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(leaveBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(curPlayerName)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void leaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveBtnActionPerformed
        // if leaving game without finishing set game state to lost
        try {
            String win = proxy.checkWin(gameId);
            System.out.println(win);
            if (win.equals("ERROR-NOMOVES")) { // game hast not started yet
            
            } else if(win.equals("ERROR-DB")) {
                JOptionPane.showMessageDialog(null, "Could not connect to database.",
                        "Error", JOptionPane.INFORMATION_MESSAGE);
            } else if(win.equals("ERROR-NOGAME")) {
                JOptionPane.showMessageDialog(null, "Could not find game.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
            } else if(win.equals("ERROR-RETRIEVE")) {
                JOptionPane.showMessageDialog(null, "Could not game details.",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (Integer.valueOf(win) <= 0 && p1)
                    proxy.setGameState(gameId, 2);
                else if(Integer.valueOf(win) <= 0 && !p1)
                    proxy.setGameState(gameId, 1);
            }
        } catch (Exception e) {}
        GameList gl = new GameList(playerId, uname);
        dispose();
    }//GEN-LAST:event_leaveBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boardPanel;
    private javax.swing.JLabel curPlayerName;
    private javax.swing.JButton leaveBtn;
    // End of variables declaration//GEN-END:variables
}
