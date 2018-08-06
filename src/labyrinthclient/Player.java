
package labyrinthclient;

import java.awt.Color;
import javax.swing.JLabel;

public class Player {

        private int index;
        private String name;
        private Color color;
        private JLabel label;
        
        public Player(int index, String name, int color){
                this.index = index;
                this.name = name;
                this.color = new Color(color);
        }
        
        public void setIndex(int index){
                this.index = index;
        }
        
        public int getIndex(){
                return index;
        }
        
        public String getName(){
                return name;
        }
        
        public void setColor(Color color){
                this.color = color;
        }
        
        public Color getColor(){
                return color;
        }
        
        public void setPosition(int x, int y){
                label.setLocation(x, y);
        }
        
        public void setLabel(JLabel label){
                this.label = label;
        }
        
        public JLabel getLabel(){
                return label;
        }
}
