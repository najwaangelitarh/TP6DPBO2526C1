import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    int pipeVelocityX = -4;

    View view;
    Image birdImage;
    Player player;
    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    // Spesifikasi Wajib: Status Game dan Skor
    private boolean gameOver = false;
    private int score = 0;

    public Logic() {
        birdImage = new ImageIcon(getClass().getResource("/assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("/assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("/assets/upperPipe.png")).getImage();
        pipes = new ArrayList<>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }

    public void placePipes() {
        if (gameOver) return;

        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void move() {
        if (gameOver) return;

        // Gerak Player
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // Cek Jatuh ke Batas Bawah JFrame
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            triggerGameOver();
        }

        // Gerak Pipa & Deteksi Tabrakan / Skor
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // Hitung Skor saat Melewati Pipa Paling Atas
            if (!pipe.isPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.setPassed(true);
                if (pipes.indexOf(pipe) % 2 == 0) { // Tambah skor 1 kali per pasang pipa
                    score++;
                    if (view != null) {
                        view.updateScore(score);
                    }
                }
            }

            // Deteksi Tabrakan (AABB Collision)
            if (checkCollision(player, pipe)) {
                triggerGameOver();
            }
        }
    }

    private boolean checkCollision(Player p, Pipe pipe) {
        return p.getPosX() < pipe.getPosX() + pipe.getWidth() &&
               p.getPosX() + p.getWidth() > pipe.getPosX() &&
               p.getPosY() < pipe.getPosY() + pipe.getHeight() &&
               p.getPosY() + p.getHeight() > pipe.getPosY();
    }

    private void triggerGameOver() {
        gameOver = true;
        gameLoop.stop();
        pipesCooldown.stop();
    }

    public void restartGame() {
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();
        score = 0;
        gameOver = false;
        if (view != null) {
            view.updateScore(score);
        }
        gameLoop.start();
        pipesCooldown.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            player.setVelocityY(-9);
        }
        // Spesifikasi Wajib: Tombol R untuk Restart
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}