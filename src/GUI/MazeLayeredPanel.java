package GUI;

import MazeGenerator.Cell;
import MazeGenerator.Maze;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a JLayeredPane containing a maze in GridLayout
 */
public class MazeLayeredPanel extends JLayeredPane {
    private int WIDTH = 600;
    private int HEIGHT = 600;
    private int GRID_ROWS;
    private int GRID_COLS;
    private final Dimension LAYERED_PANE_SIZE = new Dimension(WIDTH, HEIGHT);
    private final Dimension LABEL_SIZE = new Dimension(60, 40);
    private JPanel mazePnl;
    private JPanel[][] mazePanels;
    private Maze maze;
    private Cell[][] cells;
    private boolean showSolution = false;
    private boolean set_start;
    private boolean draggable = false;
    private boolean editable = false;
    private JPanel startPanel;
    private JPanel finishPanel;
//    private ComponentResizer cr;
    private JPanel imageContainer;
    private List<Point> imageContainerLocations;
    private List<String> imagePaths;

    public JPanel getImageContainer(){
        return this.imageContainer;
    }

    /**
     * Initialise the MazeLayeredPanel class
     * @param WIDTH : maze width
     * @param HEIGHT : maze height
     * @param ROWS : number of maze rows
     * @param COLS : number of maze rows
     * @param maze : object
     */
    public MazeLayeredPanel(int WIDTH, int HEIGHT, int ROWS, int COLS, Maze maze) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.GRID_ROWS = ROWS;
        this.GRID_COLS = COLS;
        this.maze = maze;
        this.set_start = maze.set_start;
        if(imagePaths != null){maze.setListPaths(imagePaths);}
        if(imageContainerLocations != null){maze.setListPoints(imageContainerLocations);}

