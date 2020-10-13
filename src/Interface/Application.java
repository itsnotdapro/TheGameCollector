package Interface;

import javax.swing.*;

import Data.Month;
import Exceptions.Log;
import Interface.Listeners.RatingListener;
import Interface.Listeners.SelectionListener;
import Interface.Validation.DayVerifier;
import Interface.Validation.PriceVerifier;
import Interface.Validation.YearVerifier;
import Library.Library;
import Library.Platform;
import Library.SortingMethod;
import Library.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EtchedBorder;

/** Main driver class of the application. Handles creating UI, user input, and console commands
* @author 19076935 */
public class Application extends JFrame {
	private static final long serialVersionUID = 84097850937829L;
	private Library library;
	private ArrayList<GamePanel> gamePanels = new ArrayList<GamePanel>();
	private GamePanel selectedGame = null;
	
	// All of the Swing components, and variables used for GUI control
    private final JPanel panel = new JPanel();
    private JPanel games = new JPanel(), controls = new JPanel();
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
    private final JButton addButton = new JButton("Add");
    private final JLabel lblNewLabel_5 = new JLabel("Rating: ");
    private final JPanel ratingPanel = new JPanel();
    private final JRadioButton rate1 = new JRadioButton("");
    private final JRadioButton rate2 = new JRadioButton("");
    private final JRadioButton rate3 = new JRadioButton("");
    private final JRadioButton rate4 = new JRadioButton("");
    private final JRadioButton rate5 = new JRadioButton("");
    private final JRadioButton rate6 = new JRadioButton("");
    private final JRadioButton rate7 = new JRadioButton("");
    private final JRadioButton rate8 = new JRadioButton("");
    private final JRadioButton rate9 = new JRadioButton("");
    private final JRadioButton rate10 = new JRadioButton("");
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel lblNewLabel_6 = new JLabel("Progress");
    private final JPanel sortingPanel = new JPanel();
    private final JLabel lblNewLabel_7 = new JLabel("Sorting Method: ");
    private final JComboBox<SortingMethod> sortingMethod = new JComboBox<SortingMethod>();
    private final JButton deleteButton = new JButton("Delete");
    private final SelectionListener selectionListener = new SelectionListener(deleteButton);
    private final JPanel collectionPanel = new JPanel();

