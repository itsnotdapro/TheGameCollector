import javax.swing.*;
import java.awt.*;
import java.util.Date;

/** Main driver class of the application. Handles creating UI, user input, and console commands
* @author 19076935 */
public class Application {
    private final JPanel panel = new JPanel();
    private JPanel games = new JPanel();

    public Application() {
        games.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(games);
        scrollPane.setPreferredSize(new Dimension(300, (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 1.2)));
        GridBagConstraints constraint1 = new GridBagConstraints();
        constraint1.gridy = 0;
        constraint1.gridx = 0;
        GridBagConstraints constraint2 = new GridBagConstraints();
        constraint2.gridy = 1;
        constraint2.gridx = 0;
        GridBagConstraints constraint3 = new GridBagConstraints();
        constraint3.gridy = 2;
        constraint3.gridx = 0;

        try {
            games.add(new GamePanel(new Game("Half-Life", 30.00f, Platform.PC, new Date(), true)), constraint1);
            games.add(new GamePanel(new Game("SuperHot", 30.00f, Platform.PC, new Date(), true)), constraint2);
            games.add(new GamePanel(new Game("Half-Life: Alyx", 30.00f, Platform.PC, new Date(), true)), constraint3);
        } catch (Exception e) { e.printStackTrace(); }
        GridBagConstraints spacer = new GridBagConstraints();
        spacer.weighty = 1;
        spacer.gridy = 3;
        games.add(new JPanel(), spacer);

        panel.add(scrollPane);
    }

    /** Program launch point
    * @param args Console arguments
    * @author 19076935 */
    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equals("c") || arg.equals("console")) { consoleMain(args); }
        }
        JFrame frame = new JFrame("Application");

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }

        frame.setContentPane(new Application().panel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width / 1.2),
                                             (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 1.2)));
        frame.pack();
        frame.setVisible(true);
    }

    /** Launch point for the program when run in console mode
    * @param args Console arguments
    * @author 19076935 */
    public static void consoleMain(String[] args) {
        Game game1 = new Game("Half-Life", 30.00f, Platform.PC, new Date(), true);

        System.out.println(game1.getAPIData());

        //Game game2 = new Game("SuperHot", 20.00f, Platform.PS4, new Date());
        //Game game3 = new Game("SuperHot", 10.00f, Platform.GC, new Date());
        //Game game4 = new Game("SuperHot", 60.00f, Platform.PC, new Date());
        //Game game5 = new Game("SuperHot", 100.00f, Platform.THREEDS, new Date());

        //Library db = new Library("game.bin");

        //db.read();

        //System.out.println(db);
        System.exit(0);
    }
}
