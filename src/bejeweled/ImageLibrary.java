package bejeweled;
import javax.imageio.*;
import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.awt.image.BufferedImage;
public class ImageLibrary {
    private HashMap<Tile.tileID,BufferedImage> imageCollection;
    public ImageLibrary() {
        imageCollection = new HashMap(8);
        BufferedImage img;
        try {
        img = ImageIO.read(new File("./src/bejeweled/gemRed.png"));
        imageCollection.put(Tile.tileID.RED, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("./src/bejeweled/gemGreen.png"));
        imageCollection.put(Tile.tileID.GREEN, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("./src/bejeweled/gemBlue.png"));
        imageCollection.put(Tile.tileID.BLUE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("./src/bejeweled/gemOrange.png"));
        imageCollection.put(Tile.tileID.ORANGE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("./src/bejeweled/gemPurple.png"));
        imageCollection.put(Tile.tileID.PURPLE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("./src/bejeweled/gemWhite.png"));
        imageCollection.put(Tile.tileID.WHITE, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("./src/bejeweled/gemYellow.png"));
        imageCollection.put(Tile.tileID.YELLOW, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
        try {
        img = ImageIO.read(new File("./src/bejeweled/focus.png"));
        imageCollection.put(Tile.tileID.FOCUS, img);
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }
    BufferedImage getImage(Tile.tileID id) {
        return imageCollection.get(id);
    }
}
