import java.io.*;

import enerj.lang.*;

public class FFTest {
	static @Approx int[][] image;
	static final int WIDTH = 100;
	static final int HEIGHT = 100;

    private static int asciiNum(int c) {
        return c - 48;
    }
	
	public static void main(String[] argv) throws Exception {
		int width = new Integer(argv[0]);
		int height = new Integer(argv[1]);
		String filename = argv[2];
		
		image = new @Approx int[width][height];
		
		FileReader reader = new FileReader(filename);
		int c;
		int x = 0;
		int y = 0;
		while ((c = reader.read()) != -1) {
			if (c == '\n') {
				y++;
				x = 0;
			} else {
				image[x][y] = asciiNum(c);
				x++;
			}
        }

		FloodFiller ff = new FloodFiller(image, 2);
		ff.fill(0, 0);
		for (y = 0; y < height; ++y) {
			for (x = 0; x < width; ++x) {
				System.out.print(Endorsements.endorse(image[x][y]) + " ");
			}
		}
		System.out.println("");
	}
}
