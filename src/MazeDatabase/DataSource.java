package MazeDatabase;

import MazeGenerator.Maze;

import java.io.*;
import java.sql.*;

/**
 * Class that holds data in mazeData database.
 */
public class DataSource {

    private Connection connection;
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS mazeData ("
                    + "name VARCHAR(45),"
                    + "author VARCHAR(45),"
                    + "difficulty INT(30),"
                    + "maze LONGBLOB"
                    + ");";

    /**
     * Constructor initializes the connection to that executes the query statement
     */
    public DataSource() {
        connection = DatabaseConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Convert object from binary to bytes
     * Conversion method taking an object and converting to an array of byte for sending objects to database
     * @param obj
     * @return
     * @throws IOException
     */
    public byte[] convertToBytes(Object obj) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(obj);
        byte[] data = byteStream.toByteArray();
        return data;
    }

    /**
     * Adds new maze in data source
     * @param maze
     * @throws SQLException
     * @throws IOException
     */
    public void addNewMaze( Maze maze) throws SQLException, IOException {
        System.out.println(maze.getMazeName());
        Connection dataConnect = new DatabaseConnection().getInstance();
        PreparedStatement statement = dataConnect.prepareStatement("INSERT INTO mazeData VALUES(?,?,?,?)");
        byte[] mazeData = convertToBytes(maze);

        statement.setString(1,maze.getMazeName());
        statement.setString(2,maze.getMazeAuthor());
        statement.setInt(3,maze.getMazeDifficulty());
        statement.setBinaryStream(4,new ByteArrayInputStream(mazeData), mazeData.length);

        statement.execute();
        statement.close();
    }

}
