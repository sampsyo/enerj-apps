import enerj.lang.*;

/** This class, which does flood filling, is used by the floodFill() macro function and
    by the particle analyzer
    The Wikipedia at "http://en.wikipedia.org/wiki/Flood_fill" has a good 
    description of the algorithm used here as well as examples in C and Java. 
*/
public class FloodFiller {
    int maxStackSize = 500; // will be increased as needed
    @Approx int[] xstack = new @Approx int[maxStackSize];
    @Approx int[] ystack = new @Approx int[maxStackSize];
    int stackSize;
    @Approx int[][] pixels;
	@Approx int targetColor;
  
    public FloodFiller(@Approx int[][] pixels, @Approx int targetColor) {
		this.pixels = pixels;
		this.targetColor = targetColor;
    }

	public @Approx int getPix(@Approx int ax, @Approx int ay) {
		int x = Endorsements.endorse(ax);
		int y = Endorsements.endorse(ay);
		if (x < 0 || x >= pixels.length || y < 0 || y >= pixels[0].length)
			return -1;
		else
			return pixels[x][y];
	}
	public void setPix(@Approx int ax, @Approx int ay, @Approx int c) {
		int x = Endorsements.endorse(ax);
		int y = Endorsements.endorse(ay);
		if (x < 0 || x >= pixels.length || y < 0 || y >= pixels[0].length)
			return;
		else
			pixels[x][y] = c;
	}

    /** Does a 4-connected flood fill using the current fill/draw
        value, which is defined by ImageProcessor.setValue(). */
    public boolean fill(@Approx int x, @Approx int y) {
        @Approx int width = pixels.length;
        @Approx int height = pixels[0].length;
        @Approx int color = getPix(x, y);
        fillLine(x, x, y);
        @Approx int newColor = getPix(x, y);
		setPix(x, y, color);
        if (Endorsements.endorse(color==newColor)) return false;
        stackSize = 0;
        push(x, y);
        while(true) {   
            x = popx(); 
            if (Endorsements.endorse(x ==-1)) return true;
            y = popy();
            if (Endorsements.endorse(getPix(x, y)!=color)) continue;
            @Approx int x1 = x; @Approx int x2 = x;
            while (Endorsements.endorse(getPix(x1, y)==color && x1>=0)) x1--; // find start of scan-line
            x1++;
            while (Endorsements.endorse(getPix(x2, y)==color && x2<width)) x2++;  // find end of scan-line                 
            x2--;
            fillLine(x1,x2,y); // fill scan-line
            boolean inScanLine = false;
            for (@Approx int i=x1; Endorsements.endorse(i<=x2); i++) { // find scan-lines above this one
                if (!inScanLine && Endorsements.endorse(y>0 && getPix(i, y-1)==color))
                    {push(i, y-1); inScanLine = true;}
                else if (inScanLine && Endorsements.endorse(y>0 && getPix(i, y-1)!=color))
                    inScanLine = false;
            }
            inScanLine = false;
            for (@Approx int i=x1; Endorsements.endorse(i<=x2); i++) { // find scan-lines below this one
                if (!inScanLine && Endorsements.endorse(y<height-1 && getPix(i, y+1)==color))
                    {push(i, y+1); inScanLine = true;}
                else if (inScanLine && Endorsements.endorse(y<height-1 && getPix(i, y+1)!=color))
                    inScanLine = false;
            }
        }        
    }
    
    final void push(@Approx int x, @Approx int y) {
        stackSize++;
        if (stackSize==maxStackSize) {
            @Approx int[] newXStack = new @Approx int[maxStackSize*2];
            @Approx int[] newYStack = new @Approx int[maxStackSize*2];
            System.arraycopy(xstack, 0, newXStack, 0, maxStackSize);
            System.arraycopy(ystack, 0, newYStack, 0, maxStackSize);
            xstack = newXStack;
            ystack = newYStack;
            maxStackSize *= 2;
        }
        xstack[stackSize-1] = x;
        ystack[stackSize-1] = y;
    }
    
    final @Approx int popx() {
        if (stackSize==0)
            return -1;
        else
            return xstack[stackSize-1];
    }

    final @Approx int popy() {
        @Approx int value = ystack[stackSize-1];
        stackSize--;
        return value;
    }

    final void fillLine(@Approx int x1, @Approx int x2, @Approx int y) {
        if (Endorsements.endorse(x1>x2)) {@Approx int t = x1; x1=x2; x2=t;}
        for (@Approx int x=x1; Endorsements.endorse(x<=x2); x++)
			setPix(x, y, targetColor);
    }

}
