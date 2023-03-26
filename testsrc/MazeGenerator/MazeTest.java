package MazeGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for "Maze" class
 * @author Jeng Yang Wong
 */

public class MazeTest {

    Maze maze;

    // Regular Test Cases
    /** Test Case 0
     *  Construct a Maze object
     */
    @BeforeEach
    @Test
    public void setupMaze(){
        maze = new Maze(10,10);
        maze.createMaze();
        maze.generateMaze();
    }

    /** Test Case 1
     *  Test creating a maze with less than 10 rows and columns
     */
    @Test
    public void createMazeLessThanTenRowsColumns(){
        maze = new Maze(5,5);
        assertEquals(25,maze.getMaze_size(),"Maze generated incorrectly.");
    }

    /** Test Case 2
     *  Test creating a maze with more than 50 rows and columns
     */
    @Test
    public void createMazeMoreThanHundredRowsColumns(){
        maze = new Maze(25,40);
        assertEquals(25*40,maze.getMaze_size(),"Maze generated incorrectly.");
    }

    /** Test Case 3
     *  Test creating cells objects within a maze that is less than 10 rows and columns
     */
    @Test
    public void createCellsLessThanTenRowsColumns(){
        maze = new Maze(8,8);
        assertEquals(64,maze.getNumberCells(),"Cells objects were not generated correctly.");
    }


    /** Test Case 4
     *  Test creating cells objects within a maze that is more than 50 rows and columns
     */
    @Test
    public void createCellsMoreThanHundredRowsColumns(){
        maze = new Maze(60,40);
        assertEquals(60*40,maze.getNumberCells(),"Cells objects were not generated correctly.");
    }

    /** Test Case 5
     *  Test setting starting cell and ending cell and end within a maze
     */
    @Test
    public void setStartCell(){
        Cell startCell = new Cell(2,4);
        Cell endCell = new Cell(8,6);
        maze.setStart(startCell);
        maze.setFinish(endCell);
        assertEquals(startCell,maze.getStart(),"Incorrect starting cell.");
        assertEquals(endCell,maze.getFinish(),"Incorrect ending cell.");
    }

    /** Test Case 6
     *  Test if the next cell of the maze is being updated
     */
    @Test
    public void updateCellBorders(){
        assertEquals(true,maze.getIsUpdated(),"Failed to update cell borders.");
    }

    /** Test case 7
     *  Test the boolean that returns false to reset visited cells
     */
    @Test
    public void resetVisitedCells(){
        maze.resetVisited();
        assertEquals(false,maze.getCurrentCell().visited,
                "Failed to reset visited cells.");
    }

    /** Test Case 8
     *  Test setting name for a maze
     */
    @Test
    public void testSetMazeName(){
        String mazeName = "Coolest Maze";
        maze.setMazeName("Coolest Maze");
        assertEquals(mazeName,maze.getMazeName(),"Incorrect maze name.");
    }

    /** Test Case 9
     *  Test setting author of a maze
     */
    @Test
    public void testSetMazeAuthor(){
        String author = "Mike";
        maze.setMazeAuthor("Mike");
        assertEquals(author,maze.getMazeAuthor(),"Incorrect author.");
    }

    /** Test Case 10
     *  Test setting date created of a maze
     */
    @Test
    public void testSetDateCreated(){
        String dateCreated = "12-June-2022";
        maze.setDateCreated("12-June-2022");
        assertEquals(dateCreated,maze.getDateCreated(),"Incorrect date created.");
    }

    /** Test Case 11
     *  Test setting date edited of a maze
     */
    @Test
    public void testSetDateEdited(){
        String dateEdited = "12-June-2022";
        maze.setDateEdited("12-June-2022");
        assertEquals(dateEdited,maze.getDateEdited(),"Incorrect date edited.");
    }

    // Boundary Test Cases
    /** Test Case 12
     *  Test creating a maze with 1 column and 1 row
     */
    @Test
    public void createMazeOneColumnAndRows(){
        maze = new Maze(1,1);
        assertEquals(1,maze.getMaze_size(),"Maze was generated incorrectly.");
    }

    /** Test Case 13
     *  Test creating cells objects within a maze has 1 column and 1 row
     */
    @Test
    public void createCellObjectOneColumnAndRows(){
        maze = new Maze(1,1);
        assertEquals(1,maze.getNumberCells(),"Maze was generated incorrectly.");
    }

    /** Test Case 14
     *  Test creating a maze with the lowest difficulty
     */
    @Test
    public void setMazeDifficultyEasiest(){
        maze = new Maze(1,1);
        assertEquals(1,maze.getMazeDifficulty());
    }

    /** Test Case 15
     *  Test creating a maze with the hardest difficulty
     */
    @Test
    public void setMazeDifficultyHardest(){
        maze = new Maze(90,90);
        assertEquals(5,maze.getMazeDifficulty());
    }

    /** Test Case 16
     *  Test setting starting cell at top left and ending cell at bottom right
     */
    @Test
    public void fromTopLeftToBottomRight(){
        Cell startCell = new Cell(0,0);
        maze.setStart(startCell);
        maze.setFinish(maze.getCells()[maze.COLUMNS - 1][maze.ROWS -1]);
        assertEquals(startCell,maze.getStart(),"Incorrect starting cell.");
        assertEquals(maze.getFinish(),maze.getCells()[maze.COLUMNS-1][maze.ROWS-1],"Incorrect ending cell.");
    }

    /** Test Case 17
     *  Test setting starting point and ending point the same cell
     */
    @Test
    public void setSameStartingEndingPoints(){
        maze.setStart(maze.getCells()[2][5]);
        maze.setFinish(maze.getCells()[maze.COLUMNS-8][maze.ROWS-5]);
        assertEquals(maze.getStart(),maze.getFinish(),"Incorrect maze solution.");
    }

    // Exceptional Test Cases
    /** Test Case 18
     *  Test creating a maze with a negative number of columns
     */
    @Test
    public void createMazeWithNegativeColumns(){
        assertThrows(Exception.class,()-> {
            maze = new Maze((-10), 10);
        });
    }

    /** Test Case 19
     *  Test creating a maze with a negative number of rows
     */
    @Test
    public void createMazeWithNegativeRows(){
        assertThrows(Exception.class,()-> {
            maze = new Maze(10, -(10));
        });
    }

    /** Test Case 20
     *  Test setting starting cell out of bounds
     */
    @Test
    public void setStartingCellOutOfBounds(){
        assertThrows(Exception.class,()-> {
        Maze maze = new Maze(0,0);
        Cell startCell = new Cell(41,32);
        Cell endCell = new Cell(55,73);
        maze.setStart(startCell);
        maze.setFinish(endCell);
        });
    }

}