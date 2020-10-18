package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import Exceptions.Log;
import Interface.Listeners.SelectionListener;
import Library.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

/** An object that represents a game drawn to the UI
 * @author 19076935 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private final Game game;
    /** @return The game the panel is displaying @author 19076935 */
    public Game getGame() { return game; }
    
    private final JPanel details = new JPanel();
    private final JLabel gameTitle = new JLabel();
    private final JLabel gamePrice = new JLabel();
    private final JLabel gamePlatform = new JLabel();
    private final JLabel gameRating = new JLabel();
    private final JLabel gamePurchase = new JLabel("Release Date:");

    /** Instantiates the JPanel that represents a game
     * @param game The game being drawn
     * @param listener The selection listener that handles selection of the game
     * @author 19076935 */
    public GamePanel(Game game, SelectionListener listener) {
        this.game = game;
        addMouseListener(listener);
        
        setTitleBorder();

        gameTitle.setText(game.getTitle());
        gameTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        gamePrice.setText("Price: $" + game.getPriceAsString());
        gamePlatform.setText("Platform: " + game.getPlatform());
        gameRating.setText("Rating: " + game.getRating() + "/10");
        gamePurchase.setText("Purchase Date: " + Game.getDateAsString(game.getPurchase(), "d MMMM, yyyy"));
        
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.add(gameTitle);
        details.add(gamePrice);
        details.add(gamePlatform);
        details.add(gameRating);
        details.add(gamePurchase);
        
        if (!game.getResults().isEmpty()) {
        	BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            try { image = getImage(); }
            catch (IOException e) { new Log(e.getMessage()); }
            
            
            ImageIcon art = new ImageIcon(image);
            JLabel boxArtLabel = new JLabel(art);
            this.add(boxArtLabel);
            
        	details.add(new JLabel(" "));
        	details.add(new JLabel("Release Date: " + Game.getDateAsString(game.getRelease(), "d MMMM, yyyy")));
        	String genreText = "Genres: ";
        	int i = 0;
        	for (String genre : game.getGenres()) {
        		genreText += genre + (i == game.getGenres().size() - 1 ? "" : ", ");
        		++i;
        	}
        	JLabel genres = new JLabel(genreText);
        	details.add(new JLabel("Developed by: " + game.getResult("developers")));
        	details.add(genres);
        	details.add(new JLabel("Metacritic Score: " + game.getResult("score")));        	
        }
        
        this.add(details); 
        
    }
    
    /** Sets the border when the panel is not selected
     * @author 19076935 */
    public void setTitleBorder() { 
    	// For some reason you have to clear the border before you make a new one with compound borders
    	setBorder(BorderFactory.createEmptyBorder());
	    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
	    		                                  game.getPhysical() ? "Physical" : "Digital", 
	    		                                  game.getPhysical() ? TitledBorder.RIGHT : TitledBorder.LEFT, 0));
    }
    
    /** Sets the border when the panel is selected
     * @author 19076935 */
    public void setSelectedBorder() {
    	setBorder(BorderFactory.createCompoundBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, 2, 2, 2, false), 
    												 BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
    														 						  game.getPhysical() ? "Physical" : "Digital", 
    														 					      game.getPhysical() ? TitledBorder.RIGHT : TitledBorder.LEFT, 0)));
    }

    /** Gets the image associated with the game
     * @return Image associated with the game as found in data/img/gameTitle.jpg 
     * @throws IOException If the FileIO fails for any reason
     * @author 19076935 */
    public BufferedImage getImage() throws IOException {
    	if (game.getResults().isEmpty()) { return ImageIO.read(new File("data/img/placeholder.jpg")); }
        File imageFile = new File("data/img/" + game.getTitle().replace(":", "") + "-" + game.getPlatform() + ".jpg");
        // Get the image from a HTTP request if it does not exist
        if (!imageFile.exists()) {
            URL url = new URL((String)game.getResult("image"));
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new FileOutputStream(new File("data/img/" + game.getTitle().replace(":", "") + "-" + game.getPlatform() + ".jpg"));

            byte[] bytes = new byte[2048];
            int length;

            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }

            inputStream.close();
            outputStream.close();
        }

        return ImageIO.read(imageFile);
    }

    /** Method to resize a BufferedImage
     * @param src The source image to be resized
     * @param w The target width
     * @param h The target height
     * @return The resized image
     * @author 19076935 */
    public static BufferedImage resize(BufferedImage src, int w, int h) {
        Image scaled = src.getScaledInstance(w, h, Image.SCALE_DEFAULT);
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        output.getGraphics().drawImage(scaled, 0, 0, null);
        return output;
    }
}
