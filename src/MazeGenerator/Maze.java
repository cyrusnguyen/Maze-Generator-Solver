package MazeGenerator;

import java.awt.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.List;


/** @author Michael Fox */
public class Maze implements Serializable {

    public int COLUMNS;
    public int ROWS;
    public boolean set_start = true;
    private int mazeDifficulty;

    private String mazeName;
    private String mazeAuthor;

    private int numberCells;
    private int visitedCells = 0;
    private Cell cells[][];
    private Cell currentCell;
    private Stack<Cell> cellStack = new Stack<>();
    int maze_size;
    private String dateCreated;
    private String dateEdited;
    private boolean solvable = true;

    public Cell startCell;
    public Cell finishCell;
    public MazeSolution solution;
    boolean bordersIsUpdated;
    private List<String> listPaths;
    private List<Point> listPoints;

    /**
     * Set method
     * @param listPaths a list of path files for images contained within the maze
     */
    public void setListPaths(List<String> listPaths) {
        this.listPaths = listPaths;
    }

    /**
     * Set method
     * @param listPoints a list of points that contain images in the maze
     */
    public void setListPoints(List<Point> listPoints) {
        this.listPoints = listPoints;
    }

    /**
     * Get method
     * @return a list of path files for images contained within the maze
     */
    public List<String> getListPaths() {
        return this.listPaths;
    }

    /**
     * Get method
     * @return a list of points that contain images in the maze
     */
    public List<Point> getListPoints() {
        return this.listPoints;
    }


    /**
     * Maze constructor setting the default parameters to the new maze object
     */
    public Maze(int COLUMNS, int ROWS)  {
        this.COLUMNS = COLUMNS;
        this.ROWS = ROWS;
        this.numberCells = COLUMNS * ROWS;
        cells = new Cell[COLUMNS][ROWS];
        this.createMaze();

        this.maze_size  = ROWS * COLUMNS;
        this.mazeDifficulty = setDifficulty();
        this.dateCreated= new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.dateEdited =new SimpleDateFormat("dd/MM/yyyy  HH:mm").format(new Date());
        this.listPoints = new ArrayList<>();
        this.listPaths = new ArrayList<>();

        this.generateMaze();
    }