    public Application() {
        panel.setLayout(new GridLayout());
        
        // Creating the menu bar and actions
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        this.setJMenuBar(menuBar);
        
        // Creating the base UI
        collectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Collection", TitledBorder.CENTER, 0, new Font("Tahoma", Font.BOLD,  30)));
        collectionPanel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), collectionPanel.getBorder()));
        collectionPanel.setLayout(new BoxLayout(collectionPanel, BoxLayout.Y_AXIS));
        GridBagLayout gbl_sortingPanel = new GridBagLayout();
        gbl_sortingPanel.columnWidths = new int[]{77, 363, 0};
        gbl_sortingPanel.rowHeights = new int[] {0, 0};
        gbl_sortingPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_sortingPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        sortingPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        collectionPanel.add(sortingPanel);
        sortingPanel.setLayout(gbl_sortingPanel);
        lblNewLabel_7.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
        gbc_lblNewLabel_7.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_7.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_7.gridx = 0;
        gbc_lblNewLabel_7.gridy = 0;
        sortingPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
        sortingMethod.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		library.sort((SortingMethod)sortingMethod.getSelectedItem());
        		drawGames();
        	}
        });
        
        sortingMethod.setModel(new DefaultComboBoxModel<SortingMethod>(SortingMethod.values()));
        
        
        // God I hate how much code GridBagLayout makes //
        
        GridBagConstraints gbc_sortingMethod = new GridBagConstraints();
        gbc_sortingMethod.weightx = 2.0;
        gbc_sortingMethod.anchor = GridBagConstraints.NORTHWEST;
        gbc_sortingMethod.gridx = 1;
        gbc_sortingMethod.gridy = 0;
        sortingPanel.add(sortingMethod, gbc_sortingMethod);
        games.setLayout(new GridBagLayout());
        
        JScrollPane scrollPane = new JScrollPane(games);
        collectionPanel.add(scrollPane);
        scrollPane.setBorder(null);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		for (GamePanel gp : gamePanels) {
        			gp.setTitleBorder();
                	deleteButton.setEnabled(false);
                	selectedGame = null;
                }
        	}
        });
        deleteButton.setEnabled(false);
        deleteButton.setAlignmentY(0.0f);
        deleteButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        collectionPanel.add(deleteButton);
        panel.add(collectionPanel);
        
        // Initializing the library on startup //
        try {
            library = new Library("data/db.bin");
            library.read();
        } catch (Exception e) { new Log(e.getMessage()); }

        // Writing the library to the UI //
        library.sort(SortingMethod.NAME);
        drawGames();
        
        
        //////////////////////////////////////////////
        // Event handler for the Delete Game button //
        //////////////////////////////////////////////
        deleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (selectionListener.getSelected() != null) {
        			library.remove(selectionListener.getSelected().getGame());
        			gamePanels.remove(selectionListener.getSelected());
        	drawGames();
        	selectedGame = null;
        		}
        	}
        });
        controls.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(controls);
        
        controls.setLayout(new GridLayout(3, 1, 0, 0));
        controls.add(fieldsPanel);
        GridBagLayout gbl_fieldsPanel = new GridBagLayout();
        fieldsPanel.setLayout(gbl_fieldsPanel);
        fieldsPanel.setPreferredSize(new Dimension(400, 200));
        fieldsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Add New Game", TitledBorder.RIGHT, 0, new Font("Tahoma", Font.BOLD, 30)));
        fieldsPanel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), fieldsPanel.getBorder()));
        
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 0;
        fieldsPanel.add(lblNewLabel, gbc_lblNewLabel);
        
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameField.gridx = 2;
        gbc_nameField.gridy = 0;
        fieldsPanel.add(nameField, gbc_nameField);
    	nameField.setColumns(10);
    	nameField.addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyReleased(KeyEvent e) { 
    			e.getComponent().transferFocus();
    			e.getComponent().requestFocus();
    			checkAllFields(); 
    			}
    	});
        
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 1;
        fieldsPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
        
        priceField.setInputVerifier(new PriceVerifier());
        GridBagConstraints gbc_priceField = new GridBagConstraints();
        gbc_priceField.insets = new Insets(0, 0, 5, 0);
        gbc_priceField.fill = GridBagConstraints.HORIZONTAL;
        gbc_priceField.gridx = 2;
        gbc_priceField.gridy = 1;
        fieldsPanel.add(priceField, gbc_priceField);
        priceField.setColumns(10);
        priceField.addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyReleased(KeyEvent e) { 
    			e.getComponent().transferFocus();
    			e.getComponent().requestFocus();
    			checkAllFields(); 
    			}
    	});
        
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 1;
        gbc_lblNewLabel_2.gridy = 2;
        fieldsPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
        
        GridBagConstraints gbc_platformField = new GridBagConstraints();
        gbc_platformField.insets = new Insets(0, 0, 5, 0);
        gbc_platformField.fill = GridBagConstraints.HORIZONTAL;
        gbc_platformField.gridx = 2;
        gbc_platformField.gridy = 2;
        platformField.setModel(new DefaultComboBoxModel<Platform>(Platform.values()));
        fieldsPanel.add(platformField, gbc_platformField);
        
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 1;
        gbc_lblNewLabel_4.gridy = 3;
        fieldsPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
        
        GridBagConstraints gbc_datePanel = new GridBagConstraints();
        gbc_datePanel.anchor = GridBagConstraints.WEST;
        gbc_datePanel.insets = new Insets(0, 0, 5, 0);
        gbc_datePanel.gridx = 2;
        gbc_datePanel.gridy = 3;
        FlowLayout flowLayout = (FlowLayout) datePanel.getLayout();
        fieldsPanel.add(datePanel, gbc_datePanel);
        
        dayField.addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyReleased(KeyEvent e) { 
    			e.getComponent().transferFocus();
    			e.getComponent().requestFocus();
    			checkAllFields(); 
    			}
    	});
        dayField.setColumns(10);
        dayField.setInputVerifier(new DayVerifier(monthField));
        datePanel.add(dayField);
        
        monthField.setModel(new DefaultComboBoxModel<Month>(Month.values()));
        datePanel.add(monthField);
        
        yearField.addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyReleased(KeyEvent e) { 
    			e.getComponent().transferFocus();
    			e.getComponent().requestFocus();
    			checkAllFields(); 
    			}
    	});
        yearField.setColumns(10);
        yearField.setInputVerifier(new YearVerifier());
        datePanel.add(yearField);
        
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_5.gridx = 1;
        gbc_lblNewLabel_5.gridy = 4;
        fieldsPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
        
        GridBagConstraints gbc_ratingPanel = new GridBagConstraints();
        gbc_ratingPanel.anchor = GridBagConstraints.WEST;
        gbc_ratingPanel.insets = new Insets(0, 0, 5, 0);
        gbc_ratingPanel.fill = GridBagConstraints.VERTICAL;
        gbc_ratingPanel.gridx = 2;
        gbc_ratingPanel.gridy = 4;
        fieldsPanel.add(ratingPanel, gbc_ratingPanel);
        RatingListener ratingListener = new RatingListener(rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9, rate10);
        rate1.setSelected(true);
        rate1.addActionListener(ratingListener);
        rate2.addActionListener(ratingListener);
        rate3.addActionListener(ratingListener);
        rate4.addActionListener(ratingListener);
        rate5.addActionListener(ratingListener);
        rate6.addActionListener(ratingListener);
        rate7.addActionListener(ratingListener);
        rate8.addActionListener(ratingListener);
        rate9.addActionListener(ratingListener);
        rate10.addActionListener(ratingListener); 
        
        ratingPanel.add(rate1);        
        ratingPanel.add(rate2);        
        ratingPanel.add(rate3);        
        ratingPanel.add(rate4);        
        ratingPanel.add(rate5);       
        ratingPanel.add(rate6);        
        ratingPanel.add(rate7);       
        ratingPanel.add(rate8);        
        ratingPanel.add(rate9);        
        ratingPanel.add(rate10);        
        
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 1;
        gbc_lblNewLabel_3.gridy = 5;
        fieldsPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
        
        GridBagConstraints gbc_physicalField = new GridBagConstraints();
        gbc_physicalField.insets = new Insets(0, 0, 5, 0);
        gbc_physicalField.anchor = GridBagConstraints.WEST;
        gbc_physicalField.gridx = 2;
        gbc_physicalField.gridy = 5;
        fieldsPanel.add(physicalField, gbc_physicalField);
        GridBagConstraints gbc_addButton = new GridBagConstraints();
        gbc_addButton.insets = new Insets(0, 0, 5, 0);
        gbc_addButton.anchor = GridBagConstraints.EAST;
        gbc_addButton.gridx = 2;
        gbc_addButton.gridy = 6;
        addButton.setEnabled(false);
        fieldsPanel.add(addButton, gbc_addButton);
        
        ///////////////////////////////////////////
        // Event handler for the Add Game button //
        ///////////////////////////////////////////
        addButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// Create new constraint for the GamePanel
        		GridBagConstraints gbc = new GridBagConstraints();
        		gbc.gridx = 0;
        		gbc.gridy = library.size();
        		
        		// Create the new game
        		Calendar cal = Calendar.getInstance();
        		cal.set(Integer.parseInt(yearField.getText()), ((Month)monthField.getSelectedItem()).getValue(), Integer.parseInt(dayField.getText()));
        		Game addition = new Game(nameField.getText(), 
        								 Float.parseFloat(priceField.getText()),
        								 (Platform)platformField.getSelectedItem(),
        								 cal.getTime(), ratingListener.getValue(), physicalField.isSelected());
        		
        		// Get the game's information on a new thread, to update the progress bar, and because it takes a long ass time
        		new Thread(new Runnable() {
        			@Override
        			public void run() { 
        				addition.getData(progressBar); // This method is the dumb long one, cause API call goes brrrr 
        				games.add(new GamePanel(addition, gamePanels, selectionListener), gbc);
                		library.add(addition);
                		drawGames();
                		try { library.write(); }
                		catch (IOException e) { new Log(e.getMessage()); }
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
    
    public void checkAllFields() {
    	if (nameField.getText().length() > 0 && 
    	    priceField.getInputVerifier().verify(priceField) && priceField.getText().length() > 0 && 
    	    dayField.getInputVerifier().verify(dayField) && dayField.getText().length() > 0 &&
    	    yearField.getInputVerifier().verify(yearField) && yearField.getText().length() > 0) {
    		addButton.setEnabled(true);
    	} else {
    		addButton.setEnabled(false);
    	}
    }
    
    public void drawGames() {
    	games.removeAll();
    	gamePanels.clear();
    	int i = 0;
        for (Game game : library) {
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.gridx = 0;
            constraint.gridy = i;
            GamePanel gp = new GamePanel(game, gamePanels, selectionListener);
            gamePanels.add(gp);
            games.add(gp, constraint);
            ++i;
        }
        
        games.revalidate();
        games.repaint();
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
        Game game1 = new Game("Half-Life", 30.00f, Platform.PC, new Date(), 5, true);

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
