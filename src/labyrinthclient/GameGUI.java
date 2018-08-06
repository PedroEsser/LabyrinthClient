
package labyrinthclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameGUI extends JFrame{
  
        private JPanel panel;
        private int width, height, squareSize;
        private JLabel obstructor;
        private JLabel message;
        private JLabel timer;
        private LinkedList <Player> players;
        private LinkedList <Point> coins;
        private LinkedList <Wall> walls;

        public GameGUI(InformationHandler player, String labyrinthInformation, LinkedList <Player> players){
                super("Maze.io");
                
                this.players = players;
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                this.addKeyListener(player);
                this.setResizable(false);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(true);
                String[] splitLabirinthInformation = labyrinthInformation.split(":");
                int labyrinthWidth = Integer.parseInt(splitLabirinthInformation[1]);
                int labyrinthHeight = Integer.parseInt(splitLabirinthInformation[2]);
                
                if(d.width / labyrinthWidth < d.height / labyrinthHeight){
                        int help = d.width * 8 / 10;
                        squareSize = help / labyrinthWidth;
                }else{
                        int help = d.height * 8 / 10;
                        squareSize = help / labyrinthHeight;
                }
                width = labyrinthWidth * squareSize;
                height = labyrinthHeight * squareSize;
                
                this.setBounds(d.width/2 - width/2, d.height/2 - height/2, width + 207, height + 36);
                
                setupLabyrinthInformation(splitLabirinthInformation);
                
                panel = new JPanel(){
                        @Override
                        protected void paintComponent(Graphics g){
                                drawMaze(g);
                        }
                };
                panel.setBackground(Color.BLACK);
                panel.setLayout(null);
                
                obstructor = new JLabel();
                obstructor.setBounds(0, 0, width, height);
                obstructor.setBackground(Color.black);
                obstructor.setOpaque(true);
                panel.add(obstructor, 1, 0);
                
                message = new JLabel();
                message.setBounds(0, height/2, width, 50);
                message.setFont(new Font("Arial", Font.BOLD, 40));
                message.setForeground(Color.white);
                message.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(message, 1, 0);
                
                timer = new JLabel("Timer");
                timer.setBounds(width, 10, 200, 30);
                timer.setFont(new Font("Arial", Font.BOLD, 30));
                timer.setForeground(Color.white);
                timer.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(timer, 1, 0);
                
                this.add(panel);
        }
        
        private void setupLabyrinthInformation(String[] splitLabirinthInformation){
                int coins = Integer.parseInt(splitLabirinthInformation[3]);
                
                this.walls = new LinkedList <>();
                
                for(int i = coins + 4 ; i < splitLabirinthInformation.length ; i++){
                        String[] wall = splitLabirinthInformation[i].split(",");
                        int x1 = Integer.parseInt(wall[0]) * squareSize;
                        int y1 = Integer.parseInt(wall[1]) * squareSize;
                        int x2 = Integer.parseInt(wall[2]) * squareSize;
                        int y2 = Integer.parseInt(wall[3]) * squareSize;
                        this.walls.add(new Wall(x1,y1,x2,y2));
                }
                
                this.coins = new LinkedList <>();
                
                for(int i = 0 ; i < coins ; i++){
                        String[] coin = splitLabirinthInformation[i + 4].split(",");
                        int x = Integer.parseInt(coin[0]);
                        int y = Integer.parseInt(coin[1]);
                        this.coins.add(new Point(x,y));
                }
        }
        
        public void drawMaze(Graphics g){
                g.setColor(Color.black);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                
                g.setColor(Color.green);
                
                g.fillRect(width/2 - squareSize/2 + squareSize/8, height/2 - squareSize/2 + squareSize/8, squareSize*3/4, squareSize*3/4);
                
                g.setColor(Color.yellow);
                for(int i = 0 ; i < coins.size() ; i++){
                        Point c = coins.get(i);
                        g.fillOval(c.x * squareSize + squareSize/4, c.y * squareSize + squareSize/4, squareSize/2, squareSize/2);
                }
                
                g.setColor(Color.white);
                for(int i = 0 ; i < walls.size() ; i++){
                        walls.get(i).drawWall(g);
                }
                
                g.drawLine(0, 0, width, 0);
                g.drawLine(0, 0, 0, height);
                g.drawLine(width, 0, width, height);
                g.drawLine(0, height, width, height);
                
        }
        
        public void addPlayerLabels(String info){
                String[] splitInfo = info.split(":");
                for(int i = 0 ; i < players.size() ; i++){
                        String[] positions = splitInfo[i+1].split(",");
                        int x = Integer.parseInt(positions[0]);
                        int y = Integer.parseInt(positions[1]);
                        
                        Player p = players.get(i);
                        JLabel label = new JLabel();
                        label.setBounds(x * squareSize + 1, y * squareSize + 1, squareSize - 1, squareSize - 1);
                        label.setBackground(p.getColor());
                        label.setOpaque(true);
                        label.setVisible(false);
                        p.setLabel(label);
                        panel.add(label, 2, 0);
                }
                
        }
        
        public void notify(String info){
                message.setText("" + info.split(",")[1]);
        }
        
        public void startGame(){
                obstructor.setVisible(false);
                message.setVisible(false);
                for(int i = 0 ; i < players.size() ; i++){
                        players.get(i).getLabel().setVisible(true);
                }
        }
        
        public void endGame(){
                obstructor.setVisible(true);
                message.setVisible(true);
                for(int i = 0 ; i < players.size() ; i++){
                        players.get(i).getLabel().setVisible(false);
                }
        }
        
        public void updatePlayerPosition(String information){
                String[] splitInformation = information.split(",");
                int index = Integer.parseInt(splitInformation[1]);
                int x = Integer.parseInt(splitInformation[2]);
                int y = Integer.parseInt(splitInformation[3]);
                players.get(index).setPosition(x * squareSize + 1, y * squareSize + 1);
        }
        
        public void removePlayer(Player p){
                JLabel label = p.getLabel();
                label.setVisible(false);
                this.panel.remove(label);
        }
        
        public void removeCoin(String info){
                int index = Integer.parseInt(info.split(",")[1]);
                this.coins.remove(index);
        }
        
        public void updateTimer(String info){
                this.timer.setText(info.split(",")[1]);
        }
        
        public void test(){
                
        }
        
}
