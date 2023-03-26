package MazeGenerator;

import javax.swing.*;
import java.io.Serializable;
import java.util.Stack;

/**
 * @Author Michael Fox
 */
public class MazeSolution implements Serializable {

    private boolean solvable = true;
    private Cell[][] cells;
    private Stack<Cell> cellStack = new Stack<>();
    private Cell currentCell;

    public Cell[] solution;
    final public Cell startCell;
    final public Cell finishCell;

    /**
     * Constructor method initialising a new maze solution for a given maze
     * @param unsolvedMaze An unsolved maze, of which the solution is being sought
     */
    public MazeSolution(Maze unsolvedMaze){
        unsolvedMaze.resetVisited();

        this.cells = unsolvedMaze.getCells();

        this.startCell= unsolvedMaze.getStart();
        this.finishCell= unsolvedMaze.getFinish();

        this.currentCell = startCell;
        this.solution = new Cell[2*unsolvedMaze.maze_size];
        this.findPath();
    }


    /**
     * A method using other methods within MazeSolution class, to output the maze solution
     * @return the solved maze panel, with the solution path highlighted in a different colour
     */
    private void findPath(){

        int index = 0;
        currentCell.visited = true;
        while (currentCell != finishCell) {
            Cell next = nextCell(currentCell);
            if (next != null) {
                cellStack.push(currentCell);
                currentCell = next;
                currentCell.visited = true;
                cellStack.push(currentCell);
            } else if (!cellStack.empty()) {
                currentCell = cellStack.pop();
            }

            if (currentCell != finishCell && nextCell(currentCell) == null && cellStack.empty()) {
                this.solution = null;
                this.solvable = false;
                break;
            }
        }

        if(this.solution != null) {
            cellStack.pop();
            while (cellStack.size() > 1) {
                solution[index] = cellStack.pop();
                index++;
            }
            this.solvable = true;
        }
    }

    /**
     * A method used to find the next possible step for the solution
     * @param currentCell The current cell in the maze solution process
     * @return The next cell of the solution
     */
    public Cell nextCell(Cell currentCell){

        // For readability save each border value in its respective direction
        int up = currentCell.borders[0];
        int left = currentCell.borders[1];
        int down = currentCell.borders[2];
        int right = currentCell.borders[3];

        // Get index values for the current cell within the Cells[][] mazeCells
        int x = currentCell.get_xLocation();
        int y = currentCell.get_yLocation();

        // Next Cell initialise
        DefaultListModel<Cell> nextFound =  new DefaultListModel<>();
        Cell next;
        if (up == 0 && !(cells[x][y - 1].visited)) {
            next = cells[x][y - 1];
            nextFound.addElement(next);
        }
        else if (left == 0 && !(cells[x-1][y].visited)){
            next = cells[x-1][y];
            nextFound.addElement(next);
        }
        else if (down == 0 && !(cells[x][y+1].visited)){
           next = cells[x][y+1];
            nextFound.addElement(next);
        }
        else if(right == 0 && !(cells[x+1][y].visited)){
            next = cells[x+1][y];
            nextFound.addElement(next);
        }
        if(nextFound.isEmpty()) {
            return null;
        }
        int randomNumber = (int) Math.floor(Math.random()*nextFound.getSize());
        return nextFound.getElementAt(randomNumber);
    }

    /**
     * Get method
     * @return the array of Cell objects that signify the maze solution
     */
    public Cell[] getSolution() {
        return solution;
    }

    /**
     * Get method
     * @return the boolean representation of the mazes solvable state
     */
    public boolean getSolvable(){
        return solvable;
    }

}
