import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

public class GamePanel extends JPanel {
    private Game game;

    private JPanel details = new JPanel();
    private JLabel gameTitle = new JLabel();
    private JLabel gamePrice = new JLabel();
    private JLabel gamePlatform = new JLabel();

    public GamePanel(Game game) throws IOException {
        this.game = game;
        gameTitle.setText(game.getTitle());
        gameTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        gamePrice.setText("Price: $" + game.getPrice());
        gamePlatform.setText("Platform: " + game.getPlatform());

        // TODO: Have this image getter check for original images, plus in a different function & possibly thread
        URL url = new URL((String)game.getAPIResults().get("image"));
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new FileOutputStream(new File("data/img/" + game.getTitle().replace(":", "-") + ".jpg"));

        byte[] bytes = new byte[2048];
        int length;

        while ((length = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }

        inputStream.close();
        outputStream.close();

        BufferedImage image = ImageIO.read(new File("data/img/" + game.getTitle().replace(":", "-") + ".jpg"));
        ImageIcon art = new ImageIcon(image);
        JLabel boxArtLabel = new JLabel(art);

        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        this.add(boxArtLabel);
        details.add(gameTitle);
        details.add(gamePrice);
        details.add(gamePlatform);
        this.add(details);
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
