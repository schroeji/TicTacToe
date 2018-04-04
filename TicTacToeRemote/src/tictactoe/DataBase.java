/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hidden
 */
public class DataBase {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public DataBase() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost/week4?"
                + "user=sqluser&password=sqluserpw");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public ArrayList<int[]> getGames() {
        // Statements allow to issue SQL queries to the database
        ArrayList<int[]> games = new ArrayList<int[]>();
        try {
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from games where gameState='-1';");
            
            while(resultSet.next()) {
                int[] game = {resultSet.getInt("autoID"), 
                    resultSet.getInt("p1"),
                    resultSet.getInt("p2"),
                    resultSet.getInt("gameState")};
                games.add(game);
            }
            
        } catch(Exception e) {
            System.err.println(e);
        }
        return games;
    }
    
    public int checkUser(String username, String password) {
        try {
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select autoID from users where username='" + username +"' and password=PASSWORD('" + password + "')");
            resultSet.next();
            int id = resultSet.getInt("autoID");
            return id;
        } catch(Exception e) {
            //System.err.print(e);
            return -1;
        }
    }
    
    public int addUser(String name, String surname, String email, String username, String password) {
        try {
            // Result set get the result of the SQL query
            preparedStatement = connect.prepareStatement("insert into users(name, surname, email, username, password) "
                    + "values(?,?,?,?,PASSWORD(?))", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            //System.out.println(resultSet.getInt(1));
            return resultSet.getInt(1);
                    
        } catch(Exception e) {
            System.err.print(e);
            return -1;
        }
    }
    
    public int newGame(int p1) {
        try {
            // creates a new Game with the player as p1
            preparedStatement = connect.prepareStatement("insert into games(p1, p2, gameState) "
                    + "values(?,1,-1)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, p1);
            //preparedStatement.setInt(2, p2);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch(Exception e) {
            System.err.print(e);
            return -1;
        }
    }
    
    public boolean joinGame(int gameId, int playerId) {
        try {
            // Update databse entry of game with gameId and set p2 to playerId
            preparedStatement = connect.prepareStatement("update games "
                    + "SET p2=?, gameState=? where autoID=?");
            preparedStatement.setInt(1, playerId);
            // maybe use another game state number here
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, gameId);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.err.print(e);
            return false;
        }
    }
    
    public boolean setGameState(int gameID, int gs) {
        try {
            // Update databse entry of game with gameId and set p2 to playerId
            preparedStatement = connect.prepareStatement("update games "
                    + "SET gameState=? where autoID=?");
            preparedStatement.setInt(1, gs);
            // maybe use another game state number here
            preparedStatement.setInt(2, gameID);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.err.print(e);
            return false;
        }
    }
    
    public boolean playMove(int gameId, int playerId, int x, int y) {
        try {
            // maybe add additional check if move is valid here
            preparedStatement = connect.prepareStatement("insert into moves(gID, pID, x, y) "
                    + "values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, playerId);
            preparedStatement.setInt(3, x);
            preparedStatement.setInt(4, y);
            preparedStatement.executeUpdate();
            return true;
        } catch(Exception e) {
            System.err.print(e);
            return false;
        }
    }
    
    public ArrayList<int[]> getMoves(int gameId) {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        try {
            // maybe add additional check if move is valid here
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from moves where gID='"
                   + String.valueOf(gameId) + "';");
            while(resultSet.next()) {
                int[] move = {
                    resultSet.getInt("pID"),
                    resultSet.getInt("x"),
                    resultSet.getInt("y"),
                };
                moves.add(move);
            }
        } catch(Exception e) {
            System.err.print(e);
        }
        return moves;
    }
    
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }
}
