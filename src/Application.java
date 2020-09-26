import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Application {
    private JPanel panel;

    public Application() {

    }

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equals("c") || arg.equals("console")) { consoleMain(args); }
        }
        JFrame frame = new JFrame("Application");
        frame.setContentPane(new Application().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }

    public static void consoleMain(String[] args) {
        //Game game1 = new Game("SuperHot", 30.00f, Platform.PC, new Date());
        //Game game2 = new Game("SuperHot", 20.00f, Platform.PS4, new Date());
        //Game game3 = new Game("SuperHot", 10.00f, Platform.GC, new Date());
        //Game game4 = new Game("SuperHot", 60.00f, Platform.PC, new Date());
        //Game game5 = new Game("SuperHot", 100.00f, Platform.THREEDS, new Date());

        Library db = new Library("game.bin");

        db.read();

        System.out.println(db);
        System.exit(0);
    }
}
