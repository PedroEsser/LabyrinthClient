
package labyrinthclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StartWindow extends JFrame{
        
        public StartWindow(){
                super();
                
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                this.setBounds(d.width/2 - 200, d.height/2 - 150, 350, 200);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setResizable(false);
                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.black);
                this.add(panel);
                
                JLabel ipLabel = new JLabel("IP:");
                ipLabel.setFont(new Font("Arial", Font.BOLD, 20));
                ipLabel.setForeground(Color.white);
                ipLabel.setBounds(0, 20, 70, 30);
                ipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                panel.add(ipLabel);
                
                String ipAdress = "";
                try {
                        ipAdress = InetAddress.getLocalHost().getHostAddress().trim();
                } catch (UnknownHostException ex) {
                }
                
                JTextField IPTextField = new JTextField(ipAdress);
                IPTextField.setBounds(70, 20, 150, 30);
                panel.add(IPTextField);
                
                JLabel portLabel = new JLabel("Port:");
                portLabel.setFont(new Font("Arial", Font.BOLD, 20));
                portLabel.setForeground(Color.white);
                portLabel.setBounds(0, 60, 70, 30);
                portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                panel.add(portLabel);
                
                JTextField portTextField = new JTextField("666");
                portTextField.setBounds(70, 60, 150, 30);
                panel.add(portTextField);
                
                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
                nameLabel.setForeground(Color.white);
                nameLabel.setBounds(0, 100, 70, 30);
                nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                panel.add(nameLabel);
                
                JTextField nameTextField = new JTextField("Player");
                nameTextField.setBounds(70, 100, 150, 30);
                panel.add(nameTextField);
                
                JButton connectButton = new JButton("Connect!");
                connectButton.setBounds(230, 20, 90, 120);
                connectButton.addActionListener((ActionEvent e) -> {
                        connect(IPTextField.getText(), Integer.parseInt(portTextField.getText()), nameTextField.getText());
                });
                panel.add(connectButton);
                this.setVisible(true);
        }
        
        private void connect(String ip, int port, String name){
                try {
                        Socket socket = new Socket(ip, port);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        new InformationHandler(reader, writer, name, this);
                        this.setVisible(false);
                } catch (IOException e) {
                }
        }
        
        public static void main(String[] args){
                for(int i = 0 ; i < 1 ; i++){
                        new StartWindow();
                }
        }
}
