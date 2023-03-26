package MazeGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for "Cell" class
 * @author Jeng Yang Wong
 */

public class CellTest {

    Cell cell;

    /** Test Case 0
     *  Construct a Cell object
     */
    @BeforeEach
    @Test
    public void setupCell(){
        cell = new Cell(10,10);
    }

    /** Test Case 1
     *  Construct a Cell object with positive (x,y) coordinate
     */
    @Test
    public void createCellPositiveCoordinates ()throws Exception{
        cell = new Cell(20,45);
        assertEquals(20,cell.get_xLocation(),"Failed to create cell.");
        assertEquals(45,cell.get_yLocation(),"Failed to create cell.");
    }

    /** Test Case 2
     *  Construct a Cell object with negative (x,y) coordinate
     */
    @Test
    public void createCellNegativeCoordinates ()throws Exception{
        cell = new Cell(-24,-36);
        assertEquals(-24,cell.get_xLocation(),"Failed to create cell.");
        assertEquals(-36,cell.get_yLocation(),"Failed to create cell.");
    }

}