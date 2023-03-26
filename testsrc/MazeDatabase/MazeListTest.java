package MazeDatabase;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


public class MazeListTest {

    MazeList Maze;

    /** Test Case 0
     *  Construct a MazeList object
     */
    @BeforeEach
    @Test
    public void setupMazeList(){
        Maze = new MazeList(new MazeDatabaseMock());
    }

    /** Test Case 1
     *  Adding a new maze with author and maze name to the list
     */
    @Test
    public void addMaze() throws MazeListException{
        Maze.addMaze("Maze 1","Sam");
        Maze.addMaze("Maze 2","Alex");
        Maze.addMaze("Cool Maze","Jenny");
        assertEquals("Sam",Maze.getMazeAuthor("Maze 1"),"Failed to add maze.");
        assertEquals("Alex",Maze.getMazeAuthor("Maze 2"),"Failed to add maze.");
        assertEquals("Jenny",Maze.getMazeAuthor("Cool Maze"),"Failed to add maze.");
    }

    /** Test Case 2
     *  Getting a printable list of maze names in alphabetical order
     */
    @Test
    public void listMazesAlphabetically() throws MazeListException{
        String mazeList = "A nice maze\n"+"Best maze ever\n"+"This is meh\n"+"What a maze\n";
        Maze.addMaze("What a maze","Mike");
        Maze.addMaze("Best maze ever","Alice");
        Maze.addMaze("A nice maze","James");
        Maze.addMaze("This is meh","Johnny");
        assertEquals(mazeList, Maze.getMazeList(), "Listing not correct.");
    }


    /** Test Case 3
     *  Can't add a maze with the same name twice
     */
    @Test
    public void addMazeSameName() {
        assertThrows(MazeListException.class, () ->{
            Maze.addMaze("A new maze","Jason");
            Maze.addMaze("A new maze","Jason");
        });
    }

    /** Test Case 4
     *  Can't get the author for a nonexistent maze
     */
    @Test
    public void nonExistingMaze(){
        assertThrows(MazeListException.class, () -> {
            Maze.getMazeAuthor("A well-made maze.");
        });
    }

    /** Test Case 5
     *  Can't set author name for a maze that is not in database
     */
    @Test
    public void setAuthorForNonexistentMaze(){
        assertThrows(MazeListException.class, () -> {
            Maze.setMazeAuthor("The Maze Runner", "Chris");
        });
    }

    /** Test Case 6
     *  Getting a printable list of authors in alphabetical order
     */
    @Test
    public void listAuthorsAlphabetically() throws MazeListException{
        String authorList = "Maze Name: Best maze ever     Author: Alice\n"
                +"Maze Name: A nice maze     Author: James\n"
                +"Maze Name: This is meh     Author: Johnny\n"
                +"Maze Name: What a maze     Author: Mike\n";
        Maze.addMaze("What a maze","Mike");
        Maze.addMaze("Best maze ever","Alice");
        Maze.addMaze("A nice maze","James");
        Maze.addMaze("This is meh","Johnny");
        assertEquals(authorList,Maze.getAuthorList(),"Listing incorrect.");
    }

    /** Test Case 7
     *  Retrieve the list of mazes created by an author
     */
    @Test
    public void getCreationDate()throws MazeListException{
        String mazeList = "[Best maze ever, This is meh]";
        Maze.addMaze("What a maze","James");
        Maze.addMaze("Best maze ever","Alice");
        Maze.addMaze("A nice maze","James");
        Maze.addMaze("This is meh","Alice");
        assertEquals(mazeList,Maze.getMazeName("Alice"),"Author not found.");
    }


    /** Test Case 8
     *  Add date of creation of maze when added to database
     */
    @Test
    public void addCreationDate()throws MazeListException{
        DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        Date currentDateTime = Calendar.getInstance().getTime();
        String currentDateTimeString = dateFormat.format(currentDateTime);
        Maze.addMaze("The Maze Runner","Eric");
        assertEquals(currentDateTimeString,Maze.getCreationDate("The Maze Runner"),
                "Incorrect date.");
    }

    /** Test Case 9
     *  Can't add date of creation for the same maze twice
     */
    @Test
    public void addCreationDateTimeTwice(){
        assertThrows(MazeListException.class, () -> {
            Maze.addMaze("The Maze Runner","Eric");
            Maze.addMaze("The Maze Runner","Eric");
        });
    }

    /** Test Case 10
     *  Can't set same author name for a maze
     */
    @Test
    public void setSameMazeAuthor(){
        assertThrows(MazeListException.class, () -> {
            Maze.addMaze("The Maze Runner","Eric");
            Maze.setMazeAuthor("The Maze Runner","Eric");
        });
    }

    /** Test Case 11
     *  Change the author of a maze in database
     */
    @Test
    public void changeMazeAuthor() throws MazeListException{
        Maze.addMaze("Just a new maze","Henry");
        Maze.setMazeAuthor("Just a new maze","Arthur");
        assertEquals("Arthur",Maze.getMazeAuthor("Just a new maze"),"Failed to change author.");
    }

    /** Test Case 12
     *  Add date and time of last modified of a maze
     */
    @Test
    public void addLastModifiedDateTime() throws MazeListException{
        DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
        DateFormat dateFormatModified = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
        Date currentDateTime = Calendar.getInstance().getTime();
        String currentDateTimeString = dateFormat.format(currentDateTime);
        Maze.addMaze("A cool maze","Rose");
        Maze.setLastModifiedDateTime("A cool maze");
        assertEquals(currentDateTimeString,Maze.getModifiedDateTime("A cool maze"),
                "Incorrect date and time.");
    }

    /** Test Case 13
     *  Can't add last modified and time of creation for a nonexistent maze
     */
    @Test
    public void addModifiedDateTimeNonexistentMaze(){
        assertThrows(MazeListException.class, () -> {
            Maze.setLastModifiedDateTime("This is a masterpiece");
        });
    }

}