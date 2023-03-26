package MazeDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.*;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MazeList {

    private final MazeDatabase database;
    private HashMap<String,String> mazeAuthor = new HashMap<>();
    private TreeSet<String> mazeNames = new TreeSet<>();
    private HashMap<String,String> creationDate = new HashMap();
    private HashMap<String,String> lastModifiedDateTime = new HashMap();
    private Date dateTime;
    private String dateTimeString;
    private Date modifiedDateTime;
    private String modifiedDateTimeString;
    private final DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
    private final DateFormat dateFormat2 = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");


    public MazeList(MazeDatabase database){
        this.database = database;
    }

    /**
     * Method to add a maze with the name of the maze and author
     * @param mazeName, the name of the maze to be added
     * @param authorName, the name of the maze author
     */
    public void addMaze(String mazeName,String authorName) throws MazeListException{
        if(mazeNames.contains(mazeName)){
            throw new MazeListException("Maze already exists in database");
        }
        if(mazeName == null || mazeName.isEmpty()){
            throw new MazeListException("Maze name is required.");
        }
        if(authorName==null || authorName.isEmpty()){
            throw new MazeListException("Maze author is required.");
        }
        else{
            mazeNames.add(mazeName);
            mazeAuthor.put(mazeName,authorName);
            dateTime = Calendar.getInstance().getTime();
            dateTimeString = dateFormat.format(dateTime);
            creationDate.put(mazeName,dateTimeString);
        }
    }

    /**
     * Method to change the author of a maze
     * @param mazeName, the name of the maze whose author name to be changed
     * @param newAuthorName, the new author name to be assigned to the maze
     */
    public void setMazeAuthor(String mazeName, String newAuthorName) throws MazeListException{
        if(!mazeNames.contains(mazeName)){
            throw new MazeListException("Maze does not exist in database");
        }
        if(newAuthorName == mazeAuthor.get(mazeName)){
            //System.out.println(mazeAuthor.get(mazeName));
            throw new MazeListException("Attempting to set same author for this maze.");
        }
        else{
            mazeAuthor.put(mazeName,newAuthorName);
        }
    }

    /**
     * Method to retrieve the author of a maze
     * @return  the author of a maze
     */
    public String getMazeAuthor (String mazeName) throws MazeListException{
        String authorName;
        if(!mazeNames.contains(mazeName)){
            throw new MazeListException("Maze does not exist in database.");
        }
        else{
            authorName = mazeAuthor.get(mazeName);
            return authorName;
        }
    }


    /**
     * Method to retrieve the list of mazes created by an author
     * @param author, the author whose list of created mazes to be displayed
     */
    public String getMazeName (String author) throws MazeListException{
        StringBuilder mazeList = new StringBuilder();
        if(!mazeAuthor.containsValue(author)){
            throw new MazeListException("Author "+ author+" not found in database.");
        }
        else{
            mazeList.append(getKeys(mazeAuthor,author));
        }
        return mazeList.toString();
    }


    /**
     * Method to find the corresponding maze given the author
     */
    public <K, V> Set<K> getKeys(Map<K, V> map, V value) {
        Set<K> keys = new HashSet<>();
        for (Map.Entry<K, V> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    /**
     * Method display list of mazes in alphabetical order
     */
    public String getMazeList(){
        StringBuilder sortedList = new StringBuilder();
        for (String maze : mazeNames){
            sortedList.append(maze).append("\n");
        }
        return sortedList.toString();
    }

    /**
     * Method to sort the authors in alphabetical order
     * @param map, the HashMap that contains the authors
     */
    private HashMap sortValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        //Custom Comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        //copying the sorted list in HashMap to preserve the iteration order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    /**
     * Method to display the author list in alphabetical order
     */
    public String getAuthorList(){
        StringBuilder build = new StringBuilder();
        Set set = mazeAuthor.entrySet();
        Iterator iterator = set.iterator();
        Map<Integer, String> map = sortValues(mazeAuthor);
        Set set2 = map.entrySet();
        Iterator iterator2 = set2.iterator();
        while(iterator2.hasNext()) {
            Map.Entry me2 = (Map.Entry)iterator2.next();
            build.append("Maze Name: "+me2.getKey() + "     Author: " + me2.getValue() + "\n");
        }
        return build.toString();
    }

    /**
     * Method to retrieve the date of when a maze is created
     * @param mazeName, the name of the maze whose date and time of creation to be retrieved
     */
    public String getCreationDate(String mazeName) throws MazeListException{
        String date;
        if(!mazeNames.contains(mazeName)){
            throw new MazeListException("Maze does not exist in database.");
        }
        else{
            date = creationDate.get(mazeName);
            return date;
        }
    }

    /**
     * Method to add date and time of last modified when a maze is modified
     * @param mazeName, which specifies the name of the maze that needs to have a date and time of last modified
     */
    public void setLastModifiedDateTime(String mazeName) throws MazeListException{
        if(!mazeNames.contains(mazeName)){
            throw new MazeListException("Maze does not exist in database.");
        }
        else{
            modifiedDateTime = Calendar.getInstance().getTime();
            modifiedDateTimeString = dateFormat2.format(modifiedDateTime);
            lastModifiedDateTime.put(mazeName,modifiedDateTimeString);
        }
    }

    /**
     * Method to retrieve the date and time of when a maze is last modified
     * @param mazeName, the name of the maze whose last modified date and time to be retrieved
     */
    public String getModifiedDateTime(String mazeName) throws MazeListException{
        String date;
        if(!mazeNames.contains(mazeName)){
            throw new MazeListException("Maze does not exist in database.");
        }
        if(isModified(mazeName)){
            date = lastModifiedDateTime.get(mazeName);
            return date;
        }
        else{
            return null;
        }
    }

    /**
     * Boolean to check if a maze has been modified
     * @param mazeName, the name of the maze to be added
     */
    private boolean isModified(String mazeName){
        if(lastModifiedDateTime.get(mazeName) == null){
            return false;
        }else{
            return true;
        }
    }

}