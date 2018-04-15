/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hidden
 */
public class LeaderBoard extends javax.swing.JFrame {
    TTTWebService proxy = new TTTWebService_Service().getTTTWebServicePort();
    /**
     * Creates new form LeaderBoard
     */
    public LeaderBoard() {
        initComponents();
        setLocationRelativeTo(null);
        // get all games or catch errors
        String league_str = proxy.leagueTable();
        if (league_str.equals("ERROR-NOGAMES")) {
            JOptionPane.showMessageDialog(null, "No games played so far.",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else if (league_str.equals("ERROR-DB")) {
            JOptionPane.showMessageDialog(null, "Could not connect to database.",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            // prepare string
            String[] league_arr = league_str.split("\\n");
            String[][] league = new String[league_arr.length][];
            int i = 0;
            for (String match : league_arr){
                league[i] = match.split(",");
                i++;
            }
            // calculate stats and show table
            String[][] stats = calcStats(league);
            String[] columns = {"Username", "Wins", "Losses", "Draws"};
            DefaultTableModel dataSet = new DefaultTableModel(stats, columns);
            leaderBoard.setModel(dataSet);
            leaderBoard.repaint();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        leaderBoard = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TicTacToe - Leaderboard");

        leaderBoard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Wins", "Losses", "Draws"
            }
        ));
        jScrollPane1.setViewportView(leaderBoard);

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(closeButton)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private String[][] calcStats(String[][] games) {
        // calculate stats for all users:
        // iterate thorugh all games get the result and count the wins/losses
        HashMap<String, Integer[]> stats = new HashMap<>();
        for (String match[] : games) {
            String u1 = match[1];
            String u2 = match[2];
            int gameState = Integer.valueOf(match[3]);
            // create entries if the don't exist
            if (!stats.containsKey(u1)) {
                Integer[] tmp = {0,0,0};
                stats.put(u1,tmp);
            }
            if (!stats.containsKey(u2)){
                Integer[] tmp = {0,0,0};
                stats.put(u2,tmp);
            }
            // increment the counters according to the result
            if (gameState == 1) {// player1 won
                Integer[] tmp = stats.get(u1);
                ++tmp[0];
                stats.put(u1,tmp);
                tmp = stats.get(u2);
                ++tmp[1];
                stats.put(u2,tmp);
            } else if (gameState == 2) { //player2 won
                Integer[] tmp = stats.get(u1);
                ++tmp[1];
                stats.put(u1,tmp);
                tmp = stats.get(u2);
                ++tmp[0];
                stats.put(u2,tmp);
            } else if (gameState == 3) { //tie
                Integer[] tmp = stats.get(u1);
                ++tmp[2];
                stats.put(u1,tmp);
                tmp = stats.get(u2);
                ++tmp[2];
                stats.put(u2,tmp);
            }
        }
        // convert back to String[][]
        String[][] result = new String[stats.size()][4];
        int i = 0;
        for (String user : stats.keySet()) {
            Integer[] tmp = stats.get(user);
            String[] tmp_str = {user, String.valueOf(tmp[0]), String.valueOf(tmp[1]),
                                String.valueOf(tmp[2])};
            result[i] = tmp_str;
            i++;
        }
        return result;
    }
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable leaderBoard;
    // End of variables declaration//GEN-END:variables
}
