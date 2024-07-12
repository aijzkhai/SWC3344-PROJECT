import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame implements ActionListener {
    JButton startButton;

    public WelcomePage() {
        // Create a title for cinema
        setTitle("Welcome to Pak Jaz Western Management System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create image icon logo
        ImageIcon logo = new ImageIcon("meow logo.jpg");
        JLabel logoLabel = new JLabel(logo);
        
        // Create a Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        
        // Create Label
        JLabel label = new JLabel("Welcome to Golden Screen Cinemas", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label, BorderLayout.CENTER);
        
        // Create Start Button
        startButton = new JButton("Start");
        
        // Register to a listener
        startButton.addActionListener(this);
        panel.add(startButton, BorderLayout.SOUTH);
        startButton.setBounds(200, 250, 100, 40);
        
        panel.add(logoLabel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            dispose();
            new MainRestaurant();
        }
    }

    public static void main(String[] args) {
        new WelcomePage();
    }
}
