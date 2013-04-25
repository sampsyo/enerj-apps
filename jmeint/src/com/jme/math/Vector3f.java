/*
 * Copyright (c) 2003-2009 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jme.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.logging.Logger;

import enerj.lang.*;

/*
 * -- Added *Local methods to cut down on object creation - JS
 */

/**
 * <code>Vector3f</code> defines a Vector for a three float value tuple.
 * <code>Vector3f</code> can represent any three dimensional value, such as a
 * vertex, a normal, etc. Utility methods are also included to aid in
 * mathematical calculations.
 *
 * @author Mark Powell
 * @author Joshua Slack
 */
@Approximable
public class Vector3f implements Externalizable, Cloneable {
    private static final Logger logger = Logger.getLogger(Vector3f.class.getName());

    private static final long serialVersionUID = 1L;

	public final static Vector3f ZERO = new Vector3f(0, 0, 0);

	public final static Vector3f UNIT_X = new Vector3f(1, 0, 0);
	public final static Vector3f UNIT_Y = new Vector3f(0, 1, 0);
    public final static Vector3f UNIT_Z = new Vector3f(0, 0, 1);
    public final static Vector3f UNIT_XYZ = new Vector3f(1, 1, 1);
    
	/**
     * the x value of the vector.
     */
    public @Context float x;

    /**
     * the y value of the vector.
     */
    public @Context float y;

    /**
     * the z value of the vector.
     */
    public @Context float z;

