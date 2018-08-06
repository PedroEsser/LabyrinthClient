
package labyrinthclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ColorPicker extends JFrame{

        private JButton[] availableColors;
        
        public ColorPicker(InformationHandler player, String info){
                super("ColorPicker");
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                this.setBounds(d.width/2 - 200, d.height/2 - 125, 500, 250);
                this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                this.setResizable(false);
                
                JPanel panel = new JPanel();
                panel.setBackground(Color.black);
                panel.setLayout(null);
                
                availableColors = new JButton[8];
                
                String[] splitInfo = info.split(":");
                
                for(int i = 1 ; i < splitInfo.length ; i++){
                        String[] colorInfo = splitInfo[i].split(",");
                        JButton b = new JButton(colorInfo[0]);
                        b.setEnabled(false);
                        b.setForeground(Color.gray);
                        b.setBackground(new Color(Integer.parseInt(colorInfo[1])));
                        b.setBorder(BorderFactory.createLineBorder(Color.white, 2));
                        
                        int x = ((i-1)%4) * 120;
                        int y = ((i-1)/4) * 100;
                        b.setBounds(20 + x, 20 + y, 100, 80);
                        
                        b.addActionListener((ActionEvent e) -> {
                                player.send("c," + colorInfo[2]);
                                this.dispose();
                        });
                        panel.add(b);
                        availableColors[i-1] = b;
                }
                this.add(panel);
        }
        
        
        
        public void deactivateColor(String info){
                int index = Integer.parseInt(info.split(",")[1]);
                availableColors[index].setForeground(Color.gray);
                availableColors[index].setEnabled(false);
        }
        
        public void activateColor(String info){
                int index = Integer.parseInt(info.split(",")[1]);
                availableColors[index].setForeground(Color.black);
                availableColors[index].setEnabled(true);
        }
        
        @Override
        public void dispose(){
                this.setVisible(false);
        }
        
        public void exit(){
                super.dispose();
        }
}
