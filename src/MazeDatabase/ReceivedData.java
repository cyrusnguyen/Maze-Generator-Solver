package MazeDatabase;

import MazeGenerator.Maze;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author Michael Fox
 * Class handling all retrieval and local storage of information
 */

public class ReceivedData {
    private Connection connection;
    DefaultListModel<Maze> mazeList = new DefaultListModel<Maze>();

    /**
     * Constructer method to instantiate a new object to retrieve and hold data from a database
     */
    public ReceivedData(){
        DatabaseConnection data = new DatabaseConnection();
         this.connection = data.getInstance();
    }

    /**
     * Conversion method taking an array of bytes and converting to an object. Used to retrieved stored objects from database
     * @param bytes an array of bytes taken from database
     * @return an object extracted from database
     */
    public Object convertFromBytes(byte[] bytes)  {
        ByteArrayInputStream inputBytes = new ByteArrayInputStream(bytes);
        ObjectInputStream inputObjects = null;
        try {
            inputObjects = new ObjectInputStream(inputBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object ob = null;
        try {
            ob = inputObjects.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ob;
    }

    /**
     * Searches database table for rows that have the name matching the parameter. Stores the retrieved maze in global List 'mazeList'
     * @param mazeName the queried name to be found
     */
    public void getMazeByName(String mazeName) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM mazeData WHERE name="+"'"+mazeName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Maze maze = null;
            try {
                maze = (Maze) convertFromBytes(rs.getBytes("maze"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.mazeList.addElement(maze);
        }
    }

    /**
     * Searches database table for rows that have the author matching the parameter. Stores the retrieved maze in global List 'mazeList'
     * @param queriedAuthor the queried author to be found
     */
    public void getMazeByAuthor(String queriedAuthor) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM mazeData WHERE author="+"'"+queriedAuthor+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Maze maze = null;
            try {
                maze = (Maze) convertFromBytes(rs.getBytes("maze"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.mazeList.addElement(maze);
        }
    }

    /**
     * Searches database table for rows that have the difficulty matching the parameter. Stores the retrieved maze in global List 'mazeList'
     * @param mazeDifficulty the queried difficulty to be found
     */
    public void getMazeByDifficulty(int mazeDifficulty){
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM mazeData WHERE difficulty="+mazeDifficulty);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Maze maze = (Maze) convertFromBytes(rs.getBytes("maze"));
                System.out.println(maze.getMazeName());
                mazeList.addElement(maze); ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves all mazes stored within the database. Stores the retrieved maze in global List 'mazeList'
     */
    public void getAllData(){
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM mazeData");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Maze maze = (Maze) convertFromBytes(rs.getBytes("maze"));
                System.out.println(maze.getMazeName());
                mazeList.addElement(maze); ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Get method
     * @return the global parameter holding any stored mazes, retrieved from the database
     */
    public DefaultListModel<Maze> getList(){
        return this.mazeList;
    }
}
