/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hidden
 */
public class ScoreBoard extends javax.swing.JFrame {
    TTTWebService proxy = new TTTWebService_Service().getTTTWebServicePort();

    /**
     * Creates new form ScoreBoard
     */
    public ScoreBoard(int pid, String uname) {
        initComponents();
        setLocationRelativeTo(null);
        String games_str = proxy.showAllMyGames(String.valueOf(pid));
        if (games_str.equals("ERROR-NOGAMES")) {
            JOptionPane.showMessageDialog(null, "No games played so far.",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else if (games_str.equals("ERROR-DB")) {
            JOptionPane.showMessageDialog(null, "Could not connect to database.",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            String[] games_arr = games_str.split("\\n");
            String[][] games = new String[games_arr.length][];
            int i = 0;
            for (String match : games_arr){
                games[i] = match.split(",");
                i++;
            }
            int[] stats = calcStatsUser(games, uname);
            winsLbl.setText(String.valueOf(stats[0]));
            lossLbl.setText(String.valueOf(stats[1]));
            drawsLbl.setText(String.valueOf(stats[2]));
            setVisible(true);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        winsLbl = new javax.swing.JLabel();
        lossLbl = new javax.swing.JLabel();
        drawsLbl = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TicTacToe - Scoreboard");

        jLabel1.setText("Wins:");

        jLabel2.setText("Losses:");

        jLabel3.setText("Draws:");

        winsLbl.setText("0");

        lossLbl.setText("0");

        drawsLbl.setText("0");

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(winsLbl)
                            .addComponent(jLabel1))
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(lossLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(drawsLbl)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(closeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winsLbl)
                    .addComponent(lossLbl)
                    .addComponent(drawsLbl))
                .addGap(51, 51, 51)
                .addComponent(closeButton)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed
    private int[] calcStatsUser(String[][] games, String uname) {
        // calculate the stats for one user:
        // iterate through all games of the user, get the gameState and count the reuslts
        int[] result = {0, 0, 0}; // wins, losses, draws
        for (String[] game : games){
            String state_str = proxy.getGameState(Integer.valueOf(game[0]));
            String p1 = game[1];
            String p2 = game[2];
            if (state_str.equals("ERROR-NOGAME") ||
                state_str.equals("ERROR-DB")){
                System.err.println(state_str);
                continue;
            }
            int state = Integer.valueOf(state_str);
            switch(state){
                case 1:  // if first player won
                    if (p1.equals(uname)) // and user is first player
                        ++result[0]; // increment wins
                    else if (p2.equals(uname)) // user is second player
                        ++result[1]; // increment loss
                    break;
                case 2:
                    if (p1.equals(uname))
                        ++result[1];
                    else if (p2.equals(uname))
                        ++result[0];
                    break;
                case 3: //
                    ++result[2];
                    break;
                default:
                    break;
            }
        }
        return result;
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel drawsLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lossLbl;
    private javax.swing.JLabel winsLbl;
    // End of variables declaration//GEN-END:variables
}
