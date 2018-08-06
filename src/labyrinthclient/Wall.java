
package labyrinthclient;

import java.awt.Graphics;

public class Wall {

        int x1, y1, x2, y2;
        
        public Wall(int x1, int y1, int x2, int y2){
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
        }
        
        public void drawWall(Graphics g){
                g.drawLine(x1, y1, x2, y2);
        }
}
