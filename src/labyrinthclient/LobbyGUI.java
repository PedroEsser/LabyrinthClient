
package labyrinthclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class LobbyGUI extends JFrame implements KeyListener{

        private JPanel panel;
        private InformationHandler player;
        private JLabel[] playerLabels;
        private JTextField message;
        private JLabel updates;
        private JTextPane chatBox;
        private StyledDocument chat;
        private Style style;
        
        public LobbyGUI(InformationHandler player){
                super("Lobby");
                this.player = player;
                this.playerLabels = new JLabel[8];
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                
                this.setBounds(d.width/2 - 300, d.height/2  - 200, 700, 450);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setResizable(false);
                
                panel = new JPanel(){
                        @Override
                        protected void paintComponent(Graphics g){
                                g.setColor(Color.black);
                                g.fillRect(0, 0, 700, 450);
                                g.setFont(new Font("Arial", Font.BOLD, 30));
                                g.setColor(Color.white);
                                g.drawString("Lobby", 20, 30);
                                g.fillRect(10, 45, 690, 2);
                                g.fillRect(10, 350, 690, 2);
                        }
                };
                panel.setLayout(null);
                
                chatBox = new JTextPane();
                JScrollPane sp = new JScrollPane(chatBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                chatBox.setEditable(false);
                sp.setBounds(500, 55, 190, 250);
                chatBox.setBackground(Color.black);
                chatBox.setForeground(Color.white);
                sp.setBorder(BorderFactory.createLineBorder(Color.white, 2));
                panel.add(sp);
                
                chat = chatBox.getStyledDocument();
                style = chatBox.addStyle("", null);
                
                message = new JTextField();
                message.setBounds(500, 307, 190, 30);
                message.setBackground(Color.black);
                message.setForeground(Color.white);
                message.setBorder(BorderFactory.createLineBorder(Color.white, 2));
                message.addKeyListener(this);
                panel.add(message);
                
                setLabels();
                
                updates = new JLabel("Waiting for players...");
                updates.setBounds(20, 365, 380, 40);
                updates.setForeground(Color.white);
                updates.setFont(new Font("Arial", Font.BOLD, 25));
                panel.add(updates);
                
                JButton colorButton = new JButton("Pick different color");
                colorButton.setBounds(500, 358, 190, 50);
                colorButton.setBackground(Color.black);
                colorButton.setForeground(Color.white);
                colorButton.setFont(new Font("Arial", Font.BOLD, 20));
                colorButton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
                colorButton.addActionListener((ActionEvent e) -> {
                                this.player.setColorPickerVisible();
                        });
                panel.add(colorButton);
                
                this.add(panel);
                
                this.setVisible(true);
        }
        
        private void setLabels(){
                for(int i = 0 ; i < 8 ; i++){
                        JLabel label = new JLabel("");
                        label.setSize(470, 30);
                        label.setLocation(20, 55 + i * 36);
                        label.setFont(new Font("Arial", Font.BOLD, 20));
                        playerLabels[i] = label;
                        panel.add(label);
                }
        }
        
        public void addPlayer(Player player){
                int index = player.getIndex();
                playerLabels[index].setText(player.getName());
                playerLabels[index].setForeground(player.getColor());
                playerLabels[index].setBorder(BorderFactory.createLineBorder(player.getColor(), 1));
        }
        
        public void removePlayer(int index){
                playerLabels[index].setText("");
                playerLabels[index].setBorder(null);
        }
        
        public void receiveMessage(String message){
                String[] splitMessage = message.split(",");
                appendToChat(splitMessage[1], new Color(Integer.parseInt(splitMessage[2])));
                appendToChat(splitMessage[3] + "\n", Color.WHITE);
        }
        
        private void appendToChat(String msg, Color c){
        
                StyleConstants.setForeground(style, c);

                try { 
                        chat.insertString(chat.getLength(), msg, style); 
                }catch (BadLocationException e){
                }
        
        }
        
        private boolean validMessage(String message){
                if(message.equals("")){
                        return false;
                }
                for(int i = 0 ; i < message.length() ; i++){
                        if(message.charAt(i) != ' '){
                                return true;
                        }
                }
                return false;
        }
        
        public void notify(String info){
                this.updates.setText(info.split(",")[1]);
        }
        
        public void clearChat(){
                this.chatBox.setText("");
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        String message = this.message.getText();
                        if(validMessage(message)){
                                player.send("m," + this.message.getText());
                        }
                        this.message.setText("");
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
        
}
