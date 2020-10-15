package Interface;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;

import Data.Config;
import Data.Month;
import Exceptions.Log;
import Library.Platform;
import Library.SortingMethod;

/** Holds the current application state of the Swing UI, to be stored across sessions
 * @author 19076935 */
public class State implements Config {
	private final String path = "data/.conf";
	
	private SortingMethod defaultMethod = SortingMethod.NAME;
	public SortingMethod getSortingMethod() { return defaultMethod; }
	public void setSortingMethod(SortingMethod defaultMethod) { this.defaultMethod = defaultMethod; } 
	private String nameFieldEntry = "";
	private String priceFieldEntry = "";
	private String dayFieldEntry = "";
	private String yearFieldEntry = "";
	
	private Platform platformEntry = Platform.PC;
	private Month monthEntry = Month.JANUARY;
	
	private int ratingEntry = 1;
	private boolean physicalEntry = false;
	private boolean addButton = false;
	
	/** Instantiates a state object from the .conf file in /data
	 * @author 19076935 */
	public State() {
		try { read(); }
		catch(IOException e) {}
	}
	
	/** Instantiates a state object from given parameters
	 * @param defaultMethod The sorting method currently selected
	 * @param nameFieldEntry The text inside the nameField
	 * @param priceFieldEntry The text inside the priceField
	 * @param dayFieldEntry The text inside the dayField
	 * @param yearFieldEntry The text inside the yearField
	 * @param platformEntry The selected platform
	 * @param monthEntry The selected month
	 * @param ratingEntry The currently selected rating value
	 * @param physicalEntry The currently selected state of the physicalField
	 * @param addButton The enabled/disabled state of the add button
	 * @author 19076935 */
	public State(SortingMethod defaultMethod, String nameFieldEntry, String priceFieldEntry, String dayFieldEntry, String yearFieldEntry,
				 Platform platformEntry, Month monthEntry, int ratingEntry, boolean physicalEntry, boolean addButton) 
	{ 
		this.defaultMethod = defaultMethod;
		this.nameFieldEntry = nameFieldEntry;
		this.priceFieldEntry = priceFieldEntry;
		this.dayFieldEntry = dayFieldEntry;
		this.yearFieldEntry = yearFieldEntry;
		
		this.platformEntry = platformEntry;
		this.monthEntry = monthEntry;
		
		this.ratingEntry = ratingEntry;
		this.physicalEntry = physicalEntry;
		
		this.addButton = addButton;
	}
	
	/** Loads the application state stored in the object
	 * @param instance The application instance to load the state into
	 * @author 19076935 */
	public void load(Application instance) {
		instance.getSortingMethodBox().setSelectedItem(defaultMethod);
		
		instance.getNameField().setText(nameFieldEntry);
		instance.getPriceField().setText(priceFieldEntry);
		instance.getDayField().setText(dayFieldEntry);
		instance.getYearField().setText(yearFieldEntry);
		
		instance.getPlatformField().setSelectedItem(platformEntry);
		instance.getMonthField().setSelectedItem(monthEntry);
		
		instance.getRatingListener().setValue(ratingEntry);
		instance.getPhysicalField().setSelected(physicalEntry);
		
		instance.getAddButton().setEnabled(addButton);
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
            State applicationState = (State)objIn.readObject();
            
            this.defaultMethod = applicationState.defaultMethod;
    		this.nameFieldEntry = applicationState.nameFieldEntry;
    		this.priceFieldEntry = applicationState.priceFieldEntry;
    		this.dayFieldEntry = applicationState.dayFieldEntry;
    		this.yearFieldEntry = applicationState.yearFieldEntry;
    		
    		this.platformEntry = applicationState.platformEntry;
    		this.monthEntry = applicationState.monthEntry;
    		
    		this.ratingEntry = applicationState.ratingEntry;
    		this.physicalEntry = applicationState.physicalEntry;
    		this.addButton = applicationState.addButton;
            
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

}
