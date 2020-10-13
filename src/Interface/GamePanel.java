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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final Game game;
    public Game getGame() { return game; }
    
    private final JPanel details = new JPanel();
    private final JLabel gameTitle = new JLabel();
    private final JLabel gamePrice = new JLabel();
    private final JLabel gamePlatform = new JLabel();
    private final JLabel gameRating = new JLabel();
    private final JLabel gamePurchase = new JLabel("Release Date:");


    public GamePanel(Game game, ArrayList<GamePanel> allPanels, SelectionListener listener) {
        this.game = game;
        addMouseListener(listener);
        
        setTitleBorder();
        
        gameTitle.setText(game.getTitle());
        gameTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        gamePrice.setText("Price: $" + game.getPrice());
        gamePlatform.setText("Platform: " + game.getPlatform());
        gameRating.setText("Rating: " + game.getRating() + "/10");
        gamePurchase.setText("Purchase Date: " + new SimpleDateFormat("dd MMMM, yyyy").format(game.getPurchase()));
        
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.add(gameTitle);
        details.add(gamePrice);
        details.add(gamePlatform);
        details.add(gameRating);
        details.add(gamePurchase);
        
        if (game.hasData()) {
        	BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            try { image = getImage(); }
            catch (IOException e) { new Log(e.getMessage()); }
            ImageIcon art = new ImageIcon(image);
            JLabel boxArtLabel = new JLabel(art);
            this.add(boxArtLabel);
            
        	details.add(new JLabel(" "));
        	JLabel release = new JLabel("Release Date: " + new SimpleDateFormat("dd MMMM, yyyy").format(game.getRelease()));
        	String genreText = "Genres: ";
        	int i = 0;
        	for (String genre : game.getGenres()) {
        		genreText += genre + (i == game.getGenres().size() - 1 ? "" : ", ");
        		++i;
        	}
        	JLabel genres = new JLabel(genreText);
        	
        	details.add(release);
        	details.add(genres);
        }
        
        this.add(details);
    }
    
    public void setTitleBorder() { 
    	// For some reason you have to clear the border before you make a new one with compound borders
    	setBorder(BorderFactory.createEmptyBorder());
	    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
	    		                                  game.getPhysical() ? "Physical" : "Digital", 
	    		                                  game.getPhysical() ? TitledBorder.RIGHT : TitledBorder.LEFT, 0));
    	}
    public void setSelectedBorder() {
    	setBorder(BorderFactory.createCompoundBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, 2, 2, 2, false), 
    												 BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
    														 						  game.getPhysical() ? "Physical" : "Digital", 
    														 					      game.getPhysical() ? TitledBorder.RIGHT : TitledBorder.LEFT, 0)));
    }

    public BufferedImage getImage() throws IOException {
    	if (!game.hasData()) { return ImageIO.read(new File("data/img/placeholder.jpg")); }
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
