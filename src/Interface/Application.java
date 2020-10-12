package Interface;

import javax.swing.*;

import Data.Month;
import Library.Library;
import Library.Platform;
import Library.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.border.EmptyBorder;

/** Main driver class of the application. Handles creating UI, user input, and console commands
* @author 19076935 */
public class Application extends JFrame {
    private final JPanel panel = new JPanel();
    private JPanel games = new JPanel(), controls = new JPanel();
    private Library library;
    private final JPanel fieldsPanel = new JPanel();
    private final JLabel lblNewLabel = new JLabel("Name: ");
    private final JTextField nameField = new JTextField();
    private final JLabel lblNewLabel_1 = new JLabel("Price: ");
    private final JTextField priceField = new JTextField();
    private final JLabel lblNewLabel_2 = new JLabel("Platform: ");
    private final JComboBox<Platform> platformField = new JComboBox<Platform>();
    private final JLabel lblNewLabel_3 = new JLabel("Physical: ");
    private final JCheckBox physicalField = new JCheckBox("");
    private final JLabel lblNewLabel_4 = new JLabel("Date Purchased: ");
    private final JPanel datePanel = new JPanel();
    private final JComboBox<Month> monthField = new JComboBox<Month>();
    private final JTextField dayField = new JTextField();
    private final JTextField yearField = new JTextField();
    private final JPanel progressPanel = new JPanel();
    private final JButton btnNewButton = new JButton("Add");
    private final JLabel lblNewLabel_5 = new JLabel("Rating: ");
    private final JPanel ratingPanel = new JPanel();
    private final JRadioButton rate1 = new JRadioButton("");
    private final JRadioButton rate2 = new JRadioButton("");
    private final JRadioButton rate3 = new JRadioButton("");
    private final JRadioButton rate4 = new JRadioButton("");
    private final JRadioButton rate5 = new JRadioButton("");
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel lblNewLabel_6 = new JLabel("Progress");
    private final JPanel sortingPanel = new JPanel();
    private final JLabel lblNewLabel_7 = new JLabel("Sorting Method: ");
    private final JComboBox sortingMethod = new JComboBox();

