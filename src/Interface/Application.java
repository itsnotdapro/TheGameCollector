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
	public ArrayList<GamePanel> getGamePanels() { return gamePanels; }
	private GamePanel selectedGame = null;
	
	// All of the Swing components, and variables used for GUI control
	// Dear god thats an ass load of components
    private final JPanel panel = new JPanel();
    /** @return The master JPanel @author 19076935 */ 
    public JPanel getPanel() { return panel; }
    private final JPanel games = new JPanel();
    private final JPanel controls = new JPanel();
    private final JPanel fieldsPanel = new JPanel();
    /** @return The entry field's JPanel @author 19076935 */
    public JPanel getFieldsPanel() { return fieldsPanel; }
    private final JLabel nameLabel = new JLabel("Name: ");
    private final JTextField nameField = new JTextField();
    /** @return The name entry JTextField @author 19076935 */
    public JTextField getNameField() { return nameField; }
    private final JLabel priceLabel = new JLabel("Price: ");
    private final JTextField priceField = new JTextField();
    /** @return The price entry JTextField @author 19076935 */
    public JTextField getPriceField() { return priceField; }
    private final JLabel platformLabel = new JLabel("Platform: ");
    private final JComboBox<Platform> platformField = new JComboBox<Platform>();
    /** @return The platform entry JComboBox @author 19076935 */
    public JComboBox<Platform> getPlatformField() { return platformField; }
    private final JLabel physicalLabel = new JLabel("Physical: ");
    private final JCheckBox physicalField = new JCheckBox("");
    /** @return The physical or digital entry JCheckBox @author 19076935 */
    public JCheckBox getPhysicalField() { return physicalField; }
    private final JLabel pruchaseLabel = new JLabel("Date Purchased: ");
    private final JPanel datePanel = new JPanel();
    private final JComboBox<Month> monthField = new JComboBox<Month>();
    /** @return The month entry JComboBox @author 19076935 */
    public JComboBox<Month> getMonthField() { return monthField; }
    private final JTextField dayField = new JTextField();
    /** @return The day entry JTextField @author 19076935 */
    public JTextField getDayField() { return dayField; }
    private final JTextField yearField = new JTextField();
    /** @return The year entry JTextField @author 19076935 */
    public JTextField getYearField() { return yearField; }
    private final JPanel progressPanel = new JPanel();
    private final JButton addButton = new JButton("Add");
    /** @return The add JButton @author 19076935 */
    public JButton getAddButton() { return addButton; }
    private final JLabel ratingLabel = new JLabel("Rating: ");
    private final JPanel ratingPanel = new JPanel();
    private final JRadioButton[] ratingButtons = new JRadioButton[10];
    private final RatingListener ratingListener;
    /** @return The rating listener of the application @author 19076935 */
    public RatingListener getRatingListener() { return ratingListener; }
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel progressLabel = new JLabel("Progress");
    private final JPanel sortingPanel = new JPanel();
    private final JLabel sortLabel = new JLabel("Sorting Method: ");
    private final JComboBox<SortingMethod> sortingMethod = new JComboBox<SortingMethod>();
    private final JButton deleteButton = new JButton("Delete");
    /** @return The delete JButton @author 19076935 */
    public JButton getDeleteButton() { return deleteButton; }
    private final SelectionListener selectionListener = new SelectionListener(this);
    private final JPanel collectionPanel = new JPanel();
    private final JPanel programTitleLabel = new JPanel();
    private final JLabel filterLabel = new JLabel("Search: ");
    private final JTextField filterField = new JTextField();
    private final JLabel itemsCulledLabel = new JLabel("");

    /** Constructor of the application object. This instantiates the main runtime thread 
     * @author 19076935    */
    public Application() {
    	filterField.setColumns(10);
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
        GridBagLayout gbl_collectionPanel = new GridBagLayout();
        gbl_collectionPanel.columnWidths = new int[]{430, 0};
        gbl_collectionPanel.rowHeights = new int[]{59, 571, 21, 0};
        gbl_collectionPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_collectionPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        collectionPanel.setLayout(gbl_collectionPanel);
        GridBagLayout gbl_sortingPanel = new GridBagLayout();
        gbl_sortingPanel.columnWidths = new int[] {0, 0, 0};
        gbl_sortingPanel.rowHeights = new int[] {0, 0, 0, 0};
        gbl_sortingPanel.columnWeights = new double[]{0.0, 1.0, 0.0};
        gbl_sortingPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        sortingPanel.setAlignmentY(0.0f);
        sortingPanel.setAlignmentX(0.0f);
        GridBagConstraints gbc_sortingPanel = new GridBagConstraints();
        gbc_sortingPanel.anchor = GridBagConstraints.WEST;
        gbc_sortingPanel.insets = new Insets(0, 0, 5, 0);
        gbc_sortingPanel.gridx = 0;
        gbc_sortingPanel.gridy = 0;
        collectionPanel.add(sortingPanel, gbc_sortingPanel);
        sortingPanel.setLayout(gbl_sortingPanel);
        sortLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        sortLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_sortLabel = new GridBagConstraints();
        gbc_sortLabel.anchor = GridBagConstraints.WEST;
        gbc_sortLabel.insets = new Insets(0, 0, 5, 5);
        gbc_sortLabel.gridx = 0;
        gbc_sortLabel.gridy = 0;
        sortingPanel.add(sortLabel, gbc_sortLabel);
        sortingMethod.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SortingMethod method = (SortingMethod)sortingMethod.getSelectedItem();
        		if (method.requiresData()) {
        			Library fLib = library.filter(true);
        			itemsCulledLabel.setText("  Games missing data have been hidden");
        			fLib.sort(method);
        			drawGames(fLib);
        		} else {
        			library.sort(method);
        			itemsCulledLabel.setText("");
            		drawGames(library);
        		}
        		
        	}
        });
        
        sortingMethod.setModel(new DefaultComboBoxModel<SortingMethod>(SortingMethod.values()));
                
        // God I hate how much code GridBagLayout makes //
        
        GridBagConstraints gbc_sortingMethod = new GridBagConstraints();
        gbc_sortingMethod.insets = new Insets(0, 0, 5, 0);
        gbc_sortingMethod.weightx = 2.0;
        gbc_sortingMethod.anchor = GridBagConstraints.NORTHWEST;
        gbc_sortingMethod.gridx = 1;
        gbc_sortingMethod.gridy = 0;
        sortingPanel.add(sortingMethod, gbc_sortingMethod);
        
        GridBagConstraints gbc_filterLabel = new GridBagConstraints();
        gbc_filterLabel.anchor = GridBagConstraints.EAST;
        gbc_filterLabel.insets = new Insets(0, 0, 5, 5);
        gbc_filterLabel.gridx = 0;
        gbc_filterLabel.gridy = 1;
        sortingPanel.add(filterLabel, gbc_filterLabel);
        
        GridBagConstraints gbc_filterField = new GridBagConstraints();
        gbc_filterField.insets = new Insets(0, 0, 5, 0);
        gbc_filterField.fill = GridBagConstraints.HORIZONTAL;
        gbc_filterField.gridx = 1;
        gbc_filterField.gridy = 1;
        sortingPanel.add(filterField, gbc_filterField);
        
        GridBagConstraints gbc_itemsCulledLabel = new GridBagConstraints();
        gbc_itemsCulledLabel.gridheight = 2;
        gbc_itemsCulledLabel.anchor = GridBagConstraints.EAST;
        gbc_itemsCulledLabel.insets = new Insets(0, 0, 0, 5);
        gbc_itemsCulledLabel.gridx = 2;
        gbc_itemsCulledLabel.gridy = 0;
        itemsCulledLabel.setForeground(Color.GRAY);
        sortingPanel.add(itemsCulledLabel, gbc_itemsCulledLabel);
        filterField.addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyReleased(KeyEvent e) { 
    			String text = ((JTextField)(e.getComponent())).getText();
    			if (!text.equals("")) { drawGames(library.filter(text));
    			} else { drawGames(library); }
    		}
    	});
        
        games.setLayout(new GridBagLayout());        
        JScrollPane scrollPane = new JScrollPane(games);
        scrollPane.setAlignmentX(0.0f);
        scrollPane.setAlignmentY(0.0f);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.weighty = 1.0;
        gbc_scrollPane.weightx = 1.0;
        gbc_scrollPane.anchor = GridBagConstraints.NORTHWEST;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        collectionPanel.add(scrollPane, gbc_scrollPane);
        scrollPane.setBorder(null);
        scrollPane.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		selectionListener.deselect();
        	}
        });
        deleteButton.setEnabled(false);
        deleteButton.setAlignmentY(0.0f);
        deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        GridBagConstraints gbc_deleteButton = new GridBagConstraints();
        gbc_deleteButton.ipady = 1;
        gbc_deleteButton.anchor = GridBagConstraints.EAST;
        gbc_deleteButton.gridx = 0;
        gbc_deleteButton.gridy = 2;
        collectionPanel.add(deleteButton, gbc_deleteButton);
        
        
        //////////////////////////////////////////////
        // Event handler for the Delete Game button //
        //////////////////////////////////////////////
        deleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (selectionListener.getSelectedPanel() != null) {
        			library.remove(selectionListener.getSelectedPanel().getGame());
        			gamePanels.remove(selectionListener.getSelectedPanel());
        			drawGames(library);
        			selectedGame = null;
        		}
        	}
        });
        panel.add(collectionPanel);
        
        // Initializing the library on startup //
        try {
            library = new Library("data/db.bin");
            library.read();
        } catch (Exception e) { new Log(e.getMessage()); }

        // Writing the library to the UI //
        library.sort(SortingMethod.NAME);
        System.out.println(library);
        drawGames(library);
        
        controls.setBorder(new EmptyBorder(20, 20, 20, 20));
        controls.setLayout(new GridLayout(3, 1, 0, 0));
        panel.add(controls);
        
        controls.add(programTitleLabel);
        controls.add(fieldsPanel);
        GridBagLayout gbl_fieldsPanel = new GridBagLayout();
        fieldsPanel.setLayout(gbl_fieldsPanel);
        fieldsPanel.setPreferredSize(new Dimension(400, 200));
        fieldsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Add Game", TitledBorder.RIGHT, 0, new Font("Tahoma", Font.BOLD, 30)));
        fieldsPanel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), fieldsPanel.getBorder()));
        
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.anchor = GridBagConstraints.NORTHEAST;
        gbc_nameLabel.gridx = 1;
        gbc_nameLabel.gridy = 0;
        fieldsPanel.add(nameLabel, gbc_nameLabel);
        
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
        
        GridBagConstraints gbc_priceLabel = new GridBagConstraints();
        gbc_priceLabel.anchor = GridBagConstraints.EAST;
        gbc_priceLabel.insets = new Insets(0, 0, 5, 5);
        gbc_priceLabel.gridx = 1;
        gbc_priceLabel.gridy = 1;
        fieldsPanel.add(priceLabel, gbc_priceLabel);
        
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
        
        GridBagConstraints gbc_platformLabel = new GridBagConstraints();
        gbc_platformLabel.anchor = GridBagConstraints.EAST;
        gbc_platformLabel.insets = new Insets(0, 0, 5, 5);
        gbc_platformLabel.gridx = 1;
        gbc_platformLabel.gridy = 2;
        fieldsPanel.add(platformLabel, gbc_platformLabel);
        
        GridBagConstraints gbc_platformField = new GridBagConstraints();
        gbc_platformField.insets = new Insets(0, 0, 5, 0);
        gbc_platformField.fill = GridBagConstraints.HORIZONTAL;
        gbc_platformField.gridx = 2;
        gbc_platformField.gridy = 2;
        platformField.setModel(new DefaultComboBoxModel<Platform>(Platform.values()));
        fieldsPanel.add(platformField, gbc_platformField);
        
        GridBagConstraints gbc_pruchaseLabel = new GridBagConstraints();
        gbc_pruchaseLabel.anchor = GridBagConstraints.EAST;
        gbc_pruchaseLabel.insets = new Insets(0, 0, 5, 5);
        gbc_pruchaseLabel.gridx = 1;
        gbc_pruchaseLabel.gridy = 3;
        fieldsPanel.add(pruchaseLabel, gbc_pruchaseLabel);
        
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
        
        GridBagConstraints gbc_ratingLabel = new GridBagConstraints();
        gbc_ratingLabel.anchor = GridBagConstraints.EAST;
        gbc_ratingLabel.insets = new Insets(0, 0, 5, 5);
        gbc_ratingLabel.gridx = 1;
        gbc_ratingLabel.gridy = 4;
        fieldsPanel.add(ratingLabel, gbc_ratingLabel);
        
        GridBagConstraints gbc_ratingPanel = new GridBagConstraints();
        gbc_ratingPanel.anchor = GridBagConstraints.WEST;
        gbc_ratingPanel.insets = new Insets(0, 0, 5, 0);
        gbc_ratingPanel.fill = GridBagConstraints.VERTICAL;
        gbc_ratingPanel.gridx = 2;
        gbc_ratingPanel.gridy = 4;
        fieldsPanel.add(ratingPanel, gbc_ratingPanel);
        
        ratingListener = new RatingListener(ratingButtons);
        for (int i = 0; i < 10; i++) {
        	ratingButtons[i] = new JRadioButton("");
        	ratingButtons[i].addActionListener(ratingListener);
        	ratingPanel.add(ratingButtons[i]);
        	
        }
        
        ratingButtons[0].setSelected(true);
        
        GridBagConstraints gbc_physicalLabel = new GridBagConstraints();
        gbc_physicalLabel.anchor = GridBagConstraints.EAST;
        gbc_physicalLabel.insets = new Insets(0, 0, 5, 5);
        gbc_physicalLabel.gridx = 1;
        gbc_physicalLabel.gridy = 5;
        fieldsPanel.add(physicalLabel, gbc_physicalLabel);
        
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
        				// If there is no game selected, add a new game
        				if (selectionListener.getSelectedPanel() == null) { 
        					addition.getData(progressBar); // This method is the dumb long one, cause API call goes brrrr 
                    		library.add(addition);
                    	// Update the selected game instead of adding one
        				} else { 
        					Game selectedGame = selectionListener.getSelectedPanel().getGame();
        					// Skip the API call if it's not needed 
        					if (selectedGame.getTitle().equals(addition.getTitle()) && selectedGame.getPlatform() == addition.getPlatform()) { 
        						addition.setResults(selectedGame.getResults());
        					} else { addition.getData(progressBar); }
        					library.update(selectedGame, addition);
        					selectionListener.deselect();
        				}
        				
                		library.sort((SortingMethod)sortingMethod.getSelectedItem());
                		drawGames(library);
        			}
        		}).start();
        	}
        });
        progressPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        controls.add(progressPanel);
        GridBagLayout gbl_progressPanel = new GridBagLayout();
        progressPanel.setLayout(gbl_progressPanel);
        
        GridBagConstraints gbc_progressLabel = new GridBagConstraints();
        gbc_progressLabel.weightx = 1.0;
        gbc_progressLabel.weighty = 1.0;
        gbc_progressLabel.anchor = GridBagConstraints.NORTHEAST;
        gbc_progressLabel.insets = new Insets(0, 0, 0, 5);
        gbc_progressLabel.gridx = 0;
        gbc_progressLabel.gridy = 0;
        progressPanel.add(progressLabel, gbc_progressLabel);
        
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.weighty = 1.0;
        gbc_progressBar.anchor = GridBagConstraints.NORTHEAST;
        gbc_progressBar.insets = new Insets(0, 0, 0, 5);
        gbc_progressBar.gridx = 1;
        gbc_progressBar.gridy = 0;
        progressPanel.add(progressBar, gbc_progressBar);
    }
    
    /** Method that checks all the input fields and enables the add button if they have all been validated
     * @author 19076935 */
    public void checkAllFields() {
    	// These check if the field is greater than zero as the input verifiers treat "" as a valid string, so that
    	// The user can tab out of empty fields
    	if (nameField.getText().length() > 0 && 
    	    priceField.getInputVerifier().verify(priceField) && !priceField.getText().equals("") && 
    	    dayField.getInputVerifier().verify(dayField) && !dayField.getText().equals("") &&
    	    yearField.getInputVerifier().verify(yearField) && !yearField.getText().equals("")) {
    		addButton.setEnabled(true);
    	} else {
    		addButton.setEnabled(false);
    	}
    }
    
    /** Draws a given library to the screen
     * @param library The library to draw to the screen
     * @author 19076935 */
    public void drawGames(Library library) {
    	games.removeAll();
    	gamePanels.clear();
    	int i = 0;
        for (Game game : library) {
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.gridx = 0;
            constraint.gridy = i;
           // constraint.anchor = GridBagConstraints.WEST;
            GamePanel gp = new GamePanel(game, selectionListener);
            gamePanels.add(gp);
            games.add(gp, constraint);
            ++i;
        }
        
        games.revalidate();
        games.repaint();
    }

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
