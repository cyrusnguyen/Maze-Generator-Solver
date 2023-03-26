package MazeGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for "MazeSolution" class
 * @author Jeng Yang Wong
 */

public class MazeSolutionTest {

    Maze maze;
    MazeSolution solution;

    // Regular Test Cases
    /** Test Case 0
     *  Construct an object of "MazeSolution" class
     *  This sets the top left cell as starting point, and the bottom right cell is finishing point
     *  Then create an instance of the "MazeSolution" class
     */
    @BeforeEach
    @Test
    public void setupMaze(){
        maze = new Maze(10,10);
        maze.setStart(maze.getCells()[2][5]);
        maze.setFinish(maze.getCells()[maze.COLUMNS - 3][maze.ROWS - 7]);
        solution = new MazeSolution(maze);
    }

    /** Test Case 1
     *  Test if a solution is successfully generated
     */
    @Test
    public void testSolutionGenerator(){
        assertEquals(solution.solution,solution.getSolution(),"Incorrect maze solution.");
    }

    /** Test Case 2
     *  Test the boolean that returns true if a maze is solvable
     */
    @Test
    public void testIsSolvable(){
        assertEquals(true,solution.getSolvable(),"Incorrect maze solution.");
    }


    // Boundary Test Cases
    /** Test Case 3
     *  Test with two maze solutions
     */
    @Test
    public void twoMazeSolutions(){
        Maze maze2 = new Maze(20,20);
        maze2.setStart(maze2.getCells()[14][15]);
        maze2.setFinish(maze2.getCells()[maze2.COLUMNS-8][maze2.ROWS-4]);

        assertEquals(maze.getCells()[2][5],maze.getStart(),"Incorrect maze solution.");
        assertEquals(maze.getCells()[maze.COLUMNS-3][maze.ROWS-7],maze.getFinish(),"Incorrect maze solution.");

        assertEquals(maze2.getCells()[14][15],maze2.getStart(),"Incorrect maze solution.");
        assertEquals(maze2.getCells()[maze2.COLUMNS-8][maze2.ROWS-4],maze2.getFinish(),"Incorrect maze solution.");
    }


    /** Test Case 4
     *  Test with two maze solutions, but destroy one first
     */
    @Test
    public void twoMazeSolutionsDestroyOne(){
        maze.setStart(maze.getCells()[0][0]);
        maze.setFinish(maze.getCells()[maze.COLUMNS - 1][maze.ROWS -1]);
        solution = new MazeSolution(maze);

        maze.setStart(maze.getCells()[5][8]);
        maze.setFinish(maze.getCells()[maze.COLUMNS-4][maze.ROWS -2]);
        solution = new MazeSolution(maze);

        assertEquals(maze.getCells()[5][8],maze.getStart(),"Incorrect maze solution.");
        assertEquals(maze.getCells()[maze.COLUMNS-4][maze.ROWS-2],maze.getFinish(),"Incorrect maze solution.");
    }

    /** Test Case 5
     *
     */
    @Test
    public void traverseNextCellInASingleCellMaze(){
        assertThrows(Exception.class,()-> {
            Maze maze = new Maze(1, 1);
            maze.setStart(maze.getCells()[0][0]);
            maze.setFinish(maze.getCells()[0][0]);
            MazeSolution solution = new MazeSolution(maze);
            solution.nextCell(maze.getCurrentCell());
        });
    }
}
