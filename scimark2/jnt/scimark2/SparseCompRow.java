package jnt.scimark2;

import enerj.lang.*;

public class SparseCompRow
{
	/* multiple iterations used to make kernel have roughly
		same granulairty as other Scimark kernels. */

	/* computes  a matrix-vector multiply with a sparse matrix
		held in compress-row format.  If the size of the matrix
		in MxN with nz nonzeros, then the val[] is the nz nonzeros,
		with its ith entry in column col[i].  The integer vector row[]
		is of size M+1 and row[i] points to the begining of the
		ith row in col[].  
	*/

	public static void matmult( @Approx double y[], @Approx double val[], int row[],
		int col[], @Approx double x[], int NUM_ITERATIONS)
	{
		int M = row.length - 1;

		for (int reps=0; reps<NUM_ITERATIONS; reps++)
		{
		
			for (int r=0; r<M; r++)
			{
				@Approx double sum = 0.0; 
				int rowR = row[r];
				int rowRp1 = row[r+1];
				for (int i=rowR; i<rowRp1; i++)
					sum += x[ col[i] ] * val[i];
				y[r] = sum;
			}
		}
	}

}
