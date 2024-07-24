import GameMode1.Main.Main;
import GameMode2.Main.Main1;
import GameMode3.Tetrist2; // Import Tetrist2 từ GameMode3
import GameMode4.Tetrist3; // Import Tetrist3 từ GameMode4
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class GameIntroGUI extends JFrame implements ActionListener {

    private JButton mode1And2Button;
    private JButton mode3Button;
    private JButton mode4Button; // Thêm biến cho nút mới
    private JButton instructionsButton;
    private JButton exitButton;

    public GameIntroGUI() {
        setTitle("Game Introduction");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK); // Set background color to black

        // Create a JPanel to hold the content
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK); // Set background color to black

        // Display the game title
        JLabel titleLabel = new JLabel("Welcome to Tetris Game");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a JPanel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK); // Set background color to black

        // Create buttons for different game modes
        mode1And2Button = createButton("Game Mode 1 & 2");
        mode3Button = createButton("Mutiplayer");
        mode4Button = createButton("Game Mode 3"); // Khởi tạo nút mới
        instructionsButton = createButton("Instructions");
        exitButton = createButton("Exit");

        // Add action listeners to the buttons
        mode1And2Button.addActionListener(this);
        mode3Button.addActionListener(this);
        mode4Button.addActionListener(this); // Thêm action listener cho nút mới
        instructionsButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Center align buttons and add to the button panel with spacing
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(mode1And2Button);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(mode3Button);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(mode4Button); // Thêm nút mới vào button panel
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(instructionsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        // Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add the panel to the JFrame
        add(panel);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new RoundedButton(text);
        button.setMaximumSize(new Dimension(getWidth() / 2, 70)); // Set button width to 50% of frame width and height to 70
        button.setPreferredSize(new Dimension(getWidth() / 2, 70)); // Set preferred size
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }

    // Custom button class for rounded corners
    private class RoundedButton extends JButton {
        private int radius = 15;

        public RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        public void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            int w = getWidth();
            int h = getHeight();
            Shape shape = new RoundRectangle2D.Float(0, 0, w, h, radius, radius);
            return shape.contains(x, y);
        }
    }

    // Handle the event when any button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mode1And2Button) {
            // Show options for Game Mode 1 and 2
            showMode1And2Options();
        } else if (e.getSource() == mode3Button) {
            // Start Game Mode 3
            startGameMode(3);
        } else if (e.getSource() == mode4Button) { // Xử lý sự kiện cho nút mới
            // Start Game Mode 4
            startGameMode(4);
        } else if (e.getSource() == instructionsButton) {
            // Show instructions
            showInstructions();
        } else if (e.getSource() == exitButton) {
            // Exit the application
            System.exit(0);
        }
    }

    private void showMode1And2Options() {
        // Create a dialog to select between Normal and Hard
        String[] options = {"Normal", "Hard"};
        int choice = JOptionPane.showOptionDialog(this, "Select Mode:", "Game Mode 1 & 2",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            // Start Game Mode 1 (Normal)
            startGameMode(1);
        } else if (choice == 1) {
            // Start Game Mode 2 (Hard)
            startGameMode(2);
        }
    }

    private void startGameMode(int mode) {
        // Hide the introduction window
        this.setVisible(false);

        switch (mode) {
            case 1 -> // Start Game Mode 1
                Main.main(new String[]{String.valueOf(mode)});
            case 2 -> // Start Game Mode 2
                Main1.main(new String[]{String.valueOf(mode)});
            case 3 -> // Start Game Mode 3
                Tetrist2.main(new String[]{String.valueOf(mode)});
            case 4 -> // Start Game Mode 4
                Tetrist3.main(new String[]{String.valueOf(mode)});
            default -> // For other modes, just show a message for now
                JOptionPane.showMessageDialog(this, "Game Mode " + mode + " is not implemented yet.");
        }
    }

    private void showInstructions() {
        // Show the instructions window
        JOptionPane.showMessageDialog(this, """
                Instructions:

                - Use arrow keys to move.
                - W to move up.
                - S to move down.
                - A to move left.
                """,
                "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new GameIntroGUI();
    }
}
