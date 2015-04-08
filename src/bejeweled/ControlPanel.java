package bejeweled;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlPanel extends JPanel implements ActionListener {
    private JButton newGameButton = new JButton("New Game");
    private Game game;
    private JButton exit = new JButton("Exit");
    private JButton cheat = new JButton("Cheat");
    public ControlPanel(Game game){
        this.game = game;
        add(newGameButton);
        newGameButton.addActionListener(this);
        add(exit);
        exit.addActionListener(this);
        add(cheat);
        cheat.addActionListener(this);

    }
    public void actionPerformed(ActionEvent e){
      if (e.getSource().equals(newGameButton)){
            game.initGame();
           game.Solve();
            
//            
//            Point startPoint = new Point(10, 10);
//            Point endPoint = new Point(500, 500);
//            Main.GetMouseRobot().MakeMove(new Point(Main.getPositionX() + 400, Main.getPositionY() + 90), new Point(Main.getPositionX() + 460, Main.getPositionY() + 150));
      }
      if (e.getSource().equals(cheat))
          game.addScore(500);
      if (e.getSource().equals(exit))
          System.exit(0);
   }
}
