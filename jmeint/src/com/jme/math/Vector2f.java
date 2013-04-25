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

/**
 * <code>Vector2f</code> defines a Vector for a two float value vector.
 * 
 * @author Mark Powell
 * @author Joshua Slack
 */
@Approximable
public class Vector2f implements Externalizable, Cloneable {
    private static final Logger logger = Logger.getLogger(Vector2f.class.getName());

    private static final long serialVersionUID = 1L;
    /**
     * the x value of the vector.
     */
    public @Context float x;
    /**
     * the y value of the vector.
     */
    public @Context float y;

    /**
     * Creates a Vector2f with the given initial x and y values.
     * 
     * @param x
     *            The x value of this Vector2f.
     * @param y
     *            The y value of this Vector2f.
     */
    public Vector2f(@Context float x, @Context float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a Vector2f with x and y set to 0. Equivalent to Vector2f(0,0).
     */
    public Vector2f() {
        x = y = 0;
    }

    /**
     * Creates a new Vector2f that contains the passed vector's information
     * 
     * @param vector2f
     *            The vector to copy
     */
    public Vector2f(@Context Vector2f vector2f) {
        this.x = vector2f.x;
        this.y = vector2f.y;
    }

    /**
     * set the x and y values of the vector
     * 
     * @param x
     *            the x value of the vector.
     * @param y
     *            the y value of the vector.
     * @return this vector
     */
    public @Context Vector2f set(@Context float x, @Context float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * set the x and y values of the vector from another vector
     * 
     * @param vec
     *            the vector to copy from
     * @return this vector
     */
    public @Context Vector2f set(@Context Vector2f vec) {
        this.x = vec.x;
        this.y = vec.y;
        return this;
    }

    /**
     * <code>add</code> adds a provided vector to this vector creating a
     * resultant vector which is returned. If the provided vector is null, null
     * is returned.
     * 
     * @param vec
     *            the vector to add to this.
     * @return the resultant vector.
     */
    public @Context Vector2f add(@Context Vector2f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        return new @Context Vector2f(x + vec.x, y + vec.y);
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
    public @Context Vector2f addLocal(@Context Vector2f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x += vec.x;
        y += vec.y;
        return this;
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
     * @return this
     */
    public @Context Vector2f addLocal(@Context float addX, @Context float addY) {
        x += addX;
        y += addY;
        return this;
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
*/
    }


    /**
     * <code>add</code> adds this vector by <code>vec</code> and stores the
     * result in <code>result</code>.
     * 
     * @param vec
     *            The vector to add.
     * @param result
     *            The vector to store the result in.
     * @return The result vector, after adding.
     */
    public @Context Vector2f add(@Context Vector2f vec, @Context Vector2f result) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        if (result == null)
            result = new @Context Vector2f();
        result.x = x + vec.x;
        result.y = y + vec.y;
        return result;
    }

    /**
     * <code>dot</code> calculates the dot product of this vector with a
     * provided vector. If the provided vector is null, 0 is returned.
     * 
     * @param vec
     *            the vector to dot with this vector.
     * @return the resultant dot product of this vector and a given vector.
     */
    public @Context float dot(@Context Vector2f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, 0 returned.");
            return 0;
        }
        return x * vec.x + y * vec.y;
    }

    /**
     * <code>cross</code> calculates the cross product of this vector with a
     * parameter vector v.
     * 
     * @param v
     *            the vector to take the cross product of with this.
     * @return the cross product vector.
     */
    public @Context Vector3f cross(@Context Vector2f v) {
        return new @Context Vector3f(0, 0, determinant(v));
    }

    public @Context float determinant(@Context Vector2f v) {
        return (x * v.y) - (y * v.x);
    }
    
    /**
     * Sets this vector to the interpolation by changeAmnt from this to the
     * finalVec this=(1-changeAmnt)*this + changeAmnt * finalVec
     * 
     * @param finalVec
     *            The final vector to interpolate towards
     * @param changeAmnt
     *            An amount between 0.0 - 1.0 representing a percentage change
     *            from this towards finalVec
     */
    public void interpolate(@Context Vector2f finalVec, @Context float changeAmnt) {
        this.x = (1 - changeAmnt) * this.x + changeAmnt * finalVec.x;
        this.y = (1 - changeAmnt) * this.y + changeAmnt * finalVec.y;
    }

    /**
     * Sets this vector to the interpolation by changeAmnt from beginVec to
     * finalVec this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
     * 
     * @param beginVec
     *            The begining vector (delta=0)
     * @param finalVec
     *            The final vector to interpolate towards (delta=1)
     * @param changeAmnt
     *            An amount between 0.0 - 1.0 representing a precentage change
     *            from beginVec towards finalVec
     */
    public void interpolate(@Context Vector2f beginVec, @Context Vector2f finalVec,
            @Context float changeAmnt) {
        this.x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
        this.y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
    }

    /**
     * Check a vector... if it is null or its floats are NaN or infinite, return
     * false. Else return true.
     * 
     * @param vector
     *            the vector to check
     * @return true or false as stated above.
     */
    public static boolean isValidVector(@Context Vector2f vector) {
      if (vector == null) return false;
      if (Float.isNaN(Endorsements.endorse(vector.x)) ||
          Float.isNaN(Endorsements.endorse(vector.y))) return false;
      if (Float.isInfinite(Endorsements.endorse(vector.x)) ||
          Float.isInfinite(Endorsements.endorse(vector.y))) return false;
      return true;
    }

    /**
     * <code>length</code> calculates the magnitude of this vector.
     * 
     * @return the length or magnitude of the vector.
     */
    public float length() {
        return FastMath.sqrt(Endorsements.endorse(lengthSquared())); // EnerJ TODO
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
        return x * x + y * y;
    }

    /**
     * <code>distanceSquared</code> calculates the distance squared between
     * this vector and vector v.
     *
     * @param v the second vector to determine the distance squared.
     * @return the distance squared between the two vectors.
     */
    public @Context float distanceSquared(@Context Vector2f v) {
        @Context double dx = x - v.x;
        @Context double dy = y - v.y;
        return (@Context float) (dx * dx + dy * dy);
    }

    /**
     * <code>distanceSquared</code> calculates the distance squared between
     * this vector and vector v.
     *
     * @param v the second vector to determine the distance squared.
     * @return the distance squared between the two vectors.
     */
    public @Context float distanceSquared(@Context float otherX, @Context float otherY) {
        @Context double dx = x - otherX;
        @Context double dy = y - otherY;
        return (@Context float) (dx * dx + dy * dy);
    }

    /**
     * <code>distance</code> calculates the distance between this vector and
     * vector v.
     *
     * @param v the second vector to determine the distance.
     * @return the distance between the two vectors.
     */
    public float distance(@Context Vector2f v) {
        return FastMath.sqrt(Endorsements.endorse(distanceSquared(v))); // EnerJ TODO
    }
	public @Approx float distance_APPROX(@Context Vector2f v) {
        return (@Approx float)ApproxMath.sqrt(distanceSquared(v));
    }

    /**
     * <code>mult</code> multiplies this vector by a scalar. The resultant
     * vector is returned.
     * 
     * @param scalar
     *            the value to multiply this vector by.
     * @return the new vector.
     */
    public @Context Vector2f mult(@Context float scalar) {
        return new @Context Vector2f(x * scalar, y * scalar);
    }

    /**
     * <code>multLocal</code> multiplies this vector by a scalar internally,
     * and returns a handle to this vector for easy chaining of calls.
     * 
     * @param scalar
     *            the value to multiply this vector by.
     * @return this
     */
    public @Context Vector2f multLocal(@Context float scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    /**
     * <code>multLocal</code> multiplies a provided vector to this vector
     * internally, and returns a handle to this vector for easy chaining of
     * calls. If the provided vector is null, null is returned.
     * 
     * @param vec
     *            the vector to mult to this vector.
     * @return this
     */
    public @Context Vector2f multLocal(@Context Vector2f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x *= vec.x;
        y *= vec.y;
        return this;
    }

    /**
     * Multiplies this Vector2f's x and y by the scalar and stores the result in
     * product. The result is returned for chaining. Similar to
     * product=this*scalar;
     * 
     * @param scalar
     *            The scalar to multiply by.
     * @param product
     *            The vector2f to store the result in.
     * @return product, after multiplication.
     */
    public @Context Vector2f mult(@Context float scalar, @Context Vector2f product) {
        if (null == product) {
            product = new @Context Vector2f();
        }

        product.x = x * scalar;
        product.y = y * scalar;
        return product;
    }

    /**
     * <code>divide</code> divides the values of this vector by a scalar and
     * returns the result. The values of this vector remain untouched.
     * 
     * @param scalar
     *            the value to divide this vectors attributes by.
     * @return the result <code>Vector</code>.
     */
    public @Context Vector2f divide(@Context float scalar) {
        return new @Context Vector2f(x / scalar, y / scalar);
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
    public @Context Vector2f divideLocal(@Context float scalar) {
        x /= scalar;
        y /= scalar;
        return this;
    }

    /**
     * <code>negate</code> returns the negative of this vector. All values are
     * negated and set to a new vector.
     * 
     * @return the negated vector.
     */
    public @Context Vector2f negate() {
        return new @Context Vector2f(-x, -y);
    }

    /**
     * <code>negateLocal</code> negates the internal values of this vector.
     * 
     * @return this.
     */
    public @Context Vector2f negateLocal() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * <code>subtract</code> subtracts the values of a given vector from those
     * of this vector creating a new vector object. If the provided vector is
     * null, an exception is thrown.
     * 
     * @param vec
     *            the vector to subtract from this vector.
     * @return the result vector.
     */
    public @Context Vector2f subtract(@Context Vector2f vec) {
        return subtract(vec, null);
    }

    /**
     * <code>subtract</code> subtracts the values of a given vector from those
     * of this vector storing the result in the given vector object. If the
     * provided vector is null, an exception is thrown.
     * 
     * @param vec
     *            the vector to subtract from this vector.
     * @param store
     *            the vector to store the result in. It is safe for this to be
     *            the same as vec. If null, a new vector is created.
     * @return the result vector.
     */
    public @Context Vector2f subtract(@Context Vector2f vec, @Context Vector2f store) {
        if (store == null)
            store = new @Context Vector2f();
        store.x = x - vec.x;
        store.y = y - vec.y;
        return store;
    }

    /**
     * <code>subtract</code> subtracts the given x,y values from those of this
     * vector creating a new vector object.
     * 
     * @param valX
     *            value to subtract from x
     * @param valY
     *            value to subtract from y
     * @return this
     */
    public @Context Vector2f subtract(@Context float valX, @Context float valY) {
        return new @Context Vector2f(x - valX, y - valY);
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
    public @Context Vector2f subtractLocal(@Context Vector2f vec) {
        if (null == vec) {
            logger.warning("Provided vector is null, null returned.");
            return null;
        }
        x -= vec.x;
        y -= vec.y;
        return this;
    }

    /**
     * <code>subtractLocal</code> subtracts the provided values from this
     * vector internally, and returns a handle to this vector for easy chaining
     * of calls.
     * 
     * @param valX
     *            value to subtract from x
     * @param valY
     *            value to subtract from y
     * @return this
     */
    public @Context Vector2f subtractLocal(@Context float valX, @Context float valY) {
        x -= valX;
        y -= valY;
        return this;
    }

    /**
     * <code>normalize</code> returns the unit vector of this vector.
     * 
     * @return unit vector of this vector.
     */
    public @Context Vector2f normalize() {
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
    public @Context Vector2f normalizeLocal() {
        @Context float length = length();
        if (Endorsements.endorse(length != 0)) {
            return divideLocal(length);
        }

        return divideLocal(1);
    }

    /**
     * <code>smallestAngleBetween</code> returns (in radians) the minimum
     * angle between two vectors. It is assumed that both this vector and the
     * given vector are unit vectors (iow, normalized).
     * 
     * @param otherVector
     *            a unit vector to find the angle against
     * @return the angle in radians.
     */
    public @Context float smallestAngleBetween(@Context Vector2f otherVector) {
        @Context float dotProduct = dot(otherVector);
        @Context float angle = FastMath.acos(Endorsements.endorse(dotProduct)); // EnerJ TODO
        return angle;
    }

    /**
     * <code>angleBetween</code> returns (in radians) the angle required to
     * rotate a ray represented by this vector to lie colinear to a ray
     * described by the given vector. It is assumed that both this vector and
     * the given vector are unit vectors (iow, normalized).
     * 
     * @param otherVector
     *            the "destination" unit vector
     * @return the angle in radians.
     */
    public @Context float angleBetween(@Context Vector2f otherVector) {
        @Context float angle = FastMath.atan2(Endorsements.endorse(otherVector.y), Endorsements.endorse(otherVector.x))
                - FastMath.atan2(Endorsements.endorse(y), Endorsements.endorse(x)); // EnerJ TODO
        return angle;
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
    /**
     * <code>getAngle</code> returns (in radians) the angle represented by
     * this Vector2f as expressed by a conversion from rectangular coordinates (<code>x</code>,&nbsp;<code>y</code>)
     * to polar coordinates (r,&nbsp;<i>theta</i>).
     * 
     * @return the angle in radians. [-pi, pi)
     */
    public @Context float getAngle() {
        return -FastMath.atan2(Endorsements.endorse(y), Endorsements.endorse(x));
    }

    /**
     * <code>zero</code> resets this vector's data to zero internally.
     */
    public void zero() {
        x = y = 0;
    }

    /**
     * <code>hashCode</code> returns a unique code for this vector object
     * based on it's values. If two vectors are logically equivalent, they will
     * return the same hash code value.
     * 
     * @return the hash code value of this vector.
     */
    public int hashCode() {
        int hash = 37;
        hash += 37 * hash + Float.floatToIntBits(Endorsements.endorse(x));
        hash += 37 * hash + Float.floatToIntBits(Endorsements.endorse(y));
        return hash;
    }

    @Override
    public Vector2f clone() {
        try {
            return (Vector2f) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // can not happen
        }
    }

    /**
     * Saves this Vector2f into the given float[] object.
     * 
     * @param floats
     *            The float[] to take this Vector2f. If null, a new float[2] is
     *            created.
     * @return The array, with X, Y float values in that order
     */
    public @Context float[] toArray(@Context float[] floats) {
        if (floats == null) {
            floats = new @Context float[2];
        }
        floats[0] = x;
        floats[1] = y;
        return floats;
    }

    /**
     * are these two vectors the same? they are is they both have the same x and
     * y values.
     * 
     * @param o
     *            the object to compare for equality
     * @return true if they are equal
     */
    public boolean equals(Object o) {
        if (!(o instanceof Vector2f)) {
            return false;
        }

        if (this == o) {
            return true;
        }

        Vector2f comp = (Vector2f) o;
        if (Float.compare(Endorsements.endorse(x), comp.x) != 0)
            return false;
        if (Float.compare(Endorsements.endorse(y), comp.y) != 0)
            return false;
        return true;
    }

    /**
     * <code>toString</code> returns the string representation of this vector.
     * The format is: <code>(xx.x..., yy.y...)</code>
     * <p>
     * If you want to display a class name, then use
     * Vector2f.class.getName() or getClass().getName().
     * </p>
     *
     * @return the string representation of this vector.
     */
    public String toString() {
        return "(" + Endorsements.endorse(x) + ", " + Endorsements.endorse(y) + ')';
    }

    public Class<? extends Vector2f> getClassTag() {
        return this.getClass();
    }

    public void rotateAroundOrigin(@Context float angle, boolean cw) {
        if (cw)
            angle = -angle;
        @Context float newX = FastMath.cos(Endorsements.endorse(angle)) * x - FastMath.sin(Endorsements.endorse(angle)) * y;
        @Context float newY = FastMath.sin(Endorsements.endorse(angle)) * x + FastMath.cos(Endorsements.endorse(angle)) * y;
        x = newX;
        y = newY;
    }
    
    public void writeExternal(ObjectOutput out) throws IOException {
	/*
        out.writeFloat(x);
        out.writeFloat(y);
*/
    }
    
}
