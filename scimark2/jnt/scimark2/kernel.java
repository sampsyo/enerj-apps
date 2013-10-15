package jnt.scimark2;

import enerj.lang.*;

public class kernel
{
	// each measurement returns approx Mflops


	public static double measureFFT(int N, double mintime, Random R)
	{
		// initialize FFT data as complex (N real/img pairs)

		@Approx double x[] = RandomVector(2*N, R);
		@Approx double oldx[] = NewVectorCopy(x);
		long cycles = 100;

		for (int i=0; i<cycles; i++)
		{
			FFT.transform(x);	// forward transform
			FFT.inverse(x);		// backward transform
		}
		// approx Mflops

		//final double EPS = 1.0e-10;
		//if ( FFT.test(x) / N > EPS )
		//	return 0.0;
		System.out.print("FFT vector: ");
		for (int i = 0; i < N; ++i) {
		    System.out.print(Endorsements.endorse(x[i]) + " ");
		}
		System.out.println("");
		
		return 0.0;
	}


	public static double measureSOR(int N, double min_time, Random R)
	{
		@Approx double G[][] = RandomMatrix(N, N, R);

		int cycles=100;
		SOR.execute(1.25, G, cycles);
		// approx Mflops
		
		System.out.print("SOR values: ");
		for (int i = 0; i < N; ++i) {
		    for (int j = 0; j < N; ++j) {
		        System.out.print(Endorsements.endorse(G[i][j]) + " ");
		    }
		}
		System.out.println("");
		
		return 0.0;
	}

	public static double measureMonteCarlo(double min_time, Random R)
	{
		int cycles=1492;
		@Approx double out = 0.0;
		out = MonteCarlo.integrate(cycles);
		
		System.out.println("MonteCarlo out: " + Endorsements.endorse(out));
		
		// approx Mflops
		return 0.0;
	}


	public static double measureSparseMatmult(int N, int nz, 
			double min_time, Random R)
	{
		// initialize vector multipliers and storage for result
		// y = A*y;

		@Approx double x[] = RandomVector(N, R);
		@Approx double y[] = new @Approx double[N];

		// initialize square sparse matrix
		//
		// for this test, we create a sparse matrix wit M/nz nonzeros
		// per row, with spaced-out evenly between the begining of the
		// row to the main diagonal.  Thus, the resulting pattern looks
		// like
		//             +-----------------+
		//             +*                +
		//             +***              +
		//             +* * *            +
		//             +** *  *          +
		//             +**  *   *        +
		//             +* *   *   *      +
		//             +*  *   *    *    +
		//             +*   *    *    *  + 
		//             +-----------------+
		//
		// (as best reproducible with integer artihmetic)
		// Note that the first nr rows will have elements past
		// the diagonal.

		int nr = nz/N; 		// average number of nonzeros per row
		int anz = nr *N;   // _actual_ number of nonzeros

			
		@Approx double val[] = RandomVector(anz, R);
		int col[] = new int[anz];
		int row[] = new int[N+1];

		row[0] = 0;	
		for (int r=0; r<N; r++)
		{
			// initialize elements for row r

			int rowr = row[r];
			row[r+1] = rowr + nr;
			int step = r/ nr;
			if (step < 1) step = 1;   // take at least unit steps


			for (int i=0; i<nr; i++)
				col[rowr+i] = i*step;
				
		}

		int cycles=100;
    	SparseCompRow.matmult(y, val, row, col, x, cycles);
		
		System.out.print("SparseMatMult vector: ");
		for (int i = 0; i < N; ++i) {
		    System.out.print(Endorsements.endorse(y[i]) + " ");
		}
		System.out.println("");
		
		// approx Mflops
		return 0.0;
	}


	public static double measureLU(int N, double min_time, Random R)
	{
		// compute approx Mlfops, or O if LU yields large errors

		@Approx double A[][] = RandomMatrix(N, N,  R);
		@Approx double lu[][] = new @Approx double[N][N];
		int pivot[] = new int[N];

		int cycles=100;
		for (int i=0; i<cycles; i++)
		{
			CopyMatrix(lu, A);
			LU.factor((@Approx double [][])lu, pivot);
		}


		// verify that LU is correct
		@Approx double b[] = RandomVector(N, R);
		@Approx double x[] = NewVectorCopy(b);

		LU.solve((@Approx double [][])lu, pivot, x);

		// final double EPS = 1.0e-12;
		
		@Approx double[] y = matvec(A, x);
		System.out.print("LU vector: ");
		for (int i = 0; i < N; ++i) {
		    System.out.print(Endorsements.endorse(y[i]) + " ");
		}
		System.out.println("");
		//if ( normabs(b, matvec(A,x)) / N > EPS )
		//	return 0.0;


		// else return approx Mflops
		//
		return 0.0;
	}


  private static @Approx double[] NewVectorCopy(@Approx double x[])
  {
		int N = x.length;

		@Approx double y[] = new @Approx double[N];
		for (int i=0; i<N; i++)
			y[i] = x[i];

		return y;
  }
	
  private static void CopyVector(@Approx double B[], @Approx double A[])
  {
		int N = A.length;

		for (int i=0; i<N; i++)
			B[i] = A[i];
  }


  private static @Approx double normabs(@Approx double x[], @Approx double y[])
  {
		int N = x.length;
		@Approx double sum = 0.0;

		for (int i=0; i<N; i++) {
			sum += ApproxMath.abs(x[i]-y[i]);
		}

		return sum;
  }

  private static void CopyMatrix(@Approx double B[][], @Approx double A[][])
  {
        int M = A.length;
        int N = A[0].length;

		int remainder = N & 3;		 // N mod 4;

        for (int i=0; i<M; i++)
        {
            @Approx double Bi[] = B[i];
            @Approx double Ai[] = A[i];
			for (int j=0; j<remainder; j++)
                Bi[j] = Ai[j];
            for (int j=remainder; j<N; j+=4)
			{
				Bi[j] = Ai[j];
				Bi[j+1] = Ai[j+1];
				Bi[j+2] = Ai[j+2];
				Bi[j+3] = Ai[j+3];
			}
		}
  }

  private static @Approx double[][] RandomMatrix(int M, int N, Random R)
  {
  		@Approx double A[][] = new @Approx double[M][N];

        for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
            	A[i][j] = R.nextDouble();
		return A;
	}

	private static @Approx double[] RandomVector(int N, Random R)
	{
		@Approx double A[] = new @Approx double[N];

		for (int i=0; i<N; i++)
			A[i] = R.nextDouble();
		return A;
	}

	private static @Approx double[] matvec(@Approx double A[][], @Approx double x[])
	{
		int N = x.length;
		@Approx double y[] = new @Approx double[N];

		matvec(A, x, y);

		return y;
	}

	private static void matvec(@Approx double A[][], @Approx double x[], @Approx double y[])
	{
		int M = A.length;
		int N = A[0].length;

		for (int i=0; i<M; i++)
		{
			@Approx double sum = 0.0;
			@Approx double Ai[] = A[i];
			for (int j=0; j<N; j++) {
			    
				sum += Ai[j] * x[j];
			}

			y[i] = sum;
		}
	}

}
