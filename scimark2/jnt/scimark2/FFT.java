package jnt.scimark2;

import enerj.lang.*;

/** Computes FFT's of complex, double precision data where n is an integer power of 2.
  * This appears to be slower than the Radix2 method,
  * but the code is smaller and simpler, and it requires no extra storage.
  * <P>
  *
  * @author Bruce R. Miller bruce.miller@nist.gov,
  * @author Derived from GSL (Gnu Scientific Library), 
  * @author GSL's FFT Code by Brian Gough bjg@vvv.lanl.gov
  */

  /* See {@link ComplexDoubleFFT ComplexDoubleFFT} for details of data layout.
   */

public class FFT {

  /** Compute Fast Fourier Transform of (complex) data, in place.*/
  public static void transform (@Approx double data[]) {
    transform_internal(data, -1); }

  /** Compute Inverse Fast Fourier Transform of (complex) data, in place.*/
  public static void inverse (@Approx double data[]) {
    transform_internal(data, +1);  
    // Normalize
    int nd=data.length;
    int n =nd/2;
    @Approx int aprN = n;
    @Approx double norm=1.0/aprN;
    for(int i=0; i<nd; i++)
      data[i] *= norm;
  }

  /** Accuracy check on FFT of data. Make a copy of data, Compute the FFT, then
    * the inverse and compare to the original.  Returns the rms difference.*/
  public static @Approx double test(@Approx double data[]){
    int nd = data.length;
    // Make duplicate for comparison
    @Approx double[] copy = new @Approx double[nd];
    System.arraycopy(data, 0, copy, 0, nd);
    // Transform & invert
    transform(data);
    inverse(data);
    // Compute RMS difference.
    @Approx double diff = 0.0;
    for(int i=0; i<nd; i++) {
      @Approx double d = data[i]-copy[i];
      diff += d*d; }
    return ApproxMath.sqrt(diff/nd); }


  protected static int log2 (int n){
    int log = 0;
    for(int k=1; k < n; k *= 2, log++);
    if (n != (1 << log))
      throw new Error("FFT: Data length is not a power of 2!: "+n);
    return log; }

  protected static void transform_internal (@Approx double data[], int direction) {
	if (data.length == 0) return;    
	int n = data.length/2;
    if (n == 1) return;         // Identity operation!
    int logn = log2(n);

    /* bit reverse the input data for decimation in time algorithm */
    bitreverse(data) ;

    /* apply fft recursion */
	/* this loop executed log2(N) times */
    for (int bit = 0, dual = 1; bit < logn; bit++, dual *= 2) {
      @Approx double w_real = 1.0;
      @Approx double w_imag = 0.0;

      @Approx double theta = 2.0 * direction * Math.PI / (2.0 * (double) dual);
      @Approx double s = Math.sin(Endorsements.endorse(theta));
      @Approx double t = Math.sin(Endorsements.endorse(theta / 2.0));
      @Approx double s2 = 2.0 * t * t;

      /* a = 0 */
      for (int b = 0; b < n; b += 2 * dual) {
        int i = 2*b ;
        int j = 2*(b + dual);

        @Approx double wd_real = data[j] ;
        @Approx double wd_imag = data[j+1] ;
          
        data[j]   = data[i]   - wd_real;
        data[j+1] = data[i+1] - wd_imag;
        data[i]  += wd_real;
        data[i+1]+= wd_imag;
      }
      
      /* a = 1 .. (dual-1) */
      for (int a = 1; a < dual; a++) {
        /* trignometric recurrence for w-> exp(i theta) w */
        {
          @Approx double tmp_real = w_real - s * w_imag - s2 * w_real;
          @Approx double tmp_imag = w_imag + s * w_real - s2 * w_imag;
          w_real = tmp_real;
          w_imag = tmp_imag;
        }
        for (int b = 0; b < n; b += 2 * dual) {
          int i = 2*(b + a);
          int j = 2*(b + a + dual);

          @Approx double z1_real = data[j];
          @Approx double z1_imag = data[j+1];
              
          @Approx double wd_real = w_real * z1_real - w_imag * z1_imag;
          @Approx double wd_imag = w_real * z1_imag + w_imag * z1_real;

          data[j]   = data[i]   - wd_real;
          data[j+1] = data[i+1] - wd_imag;
          data[i]  += wd_real;
          data[i+1]+= wd_imag;
        }
      }
    }
  }


  protected static void bitreverse(@Approx double data[]) {
    /* This is the Goldrader bit-reversal algorithm */
    int n=data.length/2;
	int nm1 = n-1;
	int i=0; 
	int j=0;
    for (; i < nm1; i++) {

      //int ii = 2*i;
      int ii = i << 1;

      //int jj = 2*j;
      int jj = j << 1;

      //int k = n / 2 ;
      int k = n >> 1;

      if (i < j) {
        @Approx double tmp_real    = data[ii];
        @Approx double tmp_imag    = data[ii+1];
        data[ii]   = data[jj];
        data[ii+1] = data[jj+1];
        data[jj]   = tmp_real;
        data[jj+1] = tmp_imag; }

      while (k <= j) 
	  {
        //j = j - k ;
		j -= k;

        //k = k / 2 ; 
        k >>= 1 ; 
	  }
      j += k ;
    }
  }
}








