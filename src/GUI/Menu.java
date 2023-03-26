package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;

import MazeDatabase.DataSource;
import MazeGenerator.Cell;
import MazeGenerator.Maze;
import MazeGenerator.MazeSolution;


public class Menu extends JFrame implements ActionListener {
    private JFrame f;
    private JPanel panels, pnlMazeContainer, pnlWest, pnlEast, pnlEditMaze, pnlMazeGenerator;
    private JPanel pnlMazeSolver, pnlSelectMazeSize, pnlRows, pnlColumns, pnlMaze, pnlSolutionCheckbox, pnlMazeDetails;
    private JPanel pnlMazeDetailsTab, pnlImportImageTab, pnlMazeSelectionTab, pnlImagesList;
    private JTabbedPane tpWest;
    private JLabel lblimageChooser, lblMazeName, lblSelectStartFinish, lblSelectMazeSize, lblRows, lblColumns, lblMazeDifficulty;
    private JLabel lblfillMazeName, lblfillMazeAuthor, lblMazeAuthor, pnlDate, lblMoveImage, lblEditMaze;
    private JButton btnGenerateMaze,btnSolveMaze,btnImportImage, btnUpdateMazeDetails, btnClearSolution;
    private JTextArea taMazeName, taMazeAuthor;
    private JFileChooser fcImportImage;
    private JScrollPane spImportImage;
    private JSpinner spnRows, spnColumns;
    private JCheckBox cbMoveImage, cbShowSolution, cbEditMaze;
    private JMenu fileMenu, aboutMenu;;
    private JMenuItem imNewWindow, imExportMaze, imImportMaze,imSaveMazeImage,
            imImportImage, imAboutApplication;
    private JRadioButton rbStart, rbFinish;
    private Integer rowValues = 10;
    private Integer colValues = 10;
    private String difficultyString;
    private int difficulty;
    private Maze maze;
    private JPanel[][] mazePanels;
    public JPanel mazePnl;
    private MazeLayeredPanel mazeLayeredPanel;
    private JLayeredPane lpMaze;
    private ButtonGroup bgRadioButtons;


    /**
     * Get method
     * @return maze
     */
    public Maze getMaze(){
        return this.maze;
    }

    /**
     * Set method
     * @param maze
     */
    public void setMaze(Maze maze){
        this.maze = maze;
    }

    /**
     * Creates the main GUI of the application
     */
    public void createGUI() {
        this.f = new JFrame("Group 97 - Maze ");
        this.f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.f.setSize(1048, 800);
        this.f.setLayout(new BorderLayout());

        this.f.getContentPane().add(this.createMenuBar(), "North");
        this.f.getContentPane().add(this.createPanels(), "Center");
//            this.f.repaint();
        this.f.setLocationRelativeTo(null);
        this.f.setVisible(true);
    }

