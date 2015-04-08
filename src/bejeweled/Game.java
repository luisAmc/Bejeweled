package bejeweled;

import java.awt.AWTException;
import java.awt.Color;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
/*
 This class records the state of the game
 It will be extended to support animation 
 and the various states of the bejeweled game.
 */
public final class Game extends JComponent{
    private StatePanel sPanel;
    private Board gameBoard;
    private Algorithms solver;
    private Animation animation;
    private Tile focus;
    private boolean started;
    private int score;
    private int level;
    private int combo;
    public static ImageLibrary imageLibrary = new ImageLibrary();
    public static SoundLibrary soundLibrary = new SoundLibrary();
    public BufferedImage boardImg;
    public ImageIcon boardIcon;
    
    public Game(StatePanel sPanel){
        try {
           boardImg = ImageIO.read(new File("./src/bejeweled/board.png"));
        } catch (IOException e) { System.out.println(e.getMessage());}
        this.boardIcon = new ImageIcon();
        this.boardIcon.setImage(this.boardImg);
        started = false;
        this.sPanel = sPanel;
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(800,600)); 
        this.addMouseListener(new MouseListener(this));
    }
    public void initGame(){
        gameBoard = new Board(this);
        solver = new Algorithms(gameBoard,this);
        animation = new Animation(this,gameBoard,null);
        // initialize game
        gameBoard.initAll();
        while(!solver.isStable()) {
            solver.rmChains();
        }
        //set up state information
        started = true;
        focus = null;
        score = 0;
        combo = 0;
        level = 1;
        sPanel.setScore(score);
        sPanel.setCombo(combo);
        sPanel.setLevel(level);
        sPanel.setRow(-1);
        sPanel.setColumn(-1);
        repaint();
        Game.soundLibrary.playAudio("fall");
    }
    public void updateGame() {
        if (!solver.isStable()) {
            solver.markDeleted();
            solver.calculateDrop();
            animation.setType(Animation.animType.CASCADE);
            animation.animateCascade();
            Game.soundLibrary.playAudio("fall");
        }
    }
    public void cleanBoard() {
        solver.applyDrop();
        solver.fillEmpty();
        solver.endCascade();
    }
    public void addScore(int points){
         if ((this.score+points) > 1000){
            this.level++;
            sPanel.setLevel(this.level);
            this.score = 0;
            sPanel.setScore(this.score);
        }
         else {
           this.score += points;
           sPanel.setScore(score);
         }
        
    }
    public void setCombo(int combo){
        if (combo > this.combo){
          this.combo = combo;
          sPanel.setCombo(combo);
        }
    }
    public void clickPerformed(int click_x,int click_y) {
        sPanel.setColumn(click_x);
        sPanel.setRow(click_y);
        Tile clicked = gameBoard.getTileAt(click_y, click_x);
        if (focus == null) {
            focus = clicked;
            clicked.inFocus = true;
            Game.soundLibrary.playAudio("select");
        }
        else {
            if (focus.equals(clicked)) {
                clicked.inFocus = false;
                focus = null;
            }
            else {
                if(focus.isNeighbor(clicked)){
                    focus.inFocus = false;
                    swapTiles(focus,clicked);
                    focus = null;
                }
                else {
                    focus.inFocus = false;
                    focus = clicked;  
                    clicked.inFocus = true;
                }
            }
        }
    }
    private void swapTiles(Tile t1,Tile t2){
        animation.setType(Animation.animType.SWAP);
        animation.animateSwap(t1, t2);
    }
    public void paintComponent(Graphics g){
        this.boardIcon.paintIcon(null, g, 0, 0);
        if(started)
        drawGems(g);
    }
    private void drawGems(Graphics g){
        int row,col;
        for (row=0;row<8;row++){
            for (col=0;col<8;col++){
                Tile tile = gameBoard.getTileAt(row, col);
                tile.draw(g);
            }
        }
    }
    
    /*------------------------------------------------------------------------------------------------------------------------------------------------
     *-----------------------------------------------------Inicio de la AI-----------------------------------------------------------------------------
     */
    
    public void Solve() {
        
        int cont = 0, xDiff = 0, yDiff = 0;
        Robot bot = null;
        try {
          bot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int row = 0; row < 8; row++, yDiff += 65) {
            for (int col = 0; col < 8; col++, xDiff += 65) {
                
                try {
                    if (!(row == 0 || row == 7 || col == 0 || col == 7)) {
                        if (row == 1 || row == 6 || col == 1 || col == 6) {
                            System.out.println("1");
                            int[] pos = innerBorder(row, col);
                           
                            bot.mouseMove(Main.getPositionX() + 400 + xDiff, Main.getPositionY() + 90 + yDiff);    
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);

                            bot.mouseMove(Main.getPositionX() + 400 + (pos[0] * 65), Main.getPositionY() + 90 + (pos[1] * 65));    
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                        
                        } else if ((row > 2 && row < 5) && (col > 2 && col < 5)){
                            System.out.println("2");
                            int[] pos = innerCoreBoard(row, col);
                            
                            bot.mouseMove(Main.getPositionX() + 400 + xDiff, Main.getPositionY() + 90 + yDiff);    
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            
                            bot.mouseMove(Main.getPositionX() + 400 + (pos[0] * 65), Main.getPositionY() + 90 + (pos[1] * 65));    
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                        }else if ((row > 1 && row < 6) && (col > 1 && col < 6)) {
                            System.out.println("3");
                            int[] pos = coreBoard(row, col);
                            
                            bot.mouseMove(Main.getPositionX() + 400 + xDiff, Main.getPositionY() + 90 + yDiff);    
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            
                            bot.mouseMove(Main.getPositionX() + 400 + (pos[0] * 65), Main.getPositionY() + 90 + (pos[1] * 65));    
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                        } 
                    }
                } catch (Exception ex) {
                }
                
                cont++;
                if (cont < 100 && col == 7) {
                    col = 0;
                    xDiff = 0;
                    
                }
               xDiff = 0;
            }
            if (cont < 100 && row == 7){
                row = 0;
                yDiff = 0;
            }
        }
    }

    private int[] innerBorder(int row, int col) {
        Tile tile = gameBoard.getTileAt(row, col);
        Tile[] diagNeighbors = {gameBoard.getTileAt(row - 1, col - 1),
                            gameBoard.getTileAt(row - 1, col + 1),
                            gameBoard.getTileAt(row + 1, col - 1),
                            gameBoard.getTileAt(row + 1, col + 1)};

        if (checkNeighborsGem(tile, diagNeighbors[0], diagNeighbors[1]))
            return new int[]{row - 1, col};
        else if (checkNeighborsGem(tile, diagNeighbors[2], diagNeighbors[3]))
            return new int[]{row + 1, col};
        else if (checkNeighborsGem(tile, diagNeighbors[1], diagNeighbors[3]))
            return new int[]{row, col + 1};
        else 
            return new int[]{row, col - 1};
    }

    private int[] coreBoard(int row, int col) {
        Tile tile = gameBoard.getTileAt(row, col);

        boolean upL = checkNeighborsGem(tile, gameBoard.getTileAt(row - 1, col - 1), gameBoard.getTileAt(row - 1, col - 2));
        boolean upR = checkNeighborsGem(tile, gameBoard.getTileAt(row - 1, col + 1), gameBoard.getTileAt(row - 1, col + 2));

        boolean leftU = checkNeighborsGem(tile, gameBoard.getTileAt(row - 1, col - 1), gameBoard.getTileAt(row - 2, col - 1));
        boolean leftD = checkNeighborsGem(tile, gameBoard.getTileAt(row + 1, col - 1), gameBoard.getTileAt(row + 2, col - 1));

        boolean rightU = checkNeighborsGem(tile, gameBoard.getTileAt(row - 1, col + 1), gameBoard.getTileAt(row - 2, col + 1));
        boolean rightD = checkNeighborsGem(tile, gameBoard.getTileAt(row + 1, col + 1), gameBoard.getTileAt(row + 2, col + 1));

        boolean downL = checkNeighborsGem(tile, gameBoard.getTileAt(row + 1, col - 1), gameBoard.getTileAt(row + 1, col - 2));
        boolean downR = checkNeighborsGem(tile, gameBoard.getTileAt(row + 1, col + 1), gameBoard.getTileAt(row + 1, col + 2));

        if (upL || upR)
            return new int[]{row - 1, col};
        else if (downL || downR)
            return new int[]{row + 1, col};
        else if (leftU || leftD)
            return new int[]{row, col - 1};
        else 
            return new int[]{row, col + 1};
    }

    private int[] innerCoreBoard(int row, int col) {
        Tile tile = gameBoard.getTileAt(row, col);
        
        boolean up = checkNeighborsGem(tile, gameBoard.getTileAt(row - 2, col), gameBoard.getTileAt(row - 3, col));
        boolean left = checkNeighborsGem(tile, gameBoard.getTileAt(row, col - 2), gameBoard.getTileAt(row, col - 3));
        boolean right = checkNeighborsGem(tile, gameBoard.getTileAt(row, col + 2), gameBoard.getTileAt(row, col + 3));
        boolean down = checkNeighborsGem(tile, gameBoard.getTileAt(row + 2, col), gameBoard.getTileAt(row + 3, col));
        
        if (up)
            return new int[]{row - 1, col};
        else if (down)
            return new int[]{row + 1, col};
        else if (left)
            return new int[]{row, col - 1};
        else 
            return new int[]{row, col + 1};
    }
    
    private boolean checkNeighborsGem(Tile t1, Tile t2, Tile t3) {
        return ((t1.id == t2.id) && (t2.id == t3.id));
    }
    
}
