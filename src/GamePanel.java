import javax.swing.*;

public class GamePanel extends JPanel {
    private Game game;

    private JLabel gameTitle = new JLabel();
    private JLabel gamePrice = new JLabel();
    private JLabel gamePlatform = new JLabel();

    public GamePanel(Game game) {
        this.game = game;
        gameTitle.setText(game.getTitle());
        gamePrice.setText("" + game.getPrice());
        gamePlatform.setText("" + game.getPlatform());

        this.add(gameTitle);
        this.add(gamePrice);
        this.add(gamePlatform);
    }
}
