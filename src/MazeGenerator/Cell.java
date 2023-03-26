package MazeGenerator;

/**
 * @author Michael Fox
 */

public class Cell {
    /** {top, left, bottom, right} */
    public int[] borders = {1, 1, 1, 1};
    boolean visited = false;
    private int yLocation;
    private int xLocation;

    /**
     * A constructor for the maze class setting the initial fields for the created maze object
     * @param xLocation the x location within a 2D array of cells and the panel grid
     * @param yLocation the x location within a 2D array of cells and the panel grid
     */
    public Cell(int xLocation, int yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    /**
     * Get method
     * @return a copy of the x location the cell object
     */
    public int get_xLocation() {
        int x = xLocation;
        return x;
    }

    /**
     * Get method
     * @return a copy of the y location the cell object
     */
    public int get_yLocation() {
        int y = yLocation;
        return y;
    }

    /**
     * Get method
     * @see Cell#borders
     * @return a copy of the border values for the cell object
     */
    public int[] getBorders(){
        return this.borders;
    }

    /**
     * Used to update the state of the cells borders
     * @param borderIndex the array index representation of the desired border to change
     * @see Cell#borders
     * @param borderValue
     */

    public void updateBorders(int borderIndex, int borderValue){
        this.borders[borderIndex] = borderValue;
    }

}