    /**
     * A nested loop, creating new cell objects given the number of columns and rows,
     * storing them within a 2D array and adding them to the maze panel
     */
    public void createMaze(){

        Cell newCell;
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                newCell = cells[x][y] = new Cell(x,y);
            }
        }
    }

    /**
     *  The maze generation method, implementing several object methods to produce a full unsolved maze given
     *  the object parameters
     * @return the generated maze in the form of the maze object JPanel 'mazePanel'
     */

    public void generateMaze(){
        Cell nextCell;
        currentCell = cells[0][0];
        currentCell.visited = true;
        cellStack.push(currentCell);
        visitedCells++;

        while (visitedCells < numberCells){
            nextCell = checkNeighbours(currentCell);
            if(nextCell != null) {
                updateBorders(currentCell, nextCell);

                currentCell = nextCell;
                currentCell.visited = true;
                cellStack.push(currentCell);

                visitedCells++;
            }

            else if (!cellStack.empty()){
                currentCell=cellStack.pop();
            }
        }
    }

    /**
     * A four stage 'if' cycle determining whether the current cells unvisited neighbours and randomly selecting one
     * for the next cell in the generation process
     * @param currentCell the current 'occupied' cell in the maze generation process
     * @return the randomly selected, neighbouring, unvisited cell
     */
    private Cell checkNeighbours(Cell currentCell){
        int x = currentCell.get_xLocation();
        int y = currentCell.get_yLocation();

        Cell[] unvisitedNeighbours = new Cell[4];

        int  neighboursFound = 0;

        // Check neighbour to left
        if(x > 0 && !(cells[x-1][y].visited)){
            unvisitedNeighbours[neighboursFound] = cells[x-1][y];
            neighboursFound++;
        }

        // Check neighbour to right
        if(x < (COLUMNS -1) && !(cells[x+1][y].visited)){
            unvisitedNeighbours[neighboursFound] = cells[x+1][y];
            neighboursFound++;
        }

        // Check neighbour above
        if( y > 0 && !(cells[x][y-1]).visited){
            unvisitedNeighbours[neighboursFound] =  cells[x][y-1];
            neighboursFound++;
        }

        // Check neighbour below
        if( y < ROWS-1 && !(cells[x][y+1]).visited){
            unvisitedNeighbours[neighboursFound] =  cells[x][y+1];
            neighboursFound++;
        }

        int randomNumber = (int) Math.floor(Math.random()*neighboursFound);
        return unvisitedNeighbours[randomNumber];
    }

    /**
     * Determines which direction the next cell will be relative to the current cell, using the (x,y) coordinates
     * and removing the borders between by updating the 'borders[]' values of the cell object and using the
     * Cell method, 'setBorders'.
     * @param currentCell the current 'occupied' cell in the maze generation process
     * @param nextCell the randomly selected, neighbouring, unvisited cell
     */
    private void updateBorders(Cell currentCell, Cell nextCell) {

        int x = currentCell.get_xLocation();
        int y = currentCell.get_yLocation();

        // Check if next panel is to left
        if (x > 0 && nextCell == cells[x - 1][y]) {
            currentCell.borders[1] = 0;
            nextCell.borders[3] = 0;
        }

        // Check if next panel is to right
        else if (x < (COLUMNS - 1) && nextCell == cells[x + 1][y]) {
            currentCell.borders[3] = 0;
            nextCell.borders[1] = 0;
        }

        // Check if next panel is  above
        else if (y > 0 && nextCell == cells[x][y - 1]) {
            currentCell.borders[0] = 0;
            nextCell.borders[2] = 0;
        }

        // Check if next panel is  below
        else if (y < ROWS - 1 && nextCell == cells[x][y + 1]) {
            currentCell.borders[2] = 0;
            nextCell.borders[0] = 0;
        }
        bordersIsUpdated = true;
    }

    /**
     * Sets difficulty of maze on a 1-5 scale. Difficulty is determined by the total amount of cells and a set of predetermined ranges.
     * @return integer value from 1-5, 1 being the lowest difficulty and 5 being the highest difficulty
     */
    private int setDifficulty(){
        if (maze_size < 400) return 1;
        else if ( maze_size < 900) return 2;
        else if ( maze_size < 2500) return 3;
        else if ( maze_size < 4900) return 4;
        else if ( maze_size <= 10000) return 5;
        return 0;
    }

    /**
     * Getter for the mazes difficulty
     * @return integer value from 1-5, 1 being the lowest difficulty and 5 being the highest difficulty
     */
    public int getMazeDifficulty(){
        return this.mazeDifficulty;
    }

    /**
     *  Set method
     * @param name the name of the maze as per user input
     */
    public void setMazeName(String name){
        this.mazeName = name;
    }

    public String getMazeName(){
        return mazeName;
    }

    /**
     * Get method
     * @return the size of the maze, which is the result of multiplication of rows and columns
     */
    public int getMaze_size(){
        return maze_size;
    }

    /**
     * Get method
     * @return the boolean that checks whether the cell borders are updated
     */
    public boolean getIsUpdated(){
        return bordersIsUpdated;
    }

    /**
     * Get method
     * @return the number of cells of the maze
     */
    public int getNumberCells(){
        return numberCells;
    }

    /**
     * Set method
     * @param author the author name as per user input
     */
    public void setMazeAuthor(String author){
        this.mazeAuthor = author;
    }

    /**
     * Get method
     * @return the author name of maze
     */
    public String getMazeAuthor(){
        return mazeAuthor;
    }

    /**
     * Set method
     * @param date date the date in a string format
     */
    public void setDateCreated(String date){
        this.dateCreated = date;
    }

    /**
     * Getter for the mazes creation date
     * @return The date that the maze was created
     */
    public String getDateCreated(){
       return dateCreated;
    }

    /**
     * Setter for the mazes last edited date
     * @param date the date in a string format
     */
    public void setDateEdited(String date){
        this.dateEdited = date;
    }

    /**
     * Resets the boolean condition that each cell has been visited for each cell contained in the maze.
     * Used to allow solver to reassign each cell as visited during solver process
     */
    public void resetVisited(){
        for (Cell[] cell_array: cells) {
            for (Cell cell : cell_array){
                cell.visited = false;
            }
        }
    }

    /**
     *
     * @return
     */
    public Cell getCurrentCell(){
        return currentCell;
    }

    /**
     *  Get method
     * @return the 2D array 'cells[][]' containing the cells of the maze in their corresponding (x,y) coordinates
     */
    public Cell[][] getCells() {
        Cell[][] cellArray = cells;
        return cellArray;
    }

    /**
     *
     * @param start
     */
    public void setStart(Cell start){
        this.startCell = start;
    }

    /**
     *
     * @param finish
     */
    public void setFinish(Cell finish){
        this.finishCell = finish;
    }

    /**
     *
     * @return
     */
    public Cell getStart() {
        return this.startCell;

    }

    /**
     *
     * @return
     */
    public Cell getFinish(){
        return this.finishCell;
    }

    /**
     *
     * @return
     */
    public String getDateEdited(){
        return dateEdited;
    }

    /**
     *
     * @return
     */
    public boolean getMazeSolvable(){
        return this.solvable;
    }
}
