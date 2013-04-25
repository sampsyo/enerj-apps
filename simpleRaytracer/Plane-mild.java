/*
 * Stupid simple Raytracer. 
 */
import enerj.lang.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;


public class Plane
{
	int w,h;
	float k; // what the hell is this variable for?
	MemoryImageSource idx;
	Image buffer;
	@Approx int pixels[];
	int texture,light;
	float lcoff;
  float sng; // could maybe make approximate
  int numIterations =0;

	public void init(String[] args)
	{
     Dimension dd=new Dimension(400, 256);
     w=dd.width;
     h=dd.height;
     texture=Integer.parseInt(args[0]);//getParameter("texture"));
     light=Integer.parseInt(args[1]);//getParameter("light"));
     pixels=new @Approx int[w*h];
     int index,x,y; //not approx --> for loops and array indexing.
     float xe,ye,ze,xd,yd,zd;
     @Approx float ix,iy,iz;
     float nx,ny,nz;
     float lx,ly,lz;
     float lly; 
     lly=Integer.parseInt(args[2]);//getParameter("lighty"));
     ye=Integer.parseInt(args[3]); //getParameter("viewy"));
     
     nx=0;
     ny=1;
     nz=0;
     int bl=(255<<24); // this stands for black, a constant, maybe?
     float t; //who knows
     float l; //who knows
     float w1,h1; //positioning in image? so don't make approx?
     w1=w/2;
     h1=h/2;
     
     xe=0;
     
     ze=0;
     k=-1;
     
     for(y=0;y<h;y++)
     {
     	 for(x=0;x<w;x++)
     	 {
     		  t=-1;
     		  xd=(x-w1)/w1;
     		  yd=(h1-y)/h1;
     		  zd=-1;
     		  l=xd*xd+yd*yd+zd*zd;
     		  xd/=l;
     		  yd/=l;
     		  zd/=l;
     		
     		  if(((k-ye)*yd)<=0) {
     		    t=-1;
     		  } else {
     		    t=(k-ye)/yd;
          }
     		 
     		  index=y*w+x;
     		  if(t>=0)
     		  {
            ix=xe+t*xd;
            iy=ye+t*yd;
            iz=ze+t*zd;		  	
            lx=0;
            ly=lly;
            lz=0;
            lx=lx-Endorsements.endorse(ix);
            ly=ly-Endorsements.endorse(iy);
            lz=lz-Endorsements.endorse(iz);
            sng=(float)Math.sqrt(Endorsements.endorse(lx*lx+ly*ly+lz*lz));
            // sng=1.7f/sng;
            sng=1.0f/sng;
            lcoff=(lx*nx+ly*ny+lz*nz)*sng;
            pixels[index]=texture(ix,iy,iz);
     		  } else {
     		    pixels[index]=bl;
          }
          numIterations++;
     	 }
     }
     checkErrors(pixels);
	}

  public @Approx int texture(@Approx float x,@Approx float y, @Approx float z) {
 	  int v;
 	  int col;
 	  int r,g,b; //TODO: emily could maybe make thiese approx again.
 	  r=255;
   	b=0;
  	col=0;
  	if(light!=0) {
  	  r=(int)(255*lcoff);
    }
  	b=r;
 	
 	  if(texture==1) {
 	 	  col=(255<<24)|(255<<16);
 	  } else if(texture==2) {
      v=(Math.round(Endorsements.endorse(x))+Math.round(Endorsements.endorse(z))) %2;
 	    if(v==0) {
 	      col=(255<<24)|b;
      } else {
 	      col=(255<<24)|(r<<16);
      }
 	  }
 	   
    if(numIterations == 25) {
      System.gc(); 
    }
 	  return col;
  }

  public static void main(String[] args) {
    Plane p = new Plane();
    p.init(args);
  } 

  public void checkErrors(@Approx int[] pixels) {
  
    // for kicks, so we can see what the result looked like, we serialize it to a file.
    try{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("result.ser"));
        out.writeObject(pixels);
        out.flush();
        out.close();
    } catch (Exception e) {e.printStackTrace();}


    int[] referenceImage = new int[0];
    try{
      // Deserialize the reference int[]
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("reference.ser"));
      referenceImage = (int[]) in.readObject();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    int diff = 0;
    for (int i = 0; i < pixels.length; i++) {
      // count absolute RGB differences
      diff += Math.abs((Endorsements.endorse(pixels[i]) & 0xFF) - (referenceImage[i] & 0xFF));
      diff += Math.abs(((Endorsements.endorse(pixels[i]) >> 8) & 0xFF) - ((referenceImage[i] >> 8) & 0xFF));
      diff += Math.abs(((Endorsements.endorse(pixels[i]) >> 16) & 0xFF) - ((referenceImage[i] >> 16) & 0xFF));
    }
    if (diff > 0) {
      System.err.println("Image check failed! - #errors: " + diff);
    } else {
      System.err.println("Image check passed!");
    }
  }
}
