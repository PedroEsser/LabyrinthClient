
package labyrinthclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ErrorMessage extends JFrame{

        public ErrorMessage(String message, StartWindow startWindow){
                super();
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setResizable(false);
                this.setBounds(d.width/2 - 150, d.height/2 - 50, 300, 100);
                
                
                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.black);
                
                JLabel messageLabel = new JLabel(message);
                messageLabel.setFont(new Font("Arial", Font.BOLD, 15));
                messageLabel.setForeground(Color.white);
                messageLabel.setBounds(0, 0, 300, 30);
                messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(messageLabel);
                
                JButton backButton = new JButton("Go Back");
                backButton.setBounds(100, 30, 100, 30);
                backButton.addActionListener((ActionEvent e) -> {
                        this.dispose();
                        startWindow.setVisible(true);
                });
                panel.add(backButton);
                
                this.add(panel);
                this.setVisible(true);
        }
}
