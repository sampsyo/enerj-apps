package jnt.scimark2;

import enerj.lang.*;

public class SOR
{

	public static final void execute(@Approx double omega,
	        @Approx double[][] G,
	        int num_iterations)
	{
		int M = G.length;
		int N = G[0].length;

		@Approx double omega_over_four = omega * 0.25;
		@Approx double one_minus_omega = 1.0 - omega;

		// update interior points
		//
		int Mm1 = M-1;
		int Nm1 = N-1; 
		for (int p=0; p<num_iterations; p++)
		{
			for (int i=1; i<Mm1; i++)
			{
				@Approx double[] Gi = G[i];
				@Approx double[] Gim1 = G[i-1];
				@Approx double[] Gip1 = G[i+1];
				for (int j=1; j<Nm1; j++)
					Gi[j] = omega_over_four * (Gim1[j] + Gip1[j] + Gi[j-1] 
								+ Gi[j+1]) + one_minus_omega * Gi[j];
			}
		}
	}
}
			
