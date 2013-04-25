import com.jme.intersection.Intersection;
import com.jme.math.Vector3f;

import java.util.Random;
import enerj.lang.*;

public class JMEIntTest {
	public static Random rand;
	public static @Approx Vector3f randvec() {
		return new @Approx Vector3f(
			rand.nextFloat(), rand.nextFloat(), rand.nextFloat()
		);
	}
    public static void main(String[] argv) {
	
		// Use a constant seed so we operate deterministically.
		rand = new Random(1234); 
		
		for (int i = 0; i <= 100; ++i) {
			@Approx boolean isec = Intersection.intersection(
				randvec(), randvec(), randvec(),
				randvec(), randvec(), randvec()
			);
			if (Endorsements.endorse(isec)) {
				System.out.print("1 ");
			} else {
				System.out.print("0 ");
			}
		}
		
		System.out.println("");
		
    }
}