    /**
     * Constructor instantiates a new <code>Vector3f</code> with default
     * values of (0,0,0).
     *
     */
    public Vector3f() {
        x = y = z = 0;
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> with provides
     * values.
     *
     * @param x
     *            the x value of the vector.
     * @param y
     *            the y value of the vector.
     * @param z
     *            the z value of the vector.
     */
    public Vector3f(@Context float x, @Context float y, @Context float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor instantiates a new <code>Vector3f</code> that is a copy
     * of the provided vector
     * @param copy The Vector3f to copy
     */
    public Vector3f(@Context Vector3f copy) {
        this.set(copy);
    }

    /**
     * <code>set</code> sets the x,y,z values of the vector based on passed
     * parameters.
     *
     * @param x
     *            the x value of the vector.
     * @param y
     *            the y value of the vector.
     * @param z
     *            the z value of the vector.
     * @return this vector
     */
    public @Context Vector3f set(@Context float x, @Context float y, @Context float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * <code>set</code> sets the x,y,z values of the vector by copying the
     * supplied vector.
     *
     * @param vect
     *            the vector to copy.
     * @return this vector
     */
    public @Context Vector3f set(@Context Vector3f vect) {
        this.x = vect.x;
        this.y = vect.y;
        this.z = vect.z;
        return this;
    }

    /**
     *
     * <code>add</code> adds a provided vector to this vector creating a
     * resultant vector which is returned. If the provided vector is null, null
     * is returned.
     *
     * Neither 'this' nor 'vec' are modified.
     *
     * @param vec the vector to add to this.
     * @return the resultant vector.
     */
    public @Context Vector3f add(@Context Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        return new @Context Vector3f(x + vec.x, y + vec.y, z + vec.z);
    }

    /**
     *
     * <code>add</code> adds the values of a provided vector storing the
     * values in the supplied vector.
     *
     * @param vec
     *            the vector to add to this
     * @param result
     *            the vector to store the result in
     * @return result returns the supplied result vector.
     */
    public @Context Vector3f add(@Context Vector3f vec, @Context Vector3f result) {
        result.x = x + vec.x;
        result.y = y + vec.y;
        result.z = z + vec.z;
        return result;
    }

    /**
     * <code>addLocal</code> adds a provided vector to this vector internally,
     * and returns a handle to this vector for easy chaining of calls. If the
     * provided vector is null, null is returned.
     *
     * @param vec
     *            the vector to add to this vector.
     * @return this
     */
    public @Context Vector3f addLocal(@Context Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    /**
     *
     * <code>add</code> adds the provided values to this vector, creating a
     * new vector that is then returned.
     *
     * @param addX
     *            the x value to add.
     * @param addY
     *            the y value to add.
     * @param addZ
     *            the z value to add.
     * @return the result vector.
     */
    public @Context Vector3f add(@Context float addX, @Context float addY, @Context float addZ) {
        return new @Context Vector3f(x + addX, y + addY, z + addZ);
    }

    /**
     * <code>addLocal</code> adds the provided values to this vector
     * internally, and returns a handle to this vector for easy chaining of
     * calls.
     *
     * @param addX
     *            value to add to x
     * @param addY
     *            value to add to y
     * @param addZ
     *            value to add to z
     * @return this
     */
    public @Context Vector3f addLocal(@Context float addX, @Context float addY, @Context float addZ) {
        x += addX;
        y += addY;
        z += addZ;
        return this;
    }

    /**
     *
     * <code>scaleAdd</code> multiplies this vector by a scalar then adds the
     * given Vector3f.
     *
     * @param scalar
     *            the value to multiply this vector by.
     * @param add
     *            the value to add
     */
    public void scaleAdd(@Context float scalar, @Context Vector3f add) {
        x = x * scalar + add.x;
        y = y * scalar + add.y;
        z = z * scalar + add.z;
    }

    /**
     *
     * <code>scaleAdd</code> multiplies the given vector by a scalar then adds
     * the given vector.
     *
     * @param scalar
     *            the value to multiply this vector by.
     * @param mult
     *            the value to multiply the scalar by
     * @param add
     *            the value to add
     */
    public void scaleAdd(@Context float scalar, @Context Vector3f mult, @Context Vector3f add) {
        this.x = mult.x * scalar + add.x;
        this.y = mult.y * scalar + add.y;
        this.z = mult.z * scalar + add.z;
    }

    /**
     *
     * <code>dot</code> calculates the dot product of this vector with a
     * provided vector. If the provided vector is null, 0 is returned.
     *
     * @param vec
     *            the vector to dot with this vector.
     * @return the resultant dot product of this vector and a given vector.
     */
    public @Context float dot(@Context Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, 0 returned.");
            return 0;
        }
        return x * vec.x + y * vec.y + z * vec.z;
    }

    /**
     * Returns a new vector which is the cross product of this vector with
     * the specified vector.
     * <P>
     * Neither 'this' nor v are modified.  The starting value of 'result'
     * </P>
     *
     * @param v the vector to take the cross product of with this.
     * @return the cross product vector.
     */
    public @Context Vector3f cross(@Context Vector3f v) {
        return cross(v, null);
    }

    /**
     * <code>cross</code> calculates the cross product of this vector with a
     * parameter vector v.  The result is stored in <code>result</code>
     * <P>
     * Neither 'this' nor v are modified.  The starting value of 'result'
     * (if any) is ignored.
     * </P>
     *
     * @param v the vector to take the cross product of with this.
     * @param result the vector to store the cross product result.
     * @return result, after recieving the cross product vector.
     */
    public @Context Vector3f cross(@Context Vector3f v, @Context Vector3f result) {
        return cross(v.x, v.y, v.z, result);
    }

    /**
     * <code>cross</code> calculates the cross product of this vector with a
     * Vector comprised of the specified other* elements.
     * The result is stored in <code>result</code>, without modifying either
     * 'this' or the 'other*' values.
     *
     * @param otherX
     *            x component of the vector to take the cross product of with this.
     * @param otherY
     *            y component of the vector to take the cross product of with this.
     * @param otherZ
     *            z component of the vector to take the cross product of with this.
     * @param result the vector to store the cross product result.
     * @return result, after recieving the cross product vector.
     */
    public @Context Vector3f cross(@Context float otherX, @Context float otherY, @Context float otherZ, @Context Vector3f result) {
        if (result == null) result = new @Context Vector3f();
        @Context float resX = ((y * otherZ) - (z * otherY)); 
        @Context float resY = ((z * otherX) - (x * otherZ));
        @Context float resZ = ((x * otherY) - (y * otherX));
        result.set(resX, resY, resZ);
        return result;
    }

    /**
     * <code>crossLocal</code> calculates the cross product of this vector
     * with a parameter vector v.
     *
     * @param v
     *            the vector to take the cross product of with this.
     * @return this.
     */
    public @Context Vector3f crossLocal(@Context Vector3f v) {
        return crossLocal(v.x, v.y, v.z);
    }

    /**
     * <code>crossLocal</code> calculates the cross product of this vector
     * with a parameter vector v.
     *
     * @param otherX
     *            x component of the vector to take the cross product of with this.
     * @param otherY
     *            y component of the vector to take the cross product of with this.
     * @param otherZ
     *            z component of the vector to take the cross product of with this.
     * @return this.
     */
    public @Context Vector3f crossLocal(@Context float otherX, @Context float otherY, @Context float otherZ) {
        @Context float tempx = ( y * otherZ ) - ( z * otherY );
        @Context float tempy = ( z * otherX ) - ( x * otherZ );
        z = (x * otherY) - (y * otherX);
        x = tempx;
        y = tempy;
        return this;
    }

    /**
     * <code>length</code> calculates the magnitude of this vector.
     *
     * @return the length or magnitude of the vector.
     */
    public float length() {
        return FastMath.sqrt(Endorsements.endorse(lengthSquared())); //EnerJ TODO
    }
	public @Approx float length_APPROX() {
		return (@Approx float)ApproxMath.sqrt(lengthSquared());
	}

    /**
     * <code>lengthSquared</code> calculates the squared value of the
     * magnitude of the vector.
     *
     * @return the magnitude squared of the vector.
     */
    public @Context float lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * <code>distanceSquared</code> calculates the distance squared between
     * this vector and vector v.
     *
     * @param v the second vector to determine the distance squared.
     * @return the distance squared between the two vectors.
     */
    public @Context float distanceSquared(@Context Vector3f v) {
        @Context double dx = x - v.x;
        @Context double dy = y - v.y;
        @Context double dz = z - v.z;
        return (@Context float) (dx * dx + dy * dy + dz * dz);
    }

    /**
     * <code>distance</code> calculates the distance between this vector and
     * vector v.
     *
     * @param v the second vector to determine the distance.
     * @return the distance between the two vectors.
     */
    public float distance(@Context Vector3f v) {
        return FastMath.sqrt(Endorsements.endorse(distanceSquared(v))); // EnerJ TODO
    }
	public @Approx float distance_APPROX(@Context Vector3f v) {
        return (@Approx float)ApproxMath.sqrt(distanceSquared(v));
    }

    /**
     * <code>mult</code> multiplies this vector by a scalar. The resultant
     * vector is returned.
     * "this" is not modified.
     *
     * @param scalar the value to multiply this vector by.
     * @return the new vector.
     */
    public @Context Vector3f mult(@Context float scalar) {
        return new @Context Vector3f(x * scalar, y * scalar, z * scalar);
    }

    /**
     *
     * <code>mult</code> multiplies this vector by a scalar. The resultant
     * vector is supplied as the second parameter and returned.
     * "this" is not modified.
     *
     * @param scalar the scalar to multiply this vector by.
     * @param product the product to store the result in.
     * @return product
     */
    public @Context Vector3f mult(@Context float scalar, @Context Vector3f product) {
        if (null == product) {
            product = new @Context Vector3f();
        }

        product.x = x * scalar;
        product.y = y * scalar;
        product.z = z * scalar;
        return product;
    }

    /**
     * <code>multLocal</code> multiplies this vector by a scalar internally,
     * and returns a handle to this vector for easy chaining of calls.
     *
     * @param scalar
     *            the value to multiply this vector by.
     * @return this
     */
    public @Context Vector3f multLocal(@Context float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    /**
     * <code>multLocal</code> multiplies a provided vector to this vector
     * internally, and returns a handle to this vector for easy chaining of
     * calls. If the provided vector is null, null is returned.
     * The provided 'vec' is not modified.
     *
     * @param vec the vector to mult to this vector.
     * @return this
     */
    public @Context Vector3f multLocal(@Context Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
        return this;
    }


    /**
     * Returns a new Vector instance comprised of elements which are the
     * product of the corresponding vector elements.
     * (N.b. this is not a cross product).
     * <P>
     * Neither 'this' nor 'vec' are modified.
     * </P>
     *
     * @param vec the vector to mult to this vector.
     */
    public @Context Vector3f mult(@Context Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        return mult(vec, null);
    }

    /**
     * Multiplies a provided 'vec' vector with this vector.
     * If the specified 'store' is null, then a new Vector instance is returned.
     * Otherwise, 'store' with replaced values will be returned, to facilitate
     * chaining.
     * </P> <P>
     * 'This' is not modified; and the starting value of 'store' (if any) is
     * ignored (and over-written).
     * <P>
     * The resultant Vector is comprised of elements which are the
     * product of the corresponding vector elements.
     * (N.b. this is not a cross product).
     * </P>
     *
     * @param vec the vector to mult to this vector.
     * @param store result vector (null to create a new vector)
     * @return 'store', or a new Vector3f
     */
    public @Context Vector3f mult(@Context Vector3f vec, @Context Vector3f store) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        if (store == null) store = new @Context Vector3f();
        return store.set(x * vec.x, y * vec.y, z * vec.z);
    }


    /**
     * <code>divide</code> divides the values of this vector by a scalar and
     * returns the result. The values of this vector remain untouched.
     *
     * @param scalar the value to divide this vectors attributes by.
     * @return the result <code>Vector</code>.
     */
    public @Context Vector3f divide(@Context float scalar) {
        scalar = 1f/scalar;
        return new @Context Vector3f(x * scalar, y * scalar, z * scalar);
    }

    /**
     * <code>divideLocal</code> divides this vector by a scalar internally,
     * and returns a handle to this vector for easy chaining of calls. Dividing
     * by zero will result in an exception.
     *
     * @param scalar
     *            the value to divides this vector by.
     * @return this
     */
    public @Context Vector3f divideLocal(@Context float scalar) {
        scalar = 1f/scalar;
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }


    /**
     * <code>divide</code> divides the values of this vector by a scalar and
     * returns the result. The values of this vector remain untouched.
     *
     * @param scalar
     *            the value to divide this vectors attributes by.
     * @return the result <code>Vector</code>.
     */
    public @Context Vector3f divide(@Context Vector3f scalar) {
        return new @Context Vector3f(x / scalar.x, y / scalar.y, z / scalar.z);
    }

    /**
     * <code>divideLocal</code> divides this vector by a scalar internally,
     * and returns a handle to this vector for easy chaining of calls. Dividing
     * by zero will result in an exception.
     *
     * @param scalar
     *            the value to divides this vector by.
     * @return this
     */
    public @Context Vector3f divideLocal(@Context Vector3f scalar) {
        x /= scalar.x;
        y /= scalar.y;
        z /= scalar.z;
        return this;
    }

    /**
     *
     * <code>negate</code> returns the negative of this vector. All values are
     * negated and set to a new vector.
     *
     * @return the negated vector.
     */
    public @Context Vector3f negate() {
        return new @Context Vector3f(-x, -y, -z);
    }

    /**
     *
     * <code>negateLocal</code> negates the internal values of this vector.
     *
     * @return this.
     */
    public @Context Vector3f negateLocal() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     *
     * <code>subtract</code> subtracts the values of a given vector from those
     * of this vector creating a new vector object. If the provided vector is
     * null, null is returned.
     *
     * @param vec
     *            the vector to subtract from this vector.
     * @return the result vector.
     */
    public @Context Vector3f subtract(@Context Vector3f vec) {
        return new @Context Vector3f(x - vec.x, y - vec.y, z - vec.z);
    }

    /**
     * <code>subtractLocal</code> subtracts a provided vector to this vector
     * internally, and returns a handle to this vector for easy chaining of
     * calls. If the provided vector is null, null is returned.
     *
     * @param vec
     *            the vector to subtract
     * @return this
     */
    public @Context Vector3f subtractLocal(@Context Vector3f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }

    /**
     *
     * <code>subtract</code>
     *
     * @param vec
     *            the vector to subtract from this
     * @param result
     *            the vector to store the result in
     * @return result
     */
    public @Context Vector3f subtract(@Context Vector3f vec, @Context Vector3f result) {
        if(result == null) {
            result = new @Context Vector3f();
        }
        result.x = x - vec.x;
        result.y = y - vec.y;
        result.z = z - vec.z;
        return result;
    }

    /**
     *
     * <code>subtract</code> subtracts the provided values from this vector,
     * creating a new vector that is then returned.
     *
     * @param subtractX
     *            the x value to subtract.
     * @param subtractY
     *            the y value to subtract.
     * @param subtractZ
     *            the z value to subtract.
     * @return the result vector.
     */
    public @Context Vector3f subtract(@Context float subtractX, @Context float subtractY, @Context float subtractZ) {
        return new @Context Vector3f(x - subtractX, y - subtractY, z - subtractZ);
    }

    /**
     * <code>subtractLocal</code> subtracts the provided values from this vector
     * internally, and returns a handle to this vector for easy chaining of
     * calls.
     *
     * @param subtractX
     *            the x value to subtract.
     * @param subtractY
     *            the y value to subtract.
     * @param subtractZ
     *            the z value to subtract.
     * @return this
     */
    public @Context Vector3f subtractLocal(@Context float subtractX, @Context float subtractY, @Context float subtractZ) {
        x -= subtractX;
        y -= subtractY;
        z -= subtractZ;
        return this;
    }

    /**
     * <code>normalize</code> returns the unit vector of this vector.
     *
     * @return unit vector of this vector.
     */
    public @Context Vector3f normalize() {
        @Context float length = length();
        if (Endorsements.endorse(length != 0)) {
            return divide(length);
        }
        
        return divide(1);        
    }

    /**
     * <code>normalizeLocal</code> makes this vector into a unit vector of
     * itself.
     *
     * @return this.
     */
    public @Context Vector3f normalizeLocal() {
        @Context float length = length();
        if (Endorsements.endorse(length != 0)) {
            return divideLocal(length);
        }
        
        return this;        
    }

    /**
     * <code>zero</code> resets this vector's data to zero internally.
     */
    public void zero() {
        x = y = z = 0;
    }

    /**
     * <code>angleBetween</code> returns (in radians) the angle between two vectors.
     * It is assumed that both this vector and the given vector are unit vectors (iow, normalized).
     * 
     * @param otherVector a unit vector to find the angle against
     * @return the angle in radians.
     */
    public @Context float angleBetween(@Context Vector3f otherVector) {
        @Context float dotProduct = dot(otherVector);
        @Context float angle = FastMath.acos(Endorsements.endorse(dotProduct)); // EnerJ TODO
        return angle;
    }
    
    /**
     * Sets this vector to the interpolation by changeAmnt from this to the finalVec
     * this=(1-changeAmnt)*this + changeAmnt * finalVec
     * @param finalVec The final vector to interpolate towards
     * @param changeAmnt An amount between 0.0 - 1.0 representing a precentage
     *  change from this towards finalVec
     */
    public void interpolate(@Context Vector3f finalVec, @Context float changeAmnt) {
        this.x=(1-changeAmnt)*this.x + changeAmnt*finalVec.x;
        this.y=(1-changeAmnt)*this.y + changeAmnt*finalVec.y;
        this.z=(1-changeAmnt)*this.z + changeAmnt*finalVec.z;
    }

    /**
     * Sets this vector to the interpolation by changeAmnt from beginVec to finalVec
     * this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
     * @param beginVec the beging vector (changeAmnt=0)
     * @param finalVec The final vector to interpolate towards
     * @param changeAmnt An amount between 0.0 - 1.0 representing a precentage
     *  change from beginVec towards finalVec
     */
    public void interpolate(@Context Vector3f beginVec, @Context Vector3f finalVec, @Context float changeAmnt) {
        this.x=(1-changeAmnt)*beginVec.x + changeAmnt*finalVec.x;
        this.y=(1-changeAmnt)*beginVec.y + changeAmnt*finalVec.y;
        this.z=(1-changeAmnt)*beginVec.z + changeAmnt*finalVec.z;
    }

    /**
     * Check a vector... if it is null or its floats are NaN or infinite,
     * return false.  Else return true.
     * @param vector the vector to check
     * @return true or false as stated above.
     */
    public static boolean isValidVector(Vector3f vector) {
      if (vector == null) return false;
      if (Float.isNaN(vector.x) ||
          Float.isNaN(vector.y) ||
          Float.isNaN(vector.z)) return false;
      if (Float.isInfinite(vector.x) ||
          Float.isInfinite(vector.y) ||
          Float.isInfinite(vector.z)) return false;
      return true;
    }

    public static void generateOrthonormalBasis(Vector3f u, Vector3f v, Vector3f w) {
        w.normalizeLocal();
        generateComplementBasis(u, v, w);
    }

    public static void generateComplementBasis(Vector3f u, Vector3f v,
            Vector3f w) {
        float fInvLength;

        if (FastMath.abs(w.x) >= FastMath.abs(w.y)) {
            // w.x or w.z is the largest magnitude component, swap them
            fInvLength = FastMath.invSqrt(w.x * w.x + w.z * w.z);
            u.x = -w.z * fInvLength;
            u.y = 0.0f;
            u.z = +w.x * fInvLength;
            v.x = w.y * u.z;
            v.y = w.z * u.x - w.x * u.z;
            v.z = -w.y * u.x;
        } else {
            // w.y or w.z is the largest magnitude component, swap them
            fInvLength = FastMath.invSqrt(w.y * w.y + w.z * w.z);
            u.x = 0.0f;
            u.y = +w.z * fInvLength;
            u.z = -w.y * fInvLength;
            v.x = w.y * u.z - w.z * u.y;
            v.y = -w.x * u.z;
            v.z = w.x * u.y;
        }
    }

    @Override
    public Vector3f clone() {
        try {
            return (Vector3f) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }

    /**
     * Saves this Vector3f into the given float[] object.
     * 
     * @param floats
     *            The float[] to take this Vector3f. If null, a new float[3] is
     *            created.
     * @return The array, with X, Y, Z float values in that order
     */
    public @Context float[] toArray(@Context float[] floats) {
        if (floats == null) {
            floats = new @Context float[3];
        }
        floats[0] = x;
        floats[1] = y;
        floats[2] = z;
        return floats;
    }

    /**
     * are these two vectors the same? they are is they both have the same x,y,
     * and z values.
     *
     * @param o
     *            the object to compare for equality
     * @return true if they are equal
     */
    public boolean equals(Object o) {
        if (!(o instanceof Vector3f)) { return false; }

        if (this == o) { return true; }

        Vector3f comp = (Vector3f) o;
        if (Float.compare(Endorsements.endorse(x),comp.x) != 0) return false;
        if (Float.compare(Endorsements.endorse(y),comp.y) != 0) return false;
        if (Float.compare(Endorsements.endorse(z),comp.z) != 0) return false;
        return true;
    }

    /**
     * <code>hashCode</code> returns a unique code for this vector object based
     * on it's values. If two vectors are logically equivalent, they will return
     * the same hash code value.
     * @return the hash code value of this vector.
     */
    public int hashCode() {
        int hash = 37;
        hash += 37 * hash + Float.floatToIntBits(Endorsements.endorse(x));
        hash += 37 * hash + Float.floatToIntBits(Endorsements.endorse(y));
        hash += 37 * hash + Float.floatToIntBits(Endorsements.endorse(z));
        return hash;
    }

    /**
     * <code>toString</code> returns the string representation of this vector.
     * The format is: <code>(xx.x..., yy.y..., zz.z...)</code>
     * <p>
     * If you want to display a class name, then use
     * Vector3f.class.getName() or getClass().getName().
     * </p>
     *
     * @return the string representation of this vector.
     */
    public String toString() {
        return "(" + Endorsements.endorse(x) + ", " + Endorsements.endorse(y) + ", " + Endorsements.endorse(z) + ')';
    }


    public Class<? extends Vector3f> getClassTag() {
        return this.getClass();
    }

    public @Context float getX() {
        return x;
    }

    public void setX(@Context float x) {
        this.x = x;
    }

    public @Context float getY() {
        return y;
    }

    public void setY(@Context float y) {
        this.y = y;
    }

    public @Context float getZ() {
        return z;
    }

    public void setZ(@Context float z) {
        this.z = z;
    }
    
    /**
     * @param index
     * @return x value if index == 0, y value if index == 1 or z value if index ==
     *         2
     * @throws IllegalArgumentException
     *             if index is not one of 0, 1, 2.
     */
    public @Context float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException("index must be either 0, 1 or 2");
    }
    
    /**
     * @param index
     *            which field index in this vector to set.
     * @param value
     *            to set to one of x, y or z.
     * @throws IllegalArgumentException
     *             if index is not one of 0, 1, 2.
     */
    public void set(int index, @Context float value) {
        switch (index) {
            case 0:
                x = value;
                return;
            case 1:
                y = value;
                return;
            case 2:
                z = value;
                return;
        }
        throw new IllegalArgumentException("index must be either 0, 1 or 2");
    }

    /**
     * Used with serialization. Not to be called manually.
     * 
     * @param in
     *            ObjectInput
     * @throws IOException
     * @throws ClassNotFoundException
     * @see java.io.Externalizable
     */
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
	/*
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
*/
    }
    
    public void writeExternal(ObjectOutput out) throws IOException {
/*
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
*/
    }

}
