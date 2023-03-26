package GUI;

import MazeDatabase.ReceivedData;
import MazeGenerator.Maze;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MazeImport extends JFrame implements ActionListener, Runnable {

    // maze import primary menu bar items
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu aboutMenu;

    // maze import secondary menu bar items
    private JMenuItem imNewMaze;
    private JMenuItem imExportMaze;
    private JMenuItem imImportMaze;
    private JMenuItem imImportImage;
    private JMenuItem imAboutMaze;
    private JMenuItem imAboutAuthor;
    private JMenuItem imAboutApplication;

    private JComboBox searchDropdown;
    private JTextField searchBar;
    private JPanel searchResults;
    private JFrame frame;

    /**
     * The instance of mazes list
     */
    private DefaultListModel mazes;

    /**
     * create and populate window menu bar
     * @return menu bar element
     */
    private JMenuBar createMenuBar(){

        JMenuBar menuBar = new JMenuBar();

        // Initialise
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        aboutMenu = new JMenu("About");
        imNewMaze = new JMenuItem("New Maze");
        imNewMaze.addActionListener(newMazeClicked);
        imImportMaze = new JMenuItem("Import Maze");
        imExportMaze = new JMenuItem("Export Maze");
        imImportImage = new JMenuItem("Import Image");
        imAboutMaze = new JMenuItem("About Maze");
        imAboutAuthor = new JMenuItem("About Author");
        imAboutApplication = new JMenuItem("About Application");


        //Add items to menu
        fileMenu.add(imNewMaze);
        fileMenu.add(imImportMaze);
        fileMenu.add(imExportMaze);
        editMenu.add(imImportImage);
        aboutMenu.add(imAboutMaze);
        aboutMenu.add(imAboutAuthor);
        aboutMenu.add(imAboutApplication);

        //Disable non-required menu items
        editMenu.setEnabled(false);
        imImportMaze.setEnabled(false);
        imExportMaze.setEnabled(false);
        imImportImage.setEnabled(false);
        imAboutMaze.setEnabled(false);
        imAboutAuthor.setEnabled(false);


        // Add ActionListener
        fileMenu.addActionListener(this);
        aboutMenu.addActionListener(this);
        imNewMaze.addActionListener(this);
        imAboutApplication.addActionListener(this);

        // Add menu to menuBar.
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);

        return menuBar;

    }

    /**
     * add menu bar to top and padding surrounding a given frame
     * @param windowFrame frame to pad
     */
    private void padFrame(JFrame windowFrame) {
        
        // pad given frame with empty panels
        windowFrame.getContentPane().add(createMenuBar(), BorderLayout.NORTH);
        windowFrame.getContentPane().add(new JPanel(), BorderLayout.EAST);
        windowFrame.getContentPane().add(new JPanel(), BorderLayout.SOUTH);
        windowFrame.getContentPane().add(new JPanel(), BorderLayout.WEST);
        this.frame = windowFrame;

    }

    /**
     * create main page headline
     * @param headlineText text to be displayed in headline
     * @return panel containing headline element
     */
    private JPanel createHeadline(String headlineText) {
        
        // create headline container and add styled headline element
        JPanel headlineContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JLabel headline = new JLabel(headlineText);
        headline.setFont(new Font(headline.getFont().getName(), headline.getFont().getStyle(), 20));
        headlineContainer.add(headline);
        
        return headlineContainer;
    }

    /**
     * create search element with accessibility label and wrapping container
     * @param elementLabel accessibility label content
     * @param searchElement search component to wrap
     * @param elementSize search component sizing
     * @return panel containing search component and label elements
     */
    private JPanel createSearchElement(String elementLabel, Component searchElement, Dimension elementSize) {

        // create element container and label + resize search element
        JPanel searchElementContainer = new JPanel(new GridLayout(2,1));
        JLabel searchElementLabel = new JLabel(elementLabel);
        searchElement.setPreferredSize(elementSize);

        // add label and search element to container
        searchElementContainer.add(searchElementLabel);
        searchElementContainer.add(searchElement);

        return searchElementContainer;

    }


    /**
     * create and display maze import window gui
     */
    private void createGUI() {

        // create and set up window frame
        JFrame windowFrame = new JFrame("Search and Open Maze");
        windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        windowFrame.setPreferredSize(new Dimension(750, 500));
        windowFrame.setLocation(new Point(200, 200));

        // pad frame border with whitespace
        this.padFrame(windowFrame);

        // create scrollable content container
        JPanel scrollableContainer = new JPanel(new BorderLayout());
        scrollableContainer.setPreferredSize(new Dimension(0, 1800));

        // create header and search element containers
        JPanel headerContainer = new JPanel(new BorderLayout());
        JPanel searchElementsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));

        // create headline text container
        JPanel headlineContainer = createHeadline("Search and Open Maze:");

        // create search property dropdown element
        String[] dropdownOptions = { "Name", "Author", "Difficulty"};
        searchDropdown = new JComboBox(dropdownOptions);
        JPanel searchDropdownContainer = createSearchElement("In Maze Property:", searchDropdown,
                new Dimension(130, 30));

        // create search bar input element
        searchBar = new JTextField("", 30);
        JPanel searchBarContainer = createSearchElement("Search For:", searchBar,
                new Dimension(300, 30));

        // create search button element
        JButton searchButton = new JButton("Go!");
        searchButton.addActionListener(searchClicked);
        JPanel searchButtonContainer = createSearchElement("", searchButton,
                new Dimension(60, 30));
        
        // add search elements to search container
        searchElementsContainer.add(searchDropdownContainer);
        searchElementsContainer.add(searchBarContainer);
        searchElementsContainer.add(searchButtonContainer);

        // add headline text and search elements to header container
        headerContainer.add(headlineContainer, BorderLayout.NORTH);
        headerContainer.add(searchElementsContainer, BorderLayout.CENTER);
        JPanel showAllContainer = new JPanel();
        JButton show = new JButton("SHOW ALL");
        show.addActionListener(showAll);
        showAllContainer.add(show, BorderLayout.CENTER);
        headerContainer.add(showAllContainer,BorderLayout.SOUTH);

        // create search result container
        searchResults = new JPanel();
        searchResults.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        searchResults.setPreferredSize(new Dimension(500, 500));


        // add header and search results containers to content scrollable container
        scrollableContainer.add(headerContainer, BorderLayout.NORTH);
        scrollableContainer.add(searchResults, BorderLayout.CENTER);
        windowFrame.add(BorderLayout.CENTER, new JScrollPane(scrollableContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        // display window frame
        windowFrame.pack();
        windowFrame.setVisible(true);

    }

    /**
     *  Takes user input and search for maze by either maze name, author or maze difficulty
     */
    ActionListener searchClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ReceivedData data = new ReceivedData();
            int option = searchDropdown.getSelectedIndex();

            switch (option){
                case 0 :
                    data.getMazeByName(searchBar.getText());
                    break;

                case 1 :
                    data.getMazeByAuthor(searchBar.getText());
                    break;

                case 2 :
                    data.getMazeByDifficulty(Integer.parseInt(searchBar.getText()));
                    break;

            }
            mazes = data.getList();
            if(!mazes.isEmpty()) {
                searchResults.removeAll();
                displayResults();
            }
            else{
                searchResults.removeAll();
                JOptionPane.showMessageDialog(searchBar, "No Mazes Found!");
            }
        }
    };

    /**
     * Display mazes that match user inputs
     */
    private void displayResults(){
        int index = 0;
        while(index < mazes.getSize()) {
            Maze maze = (Maze) mazes.getElementAt(index);
            MazeCard card = new MazeCard("placeholder.jpg", maze.getMazeName(), maze.getMazeDifficulty(), maze.getMazeAuthor(), maze.getMazeSolvable(),
                    maze.getDateCreated(), maze.getDateEdited());
            card.setParent(this.frame);
            card.setMaze(maze);
            searchResults.add(card.GetCard());
            index++;
        }
        searchResults.revalidate();
        searchResults.repaint();
    }

    /**
     * Show all mazes stored in database
     */
    ActionListener showAll = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ReceivedData data = new ReceivedData();
            data.getAllData();
            mazes = data.getList();
            if(!mazes.isEmpty()) {
                searchResults.removeAll();
                displayResults();
            }
            else{
                searchResults.removeAll();
                JOptionPane.showMessageDialog(searchBar,"No Mazes Found!");
            }
        }
    };

    /**
     * Opens a new window of the maze application
     */
    ActionListener newMazeClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MazeImport.this.frame.dispose();
            Menu menu = new Menu();
            menu.createGUI();
        }
    };
    @Override
    public void actionPerformed(ActionEvent e) {


    }

    @Override
    public void run() {
        createGUI();
    }


}