        generateMazePanels();
    }

    /**
     * Create a panel containing a grid maze, add MouseMotionLister and MouseListener for each cell
     */
    private void generateMazePanels(){
        mazePnl = new JPanel();
        mazePanels = new JPanel[GRID_COLS][GRID_ROWS];
        mazePnl.setBackground(Color.white);
        mazePnl.setSize(WIDTH, HEIGHT);
        mazePnl.setPreferredSize(LAYERED_PANE_SIZE);
        mazePnl.setLayout(new GridLayout(GRID_ROWS, GRID_COLS));

        cells = maze.getCells();
        for (int y = 0; y < maze.ROWS; y++) {
            for (int x = 0; x < maze.COLUMNS; x++) {
                mazePanels[x][y] = new JPanel(new BorderLayout());
                mazePanels[x][y].setBackground(Color.white);
                Cell newCell = cells[x][y];
                int[] borders = newCell.getBorders();

                mazePanels[x][y].setBorder(BorderFactory.createMatteBorder(borders[0],borders[1],borders[2],borders[3],Color.BLACK));
                mazePanels[x][y].setName(x+"-"+y);
                mazePanels[x][y].addMouseListener(clickListen);
                addButtons(mazePanels[x][y],getCell(maze,mazePanels,mazePanels[x][y]));
                mazePnl.add(mazePanels[x][y]);
            }
        }
        add(mazePnl, JLayeredPane.DEFAULT_LAYER);
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        addMouseListener(myMouseAdapter);
        addMouseMotionListener(myMouseAdapter);

    }

    /**
     * Add 2 buttons (right and bottom) for each cell
     * @param panel JPanel
     * @param cell Cell object
     */
        public void addButtons(JPanel panel, Cell cell){
        JButton bottom = new JButton(){{
            addMouseListener(mlButtonCLick);
            setName("bottom-"+cell.get_xLocation()+","+cell.get_yLocation());
            setPreferredSize(new Dimension(5, 5));

        }};
        bottom.setBorderPainted(false);
        bottom.setBackground(Color.white);
        panel.add(bottom, BorderLayout.SOUTH);

        JButton right = new JButton(){{
            addMouseListener(mlButtonCLick);
            setName("right-"+cell.get_xLocation()+","+cell.get_yLocation());
            setPreferredSize(new Dimension(5, 5));
        }};
        right.setBorderPainted(false);
        right.setBackground(Color.white);
        panel.add(right, BorderLayout.EAST);

        }

    /**
     * MouseListener for buttons, make buttons flicker and toggle buttons when pressing
     */
    MouseListener mlButtonCLick = new MouseAdapter(){
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setBorderPainted(true);
            button.setBorder(new LineBorder(Color.MAGENTA, 1));
        }
        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setBorder(new LineBorder(Color.BLACK, 1));
            button.setBorderPainted(false);

        }
        @Override
        public void mousePressed(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            JPanel panel = null;
            if (button.getParent() != null) panel = (JPanel) button.getParent();
            String buttonLocation = button.getName().split("-")[0];
            String xString = panel.getName().split("-")[0];
            int x = Integer.valueOf(xString);

            String yString = panel.getName().split("-")[1];
            int y = Integer.valueOf(yString);
            Cell currentCell = maze.getCells()[x][y];
            if (buttonLocation.contains("right")) {

                Cell nextCell = maze.getCells()[x + 1][y];
                int[] panelBorders = maze.getCells()[x][y].borders;
                int[] neighbourBorders = maze.getCells()[x + 1][y].borders;
                JPanel neighbourPanel = mazePanels[x + 1][y];

                if (panelBorders[3] == 0) {
                    panelBorders[3] = 1;
                    currentCell.updateBorders(3, 1);
                    neighbourBorders[1] = 1;
                    nextCell.updateBorders(1, 1);
                } else {
                    panelBorders[3] = 0;
                    currentCell.updateBorders(3, 0);
                    neighbourBorders[1] = 0;
                    nextCell.updateBorders(1, 0);
                }

                updateBorders(panelBorders, panel);

                updateBorders(neighbourBorders, neighbourPanel);
                maze.getCells()[x][y].borders = panelBorders;
                maze.getCells()[x + 1][y].borders = neighbourBorders;
            }
            else{
                Cell nextCell = maze.getCells()[x][y+1];
                int[] panelBorders = maze.getCells()[x][y].borders;
                int[] neighbourBorders = maze.getCells()[x][y+1].borders;
                JPanel neighbourPanel = mazePanels[x][y+1];

                if (panelBorders[2] == 0) {
                    panelBorders[2] = 1;
                    currentCell.updateBorders(2, 1);
                    neighbourBorders[0] = 1;
                    nextCell.updateBorders(0, 1);
                } else {
                    panelBorders[2] = 0;
                    currentCell.updateBorders(2, 0);
                    neighbourBorders[0] = 0;
                    nextCell.updateBorders(0, 0);
                }

                updateBorders(panelBorders, panel);

                updateBorders(neighbourBorders, neighbourPanel);
                maze.getCells()[x][y].borders = panelBorders;
                maze.getCells()[x][y+1].borders = neighbourBorders;
            }

        }
    };

    /**
     * Draw border for the panel
     * @param borders: int array indicates {top, left, bottom, right} borders
     * @param panel: panel that needs drawing
     */
    public void updateBorders(int[] borders, JPanel panel){
        panel.setBorder(BorderFactory.createMatteBorder(borders[0],borders[1],borders[2],borders[3],Color.BLACK));

    }

    /**
     * Iterate through panel array and search for a cell corresponding JPanel
     * @param maze: Maze object
     * @param panels: Panel array
     * @param panel: JPanel
     * @return
     */
    static Cell getCell(Maze maze,JPanel[][] panels, JPanel panel){

        for (int y = 0; y < maze.ROWS; y++) {
            for (int x = 0; x < maze.COLUMNS; x++) {
                if (panel == panels[x][y]){
                    return maze.getCells()[x][y];
                }
            }
        }
        return null;
    }

    /**
     * Iterate through panels, search for their components to get the JButtons, turn them on or off.
     */
    public void handleWalls(){
        if (editable){
            for (int y = 0; y < maze.ROWS; y++) {
                for (int x = 0; x < maze.COLUMNS; x++) {
                    Component[] components = mazePanels[x][y].getComponents();
                    for (Component component: components) {
                        if (component instanceof JButton){
                            component.setVisible(true);
                        }
                    }
                }
            }

        }else{
            for (int y = 0; y < maze.ROWS; y++) {
                for (int x = 0; x < maze.COLUMNS; x++) {
                    Component[] components = mazePanels[x][y].getComponents();
                    for (Component component: components) {
                        if (component instanceof JButton){
                            component.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * Iterate through JPanels, add or remove MouseListener to make the panel draggable
     */
    public void handleDrag(){
        if (draggable){
            for (int y = 0; y < maze.ROWS; y++) {
                for (int x = 0; x < maze.COLUMNS; x++) {
                    mazePanels[x][y].removeMouseListener(clickListen);
                }
            }

        }else{
            for (int y = 0; y < maze.ROWS; y++) {
                for (int x = 0; x < maze.COLUMNS; x++) {
                    mazePanels[x][y].addMouseListener(clickListen);
                }
            }
        }
    }

    /**
     * MouseListener for JPanels, setting start or finish points
     */
    MouseListener clickListen = new MouseAdapter()
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            JPanel panel = (JPanel)e.getSource();
            System.out.print(panel.getName());
            if (panel.getBackground()==Color.GREEN)
            {
                panel.setBackground(Color.WHITE);
                maze.setStart(null);
            }

            else if (panel.getBackground() == Color.RED){
                panel.setBackground(Color.WHITE);
                maze.setFinish(null);
            }

            else {
//                panel.setOpaque(true);
                if (maze.set_start && maze.getStart() == null) {
                    maze.setStart(getCell(maze,mazePanels,panel));
                    panel.setBackground(Color.GREEN);
                    startPanel=panel;
                }

                if (!maze.set_start && maze.getFinish() == null) {
                    panel.setBackground(Color.RED);
                    maze.setFinish(getCell(maze,mazePanels,panel));
                    finishPanel = panel;
                }
            }
        }
    };

    /**
     * MouseListener and MouseMotionListener for JPanels, make the inside JLabel draggable.
     */
    private class MyMouseAdapter extends MouseAdapter {
        private JLabel dragLabel = null;
        private int dragLabelWidthDiv2;
        private int dragLabelHeightDiv2;
        private JPanel clickedPanel = null;

        @Override
        public void mousePressed(MouseEvent me) {
            clickedPanel = (JPanel) mazePnl.getComponentAt(me.getPoint());
            Component[] components = clickedPanel.getComponents();
            Component lblComponent = null;
            if (components.length == 0) {
                return;
            }
            //Check if the panel holds a label
            for (Component component: components) {
                if (component instanceof JLabel){
                    lblComponent = component;
                }
            }
                // remove label from panel
                dragLabel = (JLabel) lblComponent;
                clickedPanel.remove(dragLabel);
                clickedPanel.revalidate();
                clickedPanel.repaint();

                dragLabelWidthDiv2 = dragLabel.getWidth() / 2;
                dragLabelHeightDiv2 = dragLabel.getHeight() / 2;

                int x = me.getPoint().x - dragLabelWidthDiv2;
                int y = me.getPoint().y - dragLabelHeightDiv2;
                dragLabel.setLocation(x, y);
                add(dragLabel, JLayeredPane.DRAG_LAYER);
                repaint();
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (dragLabel == null) {
                return;
            }
            int x = me.getPoint().x - dragLabelWidthDiv2;
            int y = me.getPoint().y - dragLabelHeightDiv2;
            dragLabel.setLocation(x, y);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if (dragLabel == null) {
                return;
            }
            remove(dragLabel); // remove dragLabel for drag layer of JLayeredPane
            JPanel droppedPanel = (JPanel) mazePnl.getComponentAt(me.getPoint());
            if (droppedPanel == null) {
                // if off the grid, return label to home
                clickedPanel.add(dragLabel);
                clickedPanel.revalidate();
            } else {
                int r = -1;
                int c = -1;
                searchPanelGrid: for (int row = 0; row < mazePanels.length; row++) {
                    for (int col = 0; col < mazePanels[row].length; col++) {
                        if (mazePanels[row][col] == droppedPanel) {
                            r = row;
                            c = col;
                            break searchPanelGrid;
                        }
                    }
                }

                if (r == -1 || c == -1) {
                    // if off the grid, return label to home
                    clickedPanel.add(dragLabel);
                    clickedPanel.revalidate();
                } else {
                    droppedPanel.add(dragLabel);
                    droppedPanel.revalidate();
                }
                System.out.println(r + " - " + c);
                imageContainer = droppedPanel;
            }
            repaint();
            dragLabel = null;
        }
    }

    /**
     * Get label size
     * @return: Size of JLabel
     */
    public Dimension getLabelSize (){
        return this.LABEL_SIZE;
    }

    /**
     * Get the JLabel as image, import image to first JPanel
     * @param imageLabel: JLabel has image
     */
    public void importImage(JLabel imageLabel){
        imageLabel.setSize(mazePanels[0][0].getSize());
        mazePanels[0][0].removeAll();
        mazePanels[0][0].add(imageLabel);

        imageContainer = mazePanels[0][0];

        mazePnl.validate();
        mazePnl.repaint();
    }

    /**
     * Iterate through JPanels and search for panels having image, update the information to Maze object
     */
    public void getListPathsAndPoints(){
        imagePaths = new ArrayList<>();
        imageContainerLocations = new ArrayList<>();
        for (int row = 0; row < mazePanels.length; row++) {
            for (int col = 0; col < mazePanels[row].length; col++) {
                Component[] components = mazePanels[row][col].getComponents();
                if (components.length != 0){
                    for (Component component: components) {
                        if (component instanceof JLabel){
                            imageContainerLocations.add(new Point(row, col));
                            imagePaths.add(component.getName());
                        }
                    }
                }
            }
        }
        maze.setListPoints(imageContainerLocations);
        maze.setListPaths(imagePaths);
    }

    /**
     * Set the draggable ability
     * @param dragResult: boolean result
     */
    public void setDraggable(boolean dragResult){
        this.draggable = dragResult;
    }

    /**
     * Set the editable ability
     * @param editableResult: boolean result
     */
    public void setEditable(boolean editableResult){
        this.editable = editableResult;
    }

    /**
     * Get JPanel has the grid maze
     * @return JPanel: grid maze
     */
    public JPanel getMazePnl() {
        return mazePnl;
    }

    /**
     * Get JPanel array
     * @return JPanel[][]: JPanel array
     */
    public JPanel[][] getMazePanels() {
        return mazePanels;
    }

    /**
     * Get start panel
     * @return JPanel: start panel
     */
    public JPanel getStartPanel() {
        return startPanel;
    }

    /**
     * Get finish panel
     * @return JPanel: finish panel
     */
    public JPanel getFinishPanel() {
        return finishPanel;
    }
}
