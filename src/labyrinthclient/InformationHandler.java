
package labyrinthclient;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

public class InformationHandler implements KeyListener{
  
        private StartWindow startWindow;
        private BufferedReader reader = null;
        private PrintWriter writer = null;
        private boolean alive = true;
        private LobbyGUI lobbyGUI;
        private GameGUI gameGUI;
        private ColorPicker colorPicker;
        private LinkedList <Player> players;

        public InformationHandler(BufferedReader reader, PrintWriter writer, String name, StartWindow startWindow) {
                this.reader = reader;
                this.writer = writer;
                this.players = new LinkedList <>();
                this.startWindow = startWindow;
                send("n," + name);
                listen();
        }

        public void send(String buffer) {
                try {
                        writer.write(buffer + "\n");
                        writer.flush();
                } catch (Exception e) {
                }
        }
        
        private void listen() {
                new Thread(() ->{
                        try {
                                while (alive) {
                                        String info = reader.readLine();
                                        processInformation(info);
                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                                //System.out.println("Disconnected!");
                        }
                }).start();
                
        }
        
        private void processInformation(String info){
                switch(info.charAt(0)){
                        case 'u':this.gameGUI.updatePlayerPosition(info);
                        break;
                        case 't':this.gameGUI.updateTimer(info);
                        break;
                        case 'm':this.lobbyGUI.receiveMessage(info);
                        break;
                        case 'a':this.addPlayer(info);
                        break;
                        case 'r':this.removePlayer(info);
                        break;
                        case 'd':this.lobbyGUI.dispose();
                                try{
                                        this.gameGUI.dispose();
                                }catch(Exception e){}
                                try{
                                        this.colorPicker.exit();
                                }catch(Exception e){}
                                new ErrorMessage("Server disconnected", startWindow);
                        break;
                        case 'n':this.lobbyGUI.notify(info);
                        break;
                        case 's':this.swapColors(info);
                        break;
                        case 'l':this.lobbyGUI = new LobbyGUI(this);
                        break;
                        case 'c':this.colorPicker = new ColorPicker(this, info);
                        break;
                        case 'b':this.colorPicker.activateColor(info);
                        break;
                        case 'f':this.colorPicker.deactivateColor(info);
                        break;
                        case 'g':this.lobbyGUI.setVisible(false);
                        this.lobbyGUI.clearChat();
                                this.gameGUI = new GameGUI(this, info, players);
                        break;
                        case 'p':this.gameGUI.addPlayerLabels(info);
                        break;
                        case 'v':this.makePlayerVisible(info);
                        break;
                        case 'o':this.gameGUI.removeCoin(info);
                        break;
                        case 'h':this.gameGUI.notify(info);
                        break;
                        case 'k':this.toggleGame(info);
                        break;
                        case 'x':this.gameGUI.dispose();
                        this.lobbyGUI.setVisible(true);
                        break;
                        case 'e':new ErrorMessage(info.split(",")[1], startWindow);
                        break;
                        default:
                        break;
                }
        }
        
        private void addPlayer(String info){
                String[] splitInfo = info.split(",");
                String name = splitInfo[1];
                int color = Integer.parseInt(splitInfo[2]);
                Player p = new Player(players.size(), name, color);
                players.add(p);
                this.lobbyGUI.addPlayer(p);
        }
        
        private void removePlayer(String info){
                int index = Integer.parseInt(info.split(",")[1]);
                Player p = players.get(index);
                players.remove(p);
                for(int i = index ; i < players.size() ; i++){
                        Player player = players.get(i);
                        player.setIndex(i);
                        this.lobbyGUI.addPlayer(player);
                }
                this.lobbyGUI.removePlayer(players.size());
                try{
                        this.gameGUI.removePlayer(p);
                }catch(Exception e){
                }
        }
        
        private void swapColors(String info){
                String[] splitInfo = info.split(",");
                int index = Integer.parseInt(splitInfo[1]);
                int color = Integer.parseInt(splitInfo[2]);
                players.get(index).setColor(new Color(color));
                this.lobbyGUI.addPlayer(players.get(index));
        }
        
        private void makePlayerVisible(String info){
                int index = Integer.parseInt(info.split(",")[1]);
                boolean visible = info.split(",")[2].equals("true");
                players.get(index).getLabel().setVisible(visible);
        }
        
        public void setColorPickerVisible(){
                this.colorPicker.setVisible(true);
        }
        
        private void toggleGame(String info){
                boolean start = info.split(",")[1].equals("start");
                if(start){
                        this.gameGUI.startGame();
                }else{
                        this.gameGUI.endGame();
                }
        }
  
        @Override
        public void keyTyped(KeyEvent e) {
                
        }

        @Override
        public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                        case KeyEvent.VK_A: send("u,left");
                        break;
                        case KeyEvent.VK_LEFT: send("u,left");
                        break;
                        case KeyEvent.VK_S: send("u,down");
                        break;
                        case KeyEvent.VK_DOWN: send("u,down");
                        break;
                        case KeyEvent.VK_D: send("u,right");
                        break;
                        case KeyEvent.VK_RIGHT: send("u,right");
                        break;
                        case KeyEvent.VK_W: send("u,up");
                        break;
                        case KeyEvent.VK_UP: send("u,up");
                        break;
                        case KeyEvent.VK_T:this.gameGUI.test();
                        break;
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                
        }
}