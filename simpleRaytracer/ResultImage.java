import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

public class ResultImage {
    public static void main(String[] argv) throws Exception {
        int width = 400;
        int height = 256;
        int[] pixels;

        String filename;
        if (argv.length > 0) {
            filename = argv[0];
        } else {
            filename = "result.ser";
        }

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        pixels = (int[]) in.readObject();
        in.close();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = (WritableRaster)image.getData();
        raster.setDataElements(0, 0, width, height, pixels);
        image.setData(raster);

        boolean status = ImageIO.write(image, "PNG", new File(filename + ".png"));
        if (!status) {
            System.out.println("ImageIO error!");
        }
    }
}
