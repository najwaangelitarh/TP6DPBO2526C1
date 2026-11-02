import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tampilkan Main Menu terlebih dahulu
        MainMenu mainMenu = new MainMenu(frame);
        frame.add(mainMenu);

        frame.setVisible(true);
    }
}