    /**
     * Create GUI panels
     */
    private JPanel createPanels() {
        this.panels = new JPanel();
        this.panels.setLayout(new BorderLayout(0, 0));

        this.pnlWest = new JPanel();
        this.panels.add(this.pnlWest, "West");

        // Maze Details tab
        this.pnlMazeDetailsTab = new JPanel(new GridLayout(23, 1, 10, 1));
        this.taMazeName = new JTextArea();
        this.taMazeName.setLineWrap(true);
        this.taMazeAuthor = new JTextArea();
        this.taMazeAuthor.setLineWrap(true);
        this.lblfillMazeName = new JLabel("Fill in Maze Name:");
        this.lblfillMazeAuthor = new JLabel("Fill in Maze Author:");
        this.btnUpdateMazeDetails = new JButton("Update");
        this.pnlMazeDetailsTab.add(this.lblfillMazeName);
        this.pnlMazeDetailsTab.add(this.taMazeName);
        this.pnlMazeDetailsTab.add(this.lblfillMazeAuthor);
        this.pnlMazeDetailsTab.add(this.taMazeAuthor);
        this.pnlMazeDetailsTab.add(this.btnUpdateMazeDetails);
        this.btnUpdateMazeDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu.this.maze.setMazeName(Menu.this.taMazeName.getText());
                Menu.this.lblMazeName.setText("Maze Name: "+Menu.this.maze.getMazeName());
                Menu.this.maze.setMazeAuthor(Menu.this.taMazeAuthor.getText());
                Menu.this.lblMazeAuthor.setText("Author: " + Menu.this.maze.getMazeAuthor());
            }
        });
        this.pnlImportImageTab = new JPanel();
        this.pnlImportImageTab.setPreferredSize(new Dimension(150, 650));
        this.btnImportImage = new JButton("Upload");
        this.btnImportImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu.this.fcImportImage = new JFileChooser();
                if (Menu.this.fcImportImage.showOpenDialog((Component)e.getSource()) == 0) {
                   importImageClicked();
                }
            }
        });
        this.pnlImagesList = new JPanel(new GridLayout(10, 0, 5, 5));
        this.spImportImage = new JScrollPane(this.pnlImagesList, 20, 30);
        this.spImportImage.setPreferredSize(new Dimension(150, 600));
        this.pnlImportImageTab.add(this.btnImportImage);
        this.pnlImportImageTab.add(this.spImportImage);
        this.pnlMazeSelectionTab = new JPanel();
        this.tpWest = new JTabbedPane();
        this.tpWest.add("Maze Details", this.pnlMazeDetailsTab);
//        this.tpWest.add("Select Maze", this.pnlMazeSelectionTab);
        this.tpWest.add("Import Image", this.pnlImportImageTab);
        this.tpWest.setPreferredSize(new Dimension(200, 700));
        this.pnlWest.add(this.tpWest);

        this.pnlMazeContainer = new JPanel();
        this.panels.add(this.pnlMazeContainer, "Center");
        this.pnlMazeContainer.setLayout(new BorderLayout(0, 0));
        this.pnlMaze = new JPanel();
        this.pnlMazeDetails = new JPanel(new GridLayout(2,2,5,5));
        this.pnlMazeContainer.add(this.pnlMazeDetails, "North");
        this.lblMazeName = new JLabel();
        this.lblMazeName.setFont(new Font("Arial", 1, 20));
        this.lblMazeName.setText("Maze Name: ");
        this.pnlMazeDetails.add(lblMazeName);

        this.difficultyString = "★".repeat(this.difficulty) +
                "☆".repeat(5 - this.difficulty);
        this.lblMazeDifficulty = new JLabel("Difficulty:\r\n"+difficultyString);
        this.pnlMazeDetails.add(lblMazeDifficulty);

        this.lblMazeAuthor = new JLabel();
        this.lblMazeAuthor.setText("Author: ");
        this.lblMazeAuthor.setFont(new Font("Arial", 1, 15));
        this.pnlMazeDetails.add(lblMazeAuthor);

        this.pnlDate = new JLabel();
        pnlMazeDetails.add(pnlDate);

        this.pnlEast = new JPanel();
        this.pnlEast.setBorder(new EtchedBorder(1, (Color) null, (Color) null));
        this.panels.add(this.pnlEast, "East");
        this.pnlEast.setLayout(new BorderLayout(0, 0));
        this.pnlSelectMazeSize = new JPanel();
        this.pnlEast.add(this.pnlSelectMazeSize, "North");
        this.pnlSelectMazeSize.setLayout(new GridLayout(6, 1, 10, 10));

        this.lblSelectMazeSize = new JLabel("Select Maze Size:\n");
        this.lblSelectMazeSize.setFont(new Font("Arial", 1, 15));
        this.pnlSelectMazeSize.add(this.lblSelectMazeSize);

        this.pnlRows = new JPanel();
        this.pnlSelectMazeSize.add(this.pnlRows);
        this.lblRows = new JLabel("ROWS:");
        this.pnlRows.add(this.lblRows, "Center");
        SpinnerModel spnRowValues = new SpinnerNumberModel(10, 1, 100, 1);
        rowValues = (Integer)spnRowValues.getValue();

        this.spnRows = new JSpinner(spnRowValues);
        this.spnRows.setBounds(100, 100, 50, 30);
        this.pnlRows.add(this.spnRows);
        this.pnlColumns = new JPanel();
        this.pnlSelectMazeSize.add(this.pnlColumns);
        this.lblColumns = new JLabel("COLUMNS:");
        this.pnlColumns.add(this.lblColumns, "Center");

        SpinnerModel spnColValues = new SpinnerNumberModel(10, 1, 100, 1);
        rowValues = (Integer)spnRowValues.getValue();
        this.btnGenerateMaze = new JButton("Generate");
        btnGenerateMaze.addActionListener(new ActionListener() {
            @Override
            /**
             *
             */
            public void actionPerformed(ActionEvent e) {
                colValues = (Integer)spnColValues.getValue();
                rowValues = (Integer)spnRowValues.getValue();
                taMazeName.setText("");
                taMazeAuthor.setText("");
                lblMazeName.setText("Maze Name: ");
                lblMazeAuthor.setText("Author: ");
                maze = new Maze(colValues,rowValues);
                generateMazePanel();

            }
        });
        this.spnColumns = new JSpinner(spnColValues);
        this.spnColumns.setBounds(100, 100, 50, 30);
        this.pnlColumns.add(this.spnColumns);

