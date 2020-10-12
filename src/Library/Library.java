package Library;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Exceptions.Log;

public class Library implements Serializable, Iterable<Game> {
    private ArrayList<Game> array = new ArrayList<Game>();
    private final String path;

    public Library(String path) {
        this.path = path;
    }

    public void add(Game addition) { array.add(addition); }
    public void remove(Game reduction) { array.remove(reduction); }

    public void write() throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(this);
        objOut.close();
        out.close();
    }


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

    public void clear() {
        try { new File(path).createNewFile(); }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    public void sort(SortingMethod method) {
    	Collections.sort(array, Sort.get(method));
    }

    public int size() {return array.size();}

    @Override
    public String toString() {
        String out = "Library: \n";
        for (Game game : array) {
            out += game.toString() + "\n";
        }
        return out;
    }

    @Override
    public Iterator<Game> iterator() { return array.iterator(); }
}
