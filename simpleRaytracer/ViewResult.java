import java.awt.*;
import java.applet.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

public class ViewResult extends Applet {
    private MemoryImageSource idx;
    private Image buffer;

    public void init() {
        int width = 400;
        int height = 256;
        int[] pixels = new int[0];

        try{
            // Deserialize the reference int[]
            URL source = new URL(getCodeBase(), "result.ser");
            ObjectInputStream in = new ObjectInputStream(source.openStream());
            pixels = (int[]) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        idx = new MemoryImageSource(width, height, pixels, 0, width);
        buffer = createImage(idx);
    }

    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

}
