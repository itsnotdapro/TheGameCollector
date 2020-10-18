package Library;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Data.Config;
import Exceptions.Log;
/** Object that holds an array of game objects, and can be added to, removed from, and updated
 * @author 19076935 */
@SuppressWarnings("serial")
public class Library implements Config, Iterable<Game> {
	private final String path = "data/col.db";
	
    private ArrayList<Game> array = new ArrayList<Game>();
    
    /** Constructor to create a library
     * @author 19076935 */
    public Library() {  }

    /** Adds a game to the library
     * @param addition The game to add
     * @return If the game was successfully added
     * @author 19076935 */
    public boolean add(Game addition) { 
    	array.add(addition); 
    	try { write(); } 
    	catch (IOException e) { 
    		new Log(e.getMessage()); 
    		return false;
    		}
    	return true;
    }
    
    /** Removes a game from the library
     * @param reduction The game to remove
     * @return If the game was successfully removed
     * @author 19076935 */
    public boolean remove(Game reduction) { 
    	array.remove(reduction); 
    	try { write(); } 
    	catch (IOException e) { 
    		new Log(e.getMessage());
    		return false;
    	}
    	return true;
    }
    
    /** Updates a game in the library
     * @param index The game to update
     * @param data The game data to change it to
     * @return If the game was successfully updated
     * @author 19076935 */
    public boolean update(Game index, Game data) {
    	array.set(array.indexOf(index), data);
    	try { write(); } 
    	catch (IOException e) { 
    		new Log(e.getMessage());
    		return false;
    	}
    	return true;
    }
    
    /** Gets the game at index i
     * @param i The index to search
     * @return The game at that index
     * @author 19076935 */
    public Game atIndex(int i) {
    	return array.get(i);
    }

    @Override
    public void write() throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(this);
        objOut.close();
        out.close();
    }


    @Override
    public void read() throws IOException {
        try {
            FileInputStream in = new FileInputStream(path);
            ObjectInputStream objIn = new ObjectInputStream(in);
            Library db = (Library)objIn.readObject();
            array = db.array;
            objIn.close();
            in.close();
        } catch (FileNotFoundException e) { clear();
        } catch (EOFException e) { // catch this lmao
        } catch (Exception e) { new Log(e.getMessage()); }
    }

    @Override
    public boolean clear() {
        try { new File(path).createNewFile(); }
        catch (IOException e) { 
        	new Log(e.getMessage());
        	return false;
        }
        return true;
    }
    
    /** Sorts the library
     * @param method The sorting method to utilize
     * @author 19076935 */
    public void sort(SortingMethod method) {
    	Collections.sort(array, Sort.get(method));
    }
    
    /** Filters the library based on a specific term
     * @param term The search term
     * @return The filtered library
     * @author 19076935 */
    public Library filter(String term) {
    	Library filteredLibrary = new Library();
    	for (Game item : array) {
    		if (item.getTitle().toLowerCase().contains(term.toLowerCase())) {
    			
    			filteredLibrary.add(item);
    		}
    	}
    	return filteredLibrary;
    }
    
    /** Filters the library based on a whether it has API data
     * @param term Whether the game has API data
     * @return The filtered library
     * @author 19076935 */
    public Library filter(boolean hasData) {
    	Library filteredLibrary = new Library();
    	for (Game item : array) {
    		if (item.getResults().isEmpty() != hasData) { filteredLibrary.add(item); }
    	}
    	return filteredLibrary;
    }

    /** @return The size of the library @author 19076935 */
    public int size() {return array.size();}
    
    /** Gets the sorted library as a string
     * @param sort The sorting method to use
     * @return The string representation of the sorted library
     * @author 19076935 */
    public String stringSorted(SortingMethod sort) {
    	if (array.isEmpty()) { return "Collection: \n\nN/A"; }
    	Library sorted = this;
    	if (sort.requiresData()) { sorted = sorted.filter(true); }
    	sorted.sort(sort);
        String out = "Collection" + (sort.requiresData() ? " (Games missing data have been hidden):" : ":") + "\n";
        for (Game game : sorted.array) {
            out += "\n- " + game.toString() + "\n";
        }
        if (sorted.array.isEmpty()) { out += " \nN/A"; }
        return out;
    }

    @Override
    public String toString() {
    	if (array.isEmpty()) { return "Collection is empty"; }
        String out = "Collection: \n";
        for (Game game : array) {
            out += "\n- " + game.toString() + "\n";
        }
        return out;
    }

    @Override
    public Iterator<Game> iterator() { return array.iterator(); }
}
