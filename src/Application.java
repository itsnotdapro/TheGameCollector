import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/** Main driver class of the application. Handles creating UI, user input, and console commands
* @author 19076935 */
public class Application {
    private final JPanel panel = new JPanel();
    private JPanel games = new JPanel(), controls = new JPanel();
    private JButton button = new JButton();
    private JTextField nameField = new JTextField("", 20),
                       priceField = new JTextField("", 5);
    private JComboBox<Platform> platformField = new JComboBox<Platform>();
    private Library library;

    public Application() {
        panel.setLayout(new GridLayout());
        games.setLayout(new GridBagLayout());
        controls.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(games);
        scrollPane.setPreferredSize(new Dimension(300, (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 1.2)));

        // Creating the UI
        GridBagConstraints nameFieldConstraint = new GridBagConstraints(),
                           nameLabelConstraint = new GridBagConstraints();
        nameFieldConstraint.gridx = 1; nameLabelConstraint.gridx = 0;
        nameFieldConstraint.gridy = 0; nameLabelConstraint.gridy = 0;

        controls.add(nameField, nameFieldConstraint);
        controls.add(new JLabel("Name: "), nameLabelConstraint);

        GridBagConstraints priceFieldConstraint = new GridBagConstraints(),
                           priceLabelConstraint = new GridBagConstraints();
        priceFieldConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        priceFieldConstraint.gridx = 1; priceLabelConstraint.gridx = 0;
        priceFieldConstraint.gridy = 1; priceLabelConstraint.gridy = 1;
        controls.add(priceField, priceFieldConstraint);
        controls.add(new JLabel("Price: $ "), priceLabelConstraint);

        platformField.setModel(new DefaultComboBoxModel<>(Platform.values()));
        GridBagConstraints platformFieldConstraint = new GridBagConstraints(),
                           platformLabelConstraint = new GridBagConstraints();
        platformFieldConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        platformFieldConstraint.gridx = 1; platformLabelConstraint.gridx = 0;
        platformFieldConstraint.gridy = 2; platformLabelConstraint.gridy = 2;
        controls.add(platformField, platformFieldConstraint);
        controls.add(new JLabel("Platform: "), platformLabelConstraint);

        button.setText("Add");
        //controls.add(button);


        // Initialising the library on startup //
        try {
            library = new Library("data/db.bin");
            //library.write();
            library.read();
            library.add(new Game("Half-Life", 12.99f, Platform.PC, new Date(), false));
            library.add(new Game("SuperHot", 30f, Platform.PC, new Date(), false));
            library.add(new Game("Half-Life: Alyx", 60f, Platform.PC, new Date(), false));
        } catch (Exception e) { e.printStackTrace(); }


        // Writing the library to the UI //
        int i = 0;
        for (Game game : library) {
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.gridx = 0;
            constraint.gridy = i;
            games.add(new GamePanel(game), constraint);
            ++i;
        }

        GridBagConstraints spacer = new GridBagConstraints();
        spacer.weighty = 1;
        spacer.gridy = 10000;
        games.add(new JPanel(), spacer);

        //////////////////////////
        // ** Event handlers ** //
        //////////////////////////

        // Adds a new game
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridBagConstraints constraint = new GridBagConstraints();
                constraint.gridx = 0;
                constraint.gridy = library.size();

                Game addition = new Game("Half-Life: Alyx", 30, Platform.PC, new Date(), false);
                library.add(addition);

                games.add(new GamePanel(addition), constraint);

                games.revalidate();
                games.repaint();
            }
        });

        // Final adds
        panel.add(scrollPane);
        panel.add(controls);
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
