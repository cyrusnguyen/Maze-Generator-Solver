package GUI;

import MazeGenerator.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MazeCard {

    // maze card default values
    public static final Dimension DEFAULT_IMAGE_DIMENSION = new Dimension(180, 100);
    public static final int DEFAULT_MAXIMUM_DIFFICULTY = 5;

    // maze card image and description fields
    private String imagePath;
    private Dimension imageDimension;
    private String name;
    private int maximumDifficulty;
    private int difficulty;
    private String difficultyString;
    private String author;
    private boolean solvable;
    private String solvableString;
    private String createdDate;
    private String lastEditDate;
    private JPanel imageContainer;

    // complete maze card element
    private JPanel card;
    private Maze maze;
    JFrame parent;

    /**
     * Parent component setter
     * @param parent
     */
    public void setParent(JFrame parent){
        this.parent = parent;
    }

    /**
     * Maze setter
     * @param maze
     */
    public void setMaze(Maze maze){
        this.maze = maze;

    }
    /**
     *  thumbnail image path setter
     * @param imagePath absolute path of thumbnail file
     */
    private void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * thumbnail image path getter
     * @return absolute path of thumbnail file
     */
    public String GetImagePath() {
        return this.imagePath;
    }

    /**
     * thumbnail dimension setter
     * @param imageDimension dimension of thumbnail
     */
    private void setImageDimension(Dimension imageDimension) {
        this.imageDimension = imageDimension;
    }

    /**
     * thumbnail dimension getter
     * @return dimension of thumbnail
     */
    public Dimension GetImageDimension() {
        return this.imageDimension;
    }

    /**
     * upper bound difficulty rating setter
     * @param maximumDifficulty upper bound of difficulty rating
     */
    private void setMaximumDifficulty(int maximumDifficulty) {
        this.maximumDifficulty = maximumDifficulty;
    }

    /**
     * upper bound difficulty rating getter
     * @return upper bound of difficulty rating
     */
    public int GetMaximumDifficulty() {
        return this.maximumDifficulty;
    }

    /**
     * maze name setter
     * @param name maze name
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * maze name getter
     * @return maze name
     */
    public String GetName() {
        return this.name;
    }

    /**
     * maze difficulty setter
     * @param difficulty maze difficulty
     */
    private void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        this.difficultyString = "★".repeat(this.difficulty) +
                "☆".repeat(this.GetMaximumDifficulty() - this.difficulty);
    }

    /**
     * maze difficulty getter
     * @return maze difficulty
     */
    public int GetDifficulty() {
        return this.difficulty;
    }

    /**
     * star icon formatted difficulty display getter
     * @return star icon formatted difficulty display
     */
    public String GetDifficultyString() {
        return this.difficultyString;
    }

    /**
     * maze author setter
     * @param author maze author
     */
    private void setAuthor(String author) {
        this.author = author;
    }

    /**
     * maze author getter
     * @return maze author
     */
    public String GetAuthor() {
        return this.author;
    }

    /**
     * maze is solvable status setter
     * @param solvable boolean maze is solvable status
     */
    private void setSolvable(boolean solvable) {
        this.solvable = solvable;
        this.solvableString = ((!this.solvable) ? "NOT " : "") + "SOLVABLE";
    }

    /**
     * maze is solvable status setter
     * @return boolean maze is solvable status
     */
    public boolean GetSolvable() {
        return this.solvable;
    }

    /**
     * maze is solvable status string getter
     * @return formatted maze is solvable status string
     */
    public String GetSolvableString() {
        return this.solvableString;
    }

    /**
     * maze creation date setter
     * @param createdDate maze creation date
     */
    private void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * maze creation date getter
     * @return maze creation date
     */
    public String GetCreatedDate() {
        return this.createdDate;
    }

    /**
     * maze last edit date setter
     * @param lastEditDate maze last edit date
     */
    private void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    /**
     * maze last edit date getter
     * @return maze last edit date
     */
    public String GetLastEditDate() {
        return this.lastEditDate;
    }

    /**
     * complete maze card element getter
     * @return complete maze card element
     */
    public JPanel GetCard() {
        return this.card;
    }

    /**
     * create description element with accessibility label for maze card description
     * @param descriptionProperty description label content
     * @param propertyValue description content
     * @param spacing vertical spacing between description property and value labels
     * @return description container element with property and value labels
     */
    private JPanel createDescriptionLabel(String descriptionProperty, String propertyValue,
                                          int spacing) {

        // create description element container and remove any padding/borders
        JPanel descriptionLabelContainer = new JPanel(new GridLayout(2, 1, 0, spacing));
        descriptionLabelContainer.setBorder(new EmptyBorder(0, 0, 0, 0));

        // create description property label, reduce its size and add description content
        JLabel descriptionPropertyLabel = new JLabel(descriptionProperty);
        descriptionPropertyLabel.setFont(new Font(descriptionPropertyLabel.getFont().getName(),
                descriptionPropertyLabel.getFont().getStyle(), 9));
        JLabel descriptionValueLabel = new JLabel(propertyValue);

        // add description property label and content to element container
        descriptionLabelContainer.add(descriptionPropertyLabel);
        descriptionLabelContainer.add(descriptionValueLabel);

        return descriptionLabelContainer;

    }

    /**
     * create description element with accessibility label for maze card description
     * @param descriptionProperty description label content
     * @param propertyValue description content
     * @return description container element with property and value labels
     */
    private JPanel createDescriptionLabel(String descriptionProperty, String propertyValue) {

        // call createDescriptionLabel with default property and value label spacing size
        JPanel descriptionLabelContainer = this.createDescriptionLabel(descriptionProperty, propertyValue,
                -17);
        return descriptionLabelContainer;

    }

    /**
     * combine two panel elements horizontally in a containing panel
     * @param leftElement panel to be placed left in joined container panel
     * @param rightElement panel to be placed right in joined container panel
     * @return container panel containing provided left and right panel elements.
     */
    private JPanel horizontallyCombineElements(JPanel leftElement, JPanel rightElement) {

        // create container panel to hold provided left and right elements
        JPanel combinedLabels = new JPanel(new BorderLayout());

        // add left and right elements to container panel
        combinedLabels.add(leftElement, BorderLayout.WEST);
        combinedLabels.add(rightElement, BorderLayout.EAST);

        return combinedLabels;

    }

    /**
     * read maze thumbnail image to and attach its content to a label
     * @return maze thumbnail image label container
     */
    private JPanel createImage() {

        // create image container and set its size predetermined dimensions with top and bottom padding
        imageContainer = new JPanel(new BorderLayout());
        imageContainer.setSize(this.GetImageDimension());
        imageContainer.setBorder(new EmptyBorder(10, 0, 10, 0));
        imageContainer.setBackground(Color.LIGHT_GRAY);
        JLabel imageLabel;

        try {

            // open predetermined path to maze image, read its contents and resize it to fit its container
            File imageFile = new File(this.GetImagePath());
            Image image = ImageIO.read(imageFile);
            Image resizedImage = image.getScaledInstance(this.GetImageDimension().width, this.GetImageDimension().height,
                    Image.SCALE_SMOOTH);

            // add image to label and container when successfully read
            imageLabel = new JLabel(new ImageIcon(resizedImage));
            imageContainer.add(imageLabel, BorderLayout.SOUTH);


        } catch (java.io.IOException readError) {

            // add warning message to image label on read error
            imageLabel = new JLabel("Could Not Import Image!");
            imageLabel.setPreferredSize(this.GetImageDimension());
            imageContainer.add(imageLabel, BorderLayout.CENTER);

        }

        return imageContainer;

    }

    /**
     * create description display by combining all card description elements.
     * @return panel of all card description elements.
     */
    private JPanel createDescription(){

        // create description container
        JPanel description = new JPanel(new GridLayout(4, 1));
        description.setBorder(new EmptyBorder(0, 10, 10, 10));


        // create maze name and difficulty line of description
        JPanel mazeName = createDescriptionLabel("Maze Name:", this.GetName());
        JPanel difficulty = createDescriptionLabel("Difficulty:", this.GetDifficultyString());
        JPanel nameAndDifficultyDescription = horizontallyCombineElements(mazeName, difficulty);


        // create maze author and solvable status line of description
        JPanel author = createDescriptionLabel("Author:", this.GetAuthor());
        JPanel solvable = createDescriptionLabel("Status:", this.GetSolvableString());
        JPanel authorAndSolvableDescription = horizontallyCombineElements(author, solvable);

        // create date created and date last edited line of description
        JPanel dateCreated = createDescriptionLabel("Created:", this.GetCreatedDate(), -5);
        JPanel dateLastEdited = createDescriptionLabel("Last Edited:", this.GetLastEditDate(), -5);
        JPanel dateCreatedAndLastEdited = horizontallyCombineElements(dateCreated, dateLastEdited);
        dateCreatedAndLastEdited.setBorder(new EmptyBorder(0, 0, 10, 0));

        // create open maze button
        JPanel openContainer = new JPanel(new BorderLayout());
        JButton open = new JButton("Open");
        open.addActionListener(openClicked);
        openContainer.add(open, BorderLayout.CENTER);

        // add all description lines and open maze button to description container
        description.add(nameAndDifficultyDescription);
        description.add(authorAndSolvableDescription);
        description.add(dateCreatedAndLastEdited);
        description.add(openContainer);

        return description;
    }

    /**
     * Open a selected maze in maze import screen
     */
    private ActionListener openClicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MazeCard.this.parent.dispose();
            Menu menu = new Menu();
            menu.createGUI();
            menu.setMaze(maze);
            menu.createMazePanel(maze);
        }
    };

    /**
     * create maze image and description elements and combine them in a card container
     * @return maze information card container
     */
    private JPanel createCard() {

        // create and style maze information card container
        JPanel mazeCardContainer = new JPanel(new BorderLayout());
        mazeCardContainer.setPreferredSize(new Dimension(210, 310));
        mazeCardContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        // create and style maze information card
        JPanel mazeCard = new JPanel(new BorderLayout());
        mazeCard.setPreferredSize(new Dimension(200, 300));
        mazeCard.setBorder(new EmptyBorder(5, 5, 5, 5));

        // create maze card image and description
        JPanel image = createImage();
        JPanel description = createDescription();

        // add image and description to maze information card
        mazeCard.add(image, BorderLayout.NORTH);
        mazeCard.add(description, BorderLayout.CENTER);

        // add maze information card to its container
        mazeCardContainer.add(mazeCard, BorderLayout.CENTER);

        return mazeCardContainer;
    }

    /**
     * information card containing maze image and complete description
     * @param imagePath absolute path to maze thumbnail image
     * @param imageDimension dimension of maze thumbnail image
     * @param name maze name
     * @param maximumDifficulty upper bound of maze difficulty
     * @param difficulty maze difficulty
     * @param author maze author name
     * @param solvable maze solvable status
     * @param createdDate maze creation date
     * @param lastEditDate maze last edit date
     */
    public MazeCard(String imagePath, Dimension imageDimension, String name, int maximumDifficulty,
                    int difficulty, String author, boolean solvable, String createdDate, String lastEditDate) {

        // set initialized maze card properties
        this.setImagePath(imagePath);
        this.setImageDimension(imageDimension);
        this.setName(name);
        this.setMaximumDifficulty(maximumDifficulty);
        this.setDifficulty(difficulty);
        this.setAuthor(author);
        this.setSolvable(solvable);
        this.setCreatedDate(createdDate);
        this.setLastEditDate(lastEditDate);

        // create and set maze card element
        this.card = this.createCard();

    }

    /**
     * information card containing maze image and complete description
     * @param imagePath absolute path to maze thumbnail image
     * @param name maze name
     * @param difficulty maze difficulty
     * @param author maze author name
     * @param solvable maze solvable status
     * @param createdDate maze creation date
     * @param lastEditDate maze last edit date
     */
    public MazeCard(String imagePath, String name,
                    int difficulty, String author, boolean solvable, String createdDate, String lastEditDate) {

        // call maze card initializer with default image dimension and maximum difficulty parameters
        this(imagePath, DEFAULT_IMAGE_DIMENSION, name, DEFAULT_MAXIMUM_DIFFICULTY, difficulty,
                author, solvable, createdDate, lastEditDate);

    }

}
