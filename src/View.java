import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class View extends JPanel {
    private Logic logic;
    private JLabel scoreLabel;

    public View(Logic logic) {
        this.logic = logic;
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(logic);

        // Spesifikasi Wajib: JLabel untuk Menampilkan Skor
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel, BorderLayout.NORTH);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Background
        g.setColor(new Color(113, 197, 207));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw Player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                        player.getWidth(), player.getHeight(), null);
        }

        // Draw Pipes
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                            pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // Tampilan Game Over
        if (logic.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("GAME OVER", 70, 300);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Tekan 'R' untuk Restart", 75, 340);
        }
    }
}