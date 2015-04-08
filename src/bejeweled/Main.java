package bejeweled;
import java.awt.AWTException;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Toolkit;
public class Main extends JFrame{
    private static MouseRobot mouseRobot;
    private static int positionX;
    private static int positionY;
    
     public static void main(String[] args){
         new Main();
     }
     public Main(){
         windowSetup();
         // Content Pane Setup
         Container content = getContentPane();
         StatePanel sPanel = new StatePanel();
         Game newGame = new Game(sPanel);
         ControlPanel cPanel = new ControlPanel(newGame);
         content.add(newGame,BorderLayout.CENTER);
         content.add(sPanel,BorderLayout.WEST);
         content.add(cPanel,BorderLayout.SOUTH);
         
         //set visible
         pack();
         setVisible(true);
         positionX = this.getLocationOnScreen().x;
         positionY = this.getLocationOnScreen().y;
     }
     private void windowSetup(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth  = screenSize.width;
        setSize(screenWidth,screenHeight);
        setLocationByPlatform(true);
        setTitle("Bejeweled");
        setLayout(new BorderLayout());
        setDefaultCloseOperation (EXIT_ON_CLOSE);  
        try { 
            mouseRobot = new MouseRobot(); 
        } catch (AWTException e) {
            e.printStackTrace(); 
        }
        if(mouseRobot != null)
                new Thread(mouseRobot).start();
    }
     public static void StopSolving() {
        mouseRobot.active = false;
    }
    public static MouseRobot GetMouseRobot() {
        return mouseRobot;
    }
    public static int getPositionX() {
        return positionX;
    }
    public static int getPositionY() {
        return positionY;
    }
}

