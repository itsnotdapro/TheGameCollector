package Library;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Data.Config;
import Exceptions.Log;

public class Library implements Config, Iterable<Game> {
	private final String path = "data/col.db";
	
    private ArrayList<Game> array = new ArrayList<Game>();
    
    public Library() {  }

    public boolean add(Game addition) { 
    	array.add(addition); 
    	try { write(); } 
    	catch (IOException e) { 
    		new Log(e.getMessage()); 
    		return false;
    		}
    	return true;
    }
    public boolean remove(Game reduction) { 
    	array.remove(reduction); 
    	try { write(); } 
    	catch (IOException e) { 
    		new Log(e.getMessage());
    		return false;
    	}
    	return true;
    }
    
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
    
    public boolean update(Game index, Game data) {
    	array.set(array.indexOf(index), data);
    	try { write(); } 
    	catch (IOException e) { 
    		new Log(e.getMessage());
    		return false;
    	}
    	return true;
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
    
    public void sort(SortingMethod method) {
    	Collections.sort(array, Sort.get(method));
    }
    
    public Library filter(String term) {
    	Library filteredLibrary = new Library();
    	for (Game item : array) {
    		if (item.getTitle().toLowerCase().contains(term.toLowerCase())) {
    			
    			filteredLibrary.add(item);
    		}
    	}
    	return filteredLibrary;
    }
    
    public Library filter(boolean hasData) {
    	Library filteredLibrary = new Library();
    	for (Game item : array) {
    		if (item.hasData() == hasData) { filteredLibrary.add(item); }
    	}
    	return filteredLibrary;
    }

    public int size() {return array.size();}
    
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