// -------------------------- Panel Edit Maze -----------------------------------------
        this.pnlEditMaze = new JPanel(new GridLayout(8,1));
        this.lblEditMaze = new JLabel("Tick To Edit Maze Walls:");
        this.lblEditMaze.setFont(new Font("Arial", 1, 15));
        this.cbEditMaze = new JCheckBox("Edit Maze", true);
        this.cbEditMaze.addActionListener(new ActionListener() {
            @Override
            /**
             *
             */
            public void actionPerformed(ActionEvent e) {
                if (cbEditMaze.isSelected()){
                    mazeLayeredPanel.setEditable(true);
                    mazeLayeredPanel.handleWalls();
                }

                else{
                    mazeLayeredPanel.setEditable(false);
                    mazeLayeredPanel.handleWalls();
                }


            }
        });
        this.pnlEast.add(this.pnlEditMaze, "South");
        this.lblSelectStartFinish = new JLabel("Select To Set Start Or Finish:\n");
        this.lblSelectStartFinish.setFont(new Font("Arial", 1, 15));
        this.bgRadioButtons = new ButtonGroup();
        this.rbStart = new JRadioButton("Set Start");
        this.rbStart.setSelected(true);
        this.rbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze.set_start=true;
            }
        });
        this.rbFinish = new JRadioButton("Set Finish");
        this.rbFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze.set_start=false;
            }
        });
        this.bgRadioButtons.add(rbStart);
        this.bgRadioButtons.add(rbFinish);

        this.pnlEast.add(pnlEditMaze, "Center");
        this.lblMoveImage = new JLabel("Tick To Move Image:");
        this.lblMoveImage.setFont(new Font("Arial", 1, 15));
        this.cbMoveImage = new JCheckBox("Move Image");
        this.cbMoveImage.setSelected(false);
        this.cbMoveImage.addActionListener(pressMoveImage);

        this.pnlEditMaze.add(lblSelectStartFinish);
        this.pnlEditMaze.add(rbStart);
        this.pnlEditMaze.add(rbFinish);
        this.pnlEditMaze.add(lblEditMaze);
        this.pnlEditMaze.add(cbEditMaze);
        this.pnlEditMaze.add(lblMoveImage);
        this.pnlEditMaze.add(cbMoveImage);

        this.pnlMazeGenerator = new JPanel();
        this.pnlMazeGenerator.setLayout(new BorderLayout(0, 0));
        this.pnlSelectMazeSize.add(this.pnlMazeGenerator);
        this.pnlMazeGenerator.add(this.btnGenerateMaze, "Center");

        this.pnlMazeSolver = new JPanel();
        this.pnlMazeSolver.setLayout(new BorderLayout(0, 0));
        this.btnSolveMaze = new JButton("Solve");
        this.btnClearSolution = new JButton("Clear");
        this.cbShowSolution = new JCheckBox("Show Solution", true);
        this.pnlMazeSolver.add(this.btnClearSolution, "Center");
        this.pnlMazeSolver.add(this.btnSolveMaze, "South");
        this.pnlSolutionCheckbox = new JPanel();
        this.pnlSolutionCheckbox.setLayout(new GridLayout(1, 0, 0, 0));
        this.pnlSolutionCheckbox.add(cbShowSolution);
        this.pnlMazeSolver.add(this.pnlSolutionCheckbox, "North");

        this.pnlMazeContainer.add(pnlMazeSolver, "South");
        btnSolveMaze.addActionListener(clickSolve);
        cbShowSolution.addActionListener(pressShow);
        btnClearSolution.addActionListener(clickClear);
        return this.panels;
    }

    /**
     * Method to generate maze panel.
     */
    public void generateMazePanel(){
        pnlMaze.removeAll();
        this.pnlMaze.setLayout(new GridLayout());
        pnlMaze.setPreferredSize(new Dimension(800,800));

        this.mazeLayeredPanel = new MazeLayeredPanel(600, 600, maze.ROWS, maze.COLUMNS, maze);
        this.lpMaze = (JLayeredPane)mazeLayeredPanel;
        this.mazePanels = mazeLayeredPanel.getMazePanels();
        this.mazePnl = mazeLayeredPanel.getMazePnl();

        if (cbMoveImage != null) {
            this.cbEditMaze.setSelected(true);
            this.cbMoveImage.setSelected(false);
            this.mazeLayeredPanel.setDraggable(false);
            this.rbStart.setSelected(true);
            this.rbStart.setEnabled(true);
            this.rbFinish.setEnabled(true);
            maze.set_start=true;
        }

        if(maze.getFinish() != null){
            mazePanels[maze.getFinish().get_xLocation()][maze.getFinish().get_yLocation()].setBackground(Color.RED);
        }

        if(maze.getStart() != null){
            mazePanels[maze.getStart().get_xLocation()][maze.getStart().get_yLocation()].setBackground(Color.GREEN);
        }

        if(maze.getListPaths().size() > 0){
            int index=0;
            while(index < maze.getListPoints().size()){
                retrieveImage(maze.getListPaths().get(index),maze.getListPoints().get(index));
                index++;
            }
        }
        if(maze.getMazeName() != null){ Menu.this.lblMazeName.setText("Maze Name: "+ maze.getMazeName());}
        if (maze.getMazeAuthor() != null){Menu.this.lblMazeAuthor.setText("Author: " + maze.getMazeName());}
        pnlMaze.add(lpMaze);
        this.pnlMazeContainer.add(pnlMaze);
        pnlMaze.revalidate();
        pnlMaze.repaint();
        this.difficulty = maze.getMazeDifficulty();
        difficultyString = "★".repeat(maze.getMazeDifficulty()) +
                "☆".repeat(5 - maze.getMazeDifficulty());
        lblMazeDifficulty.setText("Difficulty:\r\n"+difficultyString);
        cbShowSolution.setSelected(true);
        maze.setDateEdited(new SimpleDateFormat("dd/MM/yyyy  HH:mm").format(new Date()));
    }
    private void importImageClicked(){
        File imageFile = Menu.this.fcImportImage.getSelectedFile();
        String sname = imageFile.getAbsolutePath();
        System.out.println(sname);
        ImageIcon imageIcon = new ImageIcon(sname);
        Image image = imageIcon.getImage();
        Menu.this.lblimageChooser = new JLabel("", 0);
        Menu.this.lblimageChooser.setName(sname);
        Menu.this.lblimageChooser.setSize(140,140);
        image = image.getScaledInstance(lblimageChooser.getWidth(), lblimageChooser.getHeight(),  java.awt.Image.SCALE_SMOOTH);
        lblimageChooser.setIcon(new ImageIcon(image));


        Menu.this.lblimageChooser.addMouseListener(clickSelectImage);

        Menu.this.pnlImagesList.add(Menu.this.lblimageChooser);
        Menu.this.pnlImagesList.revalidate();
        Menu.this.pnlImagesList.repaint();
    }

    /**
     * Method to move image around maze
     */
    ActionListener pressMoveImage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cbMoveImage.isSelected()){
                mazeLayeredPanel.setDraggable(true);
                mazeLayeredPanel.handleDrag();
                rbStart.setEnabled(false);
                rbFinish.setEnabled(false);
            }

            else{
                mazeLayeredPanel.setDraggable(false);
                mazeLayeredPanel.handleDrag();
                rbStart.setEnabled(true);
                rbFinish.setEnabled(true);
            }


        }
    };

    /**
     * Method to clear maze solution
     */
    ActionListener clickClear = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            for (JPanel[] panel_array: mazePanels) {
                for (JPanel panel: panel_array) {
                    if(panel != mazeLayeredPanel.getStartPanel() && panel != mazeLayeredPanel.getFinishPanel()) {
                        panel.setBackground(Color.white);
                    }
                }
            }
            maze.solution = null;
        }
    };

    /**
     * Method to select an image file and upload to the maze
     */
    MouseListener clickSelectImage = new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e){
            Dimension labelSize = mazeLayeredPanel.getLabelSize();
            JLabel currentLabel = (JLabel)e.getComponent();
            currentLabel.setSize(labelSize);
            ImageIcon currentImageIcon = (ImageIcon)currentLabel.getIcon();
            Image currentImage = currentImageIcon.getImage().getScaledInstance(lblimageChooser.getWidth(), lblimageChooser.getHeight(),  java.awt.Image.SCALE_SMOOTH);
            currentLabel.setIcon(new ImageIcon(currentImage));
            currentLabel.removeMouseListener(clickSelectImage);
            mazeLayeredPanel.importImage(currentLabel);
        }

    };

    ActionListener clickSolve = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            solveClicked();
        }
    };

    /**
     *
     */
    private void solveClicked(){
        if (maze.getStart() == null || maze.getFinish() == null) {
            JOptionPane.showMessageDialog(pnlMaze, "Both start and finish points must be selected before solving!");
        }

        if(maze.solution == null) {

            maze.solution = new MazeSolution(maze);
            if (maze.solution.getSolution() != null) {
                if(cbShowSolution.isSelected()) {
                    isVisible(true, maze.solution.getSolution());
                    pnlMaze.revalidate();
                    pnlMaze.repaint();
                }
            }
            else {
                maze.solution = null;
                JOptionPane.showMessageDialog(pnlMaze, "Maze has no solution!");
            }
        }

        else{
            JOptionPane.showMessageDialog(pnlMaze, "Please clear current solution.");
        }
    }

    /**
     * Method to toggle to show solution
     */
    ActionListener pressShow = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cbShowSolution.isSelected()){
                isVisible(true,maze.solution.getSolution());
            }

            else
                isVisible(false, maze.solution.getSolution());
        }
    };

    /**
     * Changes the background of the current panel for visual clarity of solution
     * @param visible A boolean statement for where the solution is visible or not.
     */
    public void isVisible(boolean visible, Cell[] cells) throws NullPointerException{
        try {
            if (!visible) {
                for (Cell cell : cells) {
                    if(cell != null) {
                        mazePanels[cell.get_xLocation()][cell.get_yLocation()].setBackground(Color.white);
                        pnlMaze.revalidate();
                        pnlMaze.repaint();
                    }
                }
            } else {
                for (Cell cell : cells) {
                    if (cell != null) {
                        mazePanels[cell.get_xLocation()][cell.get_yLocation()].setBackground(Color.YELLOW);
                        pnlMaze.revalidate();
                        pnlMaze.repaint();
                    }
                }
            }
        }

        catch (NullPointerException n){
            System.err.println(n);
        }
    }

    /**
     * Create menu bar of the application
     * @return the cell containing the specified panel
     */

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.aboutMenu = new JMenu("About");
        this.imNewWindow = new JMenuItem("New Window");
        this.imImportMaze = new JMenuItem("Import Maze");
        this.imExportMaze = new JMenuItem("Export Maze");
        this.imSaveMazeImage = new JMenuItem("Save Maze as Image");
        this.imImportImage = new JMenuItem("Import Image");
        this.imAboutApplication = new JMenuItem("About Application");
        this.fileMenu.add(this.imNewWindow);
        this.fileMenu.add(this.imImportMaze);
        this.fileMenu.add(this.imExportMaze);
        this.fileMenu.add(this.imSaveMazeImage);
        this.aboutMenu.add(this.imAboutApplication);
        this.fileMenu.addActionListener(this);
        this.aboutMenu.addActionListener(this);
        this.imNewWindow.addActionListener(this::imNewWindowClicked);
        this.imExportMaze.addActionListener(exportClicked);
        this.imImportMaze.addActionListener(this);
        this.imSaveMazeImage.addActionListener(this::imSaveMazeImageClicked);
        this.imImportImage.addActionListener(this);
        this.imAboutApplication.addActionListener(this::aboutApplicationClicked);
        menuBar.add(this.fileMenu);
        menuBar.add(this.aboutMenu);
        return menuBar;
    }

    /**
     * Click "About Application" in menu "About" now displays a message that shows what this application does.
     */
    private void aboutApplicationClicked(ActionEvent actionEvent){
        JOptionPane.showMessageDialog(this,"This is a computer-assisted maze design application.",
                "About Application", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void imSaveMazeImageClicked(ActionEvent e){
        saveMazeImage();
    }

    /**
     *  Method to save the maze as a PNG file to a designated directory
     */
    private void saveMazeImage(){
        Rectangle rect = pnlMaze.getBounds();
        BufferedImage captureImage =
                new BufferedImage(rect.width, rect.height,
                        BufferedImage.TYPE_INT_ARGB);
        pnlMaze.paint(captureImage.getGraphics());

        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Files","png");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Save as...");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try{
                File fileToSave = fileChooser.getSelectedFile();
                if (fileToSave != null && !fileToSave.getName().endsWith(".png")) {
                    // force the png extension
                    fileToSave = new File(fileToSave.getPath() + ".png");
                }
                ImageIO.write(captureImage,"png",fileToSave);
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            }catch (IOException ex){
                System.err.println(ex);
            }
        }
        dispose();
    }

    /**
     * Add functionality to New Maze menu by running a new Maze designing program
     */
    private void imNewWindowClicked(ActionEvent e){
        createGUI();
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == imImportMaze) {
            f.dispose();
            SwingUtilities.invokeLater(new MazeImport());
        }
    }

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
     * Method to create maze panel
     * @param maze
     */
    public void createMazePanel(Maze maze){
        generateMazePanel();
        if (maze.solution != null){
            isVisible(true, maze.solution.getSolution());
            pnlMaze.revalidate();
            pnlMaze.repaint();
        }
    }

    /**
     * Method to update borders of cells
     * @param borders
     * @param panel
     */
    public void updateBorders(int[] borders, JPanel panel){
        panel.setBorder(BorderFactory.createMatteBorder(borders[0],borders[1],borders[2],borders[3],Color.BLACK));

    }

    /**
     * Method to export maze to database
     */
    ActionListener exportClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel label = new JLabel();
            int result = JOptionPane.showConfirmDialog(f,"Do you want to export current maze to database?",
                    "Maze Export",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                label.setText("You selected: Yes");
                DataSource source = new DataSource();
                try {
                    mazeLayeredPanel.getListPathsAndPoints();
                    source.addNewMaze(maze);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else if (result == JOptionPane.NO_OPTION){
                label.setText("You selected: No");
                dispose();
            }else {
                label.setText("None selected");
            }
        }
    };

    private void retrieveImage(String filePath, Point point){
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage();
        Menu.this.lblimageChooser = new JLabel("", 0);
        Menu.this.lblimageChooser.setName(filePath);
        Menu.this.lblimageChooser.setSize(mazePnl.getWidth()/maze.COLUMNS , mazePnl.getHeight()/maze.ROWS);
        image = image.getScaledInstance(lblimageChooser.getWidth(), lblimageChooser.getHeight(),  java.awt.Image.SCALE_SMOOTH);
        lblimageChooser.setIcon(new ImageIcon(image));
        mazePanels[point.x][point.y].add(lblimageChooser);

    }

}

