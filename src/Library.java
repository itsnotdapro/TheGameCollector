import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library implements Serializable {
    public List<Game> array = new ArrayList<Game>();
    private final String path;

    public Library(String path) {
        this.path = path;
    }

    public void add(Game addition) {
        array.add(addition);
    }

    public void write() {
        try {
            FileOutputStream out = new FileOutputStream(path);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(this);
            objOut.close();
            out.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void read() {
        try {
            FileInputStream in = new FileInputStream(path);
            ObjectInputStream objIn = new ObjectInputStream(in);
            Library db = (Library)objIn.readObject();
            array = db.array;
            objIn.close();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public String toString() {
        String out = "Library: \n";
        for (Game game : array) {
            out += game.toString() + "\n";
        }
        return out;
    }
}
