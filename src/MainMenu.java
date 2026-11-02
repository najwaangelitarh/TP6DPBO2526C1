import java.awt.*;

import javax.swing.*;

public class MainMenu extends JPanel {
    public MainMenu(JFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(113, 197, 207));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel titleLabel = new JLabel("Flappy Bird Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Tombol Start
        JButton btnStart = new JButton("Start Game");
        btnStart.setFont(new Font("Arial", Font.BOLD, 18));
        btnStart.setPreferredSize(new Dimension(150, 40));
        btnStart.addActionListener(e -> {
            frame.getContentPane().removeAll();
            Logic logic = new Logic();
            View view = new View(logic);
            logic.setView(view);

            frame.add(view);
            frame.revalidate();
            frame.repaint();
            view.requestFocusInWindow();
        });
        gbc.gridy = 1;
        add(btnStart, gbc);

        // Tombol Exit
        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Arial", Font.BOLD, 18));
        btnExit.setPreferredSize(new Dimension(150, 40));
        btnExit.addActionListener(e -> System.exit(0));
        gbc.gridy = 2;
        add(btnExit, gbc);
    }
}