    public Application() {
    	
    	priceField.setText("80.00");
    	priceField.setColumns(10);
    	nameField.setText("Half-Life: 2");
    	nameField.setColumns(10);
        panel.setLayout(new GridLayout());
        games.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(games);

        // Initialising the library on startup //
        try {
            library = new Library("data/db.bin");
            library.read();
            library.add(new Game("Half-Life", 12.99f, Platform.PC, new Date(),5 ));
            library.add(new Game("SuperHot", 30f, Platform.PC, new Date(), 5));
            library.add(new Game("Half-Life: Alyx", 60f, Platform.PC, new Date(), 5));
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
        
       JMenu fileMenu = new JMenu("File");
       JMenu editMenu = new JMenu("Edit");
       JMenuItem saveItem = new JMenuItem("Save");
       fileMenu.add(saveItem);
       JMenuBar menuBar = new JMenuBar();
       menuBar.add(fileMenu);
       menuBar.add(editMenu);
       this.setJMenuBar(menuBar);

        panel.add(scrollPane);
        controls.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(controls);
        
        controls.setLayout(new GridLayout(3, 1, 0, 0));
        
        controls.add(sortingPanel);
        lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
        
        sortingPanel.add(lblNewLabel_7);
        
        sortingPanel.add(sortingMethod);
        controls.add(fieldsPanel);
        GridBagLayout gbl_fieldsPanel = new GridBagLayout();
        fieldsPanel.setLayout(gbl_fieldsPanel);
        fieldsPanel.setPreferredSize(new Dimension(400, 200));
        
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        fieldsPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 0;
        fieldsPanel.add(nameField, gbc_nameField);
        
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        fieldsPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
        
        GridBagConstraints gbc_priceField = new GridBagConstraints();
        gbc_priceField.insets = new Insets(0, 0, 5, 0);
        gbc_priceField.fill = GridBagConstraints.HORIZONTAL;
        gbc_priceField.gridx = 1;
        gbc_priceField.gridy = 1;
        fieldsPanel.add(priceField, gbc_priceField);
        
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 2;
        fieldsPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
        
        GridBagConstraints gbc_platformField = new GridBagConstraints();
        gbc_platformField.insets = new Insets(0, 0, 5, 0);
        gbc_platformField.fill = GridBagConstraints.HORIZONTAL;
        gbc_platformField.gridx = 1;
        gbc_platformField.gridy = 2;
        platformField.setModel(new DefaultComboBoxModel<Platform>(Platform.values()));
        fieldsPanel.add(platformField, gbc_platformField);
        
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 3;
        fieldsPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
        yearField.setText("1970");
        yearField.setColumns(10);
        dayField.setText("1");
        dayField.setColumns(10);
        
        GridBagConstraints gbc_datePanel = new GridBagConstraints();
        gbc_datePanel.anchor = GridBagConstraints.WEST;
        gbc_datePanel.insets = new Insets(0, 0, 5, 0);
        gbc_datePanel.gridx = 1;
        gbc_datePanel.gridy = 3;
        FlowLayout flowLayout = (FlowLayout) datePanel.getLayout();
        fieldsPanel.add(datePanel, gbc_datePanel);
        
        datePanel.add(dayField);
        
        monthField.setModel(new DefaultComboBoxModel<Month>(Month.values()));
        datePanel.add(monthField);
        
        datePanel.add(yearField);
        
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_5.gridx = 0;
        gbc_lblNewLabel_5.gridy = 4;
        fieldsPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
        
        GridBagConstraints gbc_ratingPanel = new GridBagConstraints();
        gbc_ratingPanel.anchor = GridBagConstraints.WEST;
        gbc_ratingPanel.insets = new Insets(0, 0, 5, 0);
        gbc_ratingPanel.fill = GridBagConstraints.VERTICAL;
        gbc_ratingPanel.gridx = 1;
        gbc_ratingPanel.gridy = 4;
        fieldsPanel.add(ratingPanel, gbc_ratingPanel);
        RatingListener ratingListener = new RatingListener(rate1, rate2, rate3, rate4, rate5);
        rate1.addActionListener(ratingListener);
        rate2.addActionListener(ratingListener);
        rate3.addActionListener(ratingListener);
        rate4.addActionListener(ratingListener);
        rate5.addActionListener(ratingListener);
        
        ratingPanel.add(rate1);
        
        ratingPanel.add(rate2);
        
        ratingPanel.add(rate3);
        
        ratingPanel.add(rate4);
        
        ratingPanel.add(rate5);
        
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 5;
        fieldsPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
        
        GridBagConstraints gbc_physicalField = new GridBagConstraints();
        gbc_physicalField.insets = new Insets(0, 0, 5, 0);
        gbc_physicalField.anchor = GridBagConstraints.WEST;
        gbc_physicalField.gridx = 1;
        gbc_physicalField.gridy = 5;
        fieldsPanel.add(physicalField, gbc_physicalField);
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
        gbc_btnNewButton.gridx = 1;
        gbc_btnNewButton.gridy = 6;
        fieldsPanel.add(btnNewButton, gbc_btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		GridBagConstraints gbc = new GridBagConstraints();
        		gbc.gridx = 0;
        		gbc.gridy = library.size();
        		Game addition = new Game(nameField.getText(), 
        								 Float.parseFloat(priceField.getText()),
        								 (Platform)platformField.getSelectedItem(),
        								 new Date(), 5);
        		
        		new Thread(new Runnable() {
        			@Override
        			public void run() { 
        				addition.getData(progressBar); 
        				games.add(new GamePanel(addition), gbc);
                		library.add(addition);
                		games.revalidate();
                		games.repaint();
        				}
        		}).start();
        	}
        });
        
        controls.add(progressPanel);
        GridBagLayout gbl_progressPanel = new GridBagLayout();
        progressPanel.setLayout(gbl_progressPanel);
        
        GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
        gbc_lblNewLabel_6.weightx = 1.0;
        gbc_lblNewLabel_6.weighty = 1.0;
        gbc_lblNewLabel_6.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_6.gridx = 0;
        gbc_lblNewLabel_6.gridy = 0;
        progressPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
        
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.weighty = 1.0;
        gbc_progressBar.anchor = GridBagConstraints.NORTHEAST;
        gbc_progressBar.insets = new Insets(0, 0, 0, 5);
        gbc_progressBar.gridx = 1;
        gbc_progressBar.gridy = 0;
        progressPanel.add(progressBar, gbc_progressBar);
        
        
       
    }
    
    public JPanel getPanel() { return panel; }

    /** Program launch point
    * @param args Console arguments
    * @author 19076935 */
    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equals("c") || arg.equals("console")) { consoleMain(args); }
        }

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }
        
        Application frame = new Application();
        
        frame.setContentPane(frame.getPanel());
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
        Game game1 = new Game("Half-Life", 30.00f, Platform.PC, new Date(), 5);

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
