/**
 * 
 */
package com.bluespot.geom.vectors;

import com.bluespot.geom.Axis;

/**
 * A {@link Vector3} in {@code int} precision.
 * 
 * @author Aaron Faanes
 * 
 * @see Vector3f
 * @see Vector3d
 */
public final class Vector3i extends AbstractVector3<Vector3i> {

	/**
	 * Create a mutable {@link Vector3i} using the specified value for all axes.
	 * 
	 * @param v
	 *            the value used for all axes
	 * @return a mutable {@code Vector3i}
	 * @throw {@link IllegalArgumentException} if {@code v} is {@code NaN}
	 */
	public static Vector3i mutable(int v) {
		return Vector3i.mutable(v, v, v);
	}

	/**
	 * Create a frozen {@link Vector3i} using the specified value for all axes.
	 * 
	 * @param v
	 *            the value used for all axes
	 * @return a frozen {@code Vector3i}
	 * @throw {@link IllegalArgumentException} if {@code v} is {@code NaN}
	 */
	public static Vector3i frozen(int v) {
		return Vector3i.mutable(v, v, v);
	}

	public static Vector3i mutable(int x, int y, int z) {
		return new Vector3i(true, x, y, z);
	}

	public static Vector3i frozen(int x, int y, int z) {
		return new Vector3i(false, x, y, z);
	}

	public static Vector3i mutable(Vector3f vector) {
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		return new Vector3i(true, (int) vector.x(), (int) vector.y(), (int) vector.z());
	}

	public static Vector3i frozen(Vector3f vector) {
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		return new Vector3i(false, (int) vector.x(), (int) vector.y(), (int) vector.z());
	}

	public static Vector3i mutable(Vector3d vector) {
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		return new Vector3i(true, (int) vector.x(), (int) vector.y(), (int) vector.z());
	}

	public static Vector3i frozen(Vector3d vector) {
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		return new Vector3i(false, (int) vector.x(), (int) vector.y(), (int) vector.z());
	}

	/**
	 * Interpolates between this vector and the destination. Offsets that are
	 * not between zero and one are handled specially:
	 * <ul>
	 * <li>If {@code offset <= 0}, a copy of {@code src} is returned
	 * <li>If {@code offset >= 1}, a copy of {@code dest} is returned
	 * </ul>
	 * This special behavior allows clients to reliably detect when
	 * interpolation is complete.
	 * 
	 * @param src
	 *            the starting vector
	 * @param dest
	 *            the ending vector
	 * @param offset
	 *            the percentage of distance between the specified points
	 * @return a mutable vector that lies between src and dest
	 */
	public static Vector3i interpolated(Vector3i src, Vector3i dest, float offset) {
		if (src == null) {
			throw new NullPointerException("src must not be null");
		}
		if (dest == null) {
			throw new NullPointerException("dest must not be null");
		}
		if (offset <= 0f) {
			return src.toMutable();
		}
		if (offset >= 1f) {
			return dest.toMutable();
		}
		return mutable(src.x + (int) ((dest.x - src.x) * offset),
				src.y + (int) ((dest.y - src.y) * offset),
				src.z + (int) ((dest.z - src.z) * offset));
	}

	private static final Vector3i ORIGIN = Vector3i.frozen(0);

	/**
	 * Returns a frozen vector at the origin.
	 * 
	 * @return a frozen vector at the origin.
	 */
	public static Vector3i origin() {
		return ORIGIN;
	}

	/**
	 * Return a frozen vector with values of 1 at the specified axes. This is
	 * normally used to create unit vectors, but {@code axis} values of multiple
	 * axes are allowed.
	 * 
	 * @param axis
	 *            the axes with values of 1
	 * @return a frozen unit vector
	 */
	public static Vector3i unit(Axis axis) {
		return origin().with(axis, 1).toFrozen();
	}

	private static final Vector3i UP = Vector3i.frozen(0, 1, 0);

	/**
	 * Returns a frozen vector that points up the y axis.
	 * 
	 * @return a frozen vector with components {@code (0, 1, 0)}
	 */
	public static Vector3i up() {
		return UP;
	}

	private static final Vector3i FORWARD = Vector3i.frozen(0, 0, -1);

	/**
	 * Returns a frozen vector that points down the z axis.
	 * 
	 * @return a frozen vector with components {@code (0, 0, -1)}
	 */
	public static Vector3i forward() {
		return FORWARD;
	}

	private static final Vector3i LEFT = Vector3i.frozen(-1, 0, 0);

	/**
	 * Returns a frozen vector that points down the negative x axis.
	 * 
	 * @return a frozen vector with components {@code (-1, 0, 0)}
	 */
	public static final Vector3i left() {
		return LEFT;
	}

	private static final Vector3i RIGHT = Vector3i.frozen(1, 0, 0);

	/**
	 * Returns a frozen vector that points down the positive x axis.
	 * 
	 * @return a frozen vector with components {@code (1, 0, 0)}
	 */
	public static Vector3i right() {
		return RIGHT;
	}

	private static final Vector3i DOWN = UP.inverted().toFrozen();

	/**
	 * Returns a frozen vector that points down the negative Y axis.
	 * 
	 * @return a frozen vector with components {@code (0, -1, 0)}
	 */
	public static final Vector3i down() {
		return DOWN;
	}

	private int z;
	private int y;
	private int x;

	/**
	 * Constructs a vector using the specified coordinates.
	 * 
	 * @param mutable
	 *            whether this vector can be directly modified
	 * @param x
	 *            the x-coordinate of this vector
	 * @param y
	 *            the y-coordinate of this vector
	 * @param z
	 *            the z-coordinate of this vector
	 * @throws IllegalArgumentException
	 *             if any coordinate is {@code NaN}
	 */
	private Vector3i(final boolean mutable, final int x, final int y, final int z) {
		super(mutable);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the x-coordinate of this vector.
	 * 
	 * @return the x-coordinate of this vector
	 */
	public int x() {
		return this.x;
	}

	/**
	 * Sets the x position to the specified value.
	 * 
	 * @param value
	 *            the new x value
	 * @return the old x value
	 */
	public int setX(int value) {
		if (!this.isMutable()) {
			throw new UnsupportedOperationException("vector is not mutable");
		}
		int old = this.x;
		this.x = value;
		return old;
	}

	/**
	 * Returns a translated mutable vector. The returned vector will be at the
	 * same position as this one, but with the x value set to the specified
	 * value.
	 * 
	 * @param value
	 *            the new x value
	 * @return a mutable vector that uses the specified value for its x axis
	 */
	public Vector3i withX(int value) {
		Vector3i result = this.toMutable();
		result.setX(value);
		return result;
	}

	/**
	 * Add the specified x value to this vector.
	 * 
	 * @param offset
	 *            the value to add
	 * @return the old x value
	 */
	public int addX(int offset) {
		return this.setX(this.x() + offset);
	}

	/**
	 * Return a mutable vector that has the same position as this one, except
	 * for the specified translation.
	 * 
	 * @param offset
	 *            the value to add
	 * @return a vector at {@code (x + offset, y, z)}
	 */
	public Vector3i addedX(int offset) {
		Vector3i vector = this.toMutable();
		vector.addX(offset);
		return vector;
	}

	/**
	 * Subtract the specified value from this vector's X axis.
	 * 
	 * @param offset
	 *            the value to subtract
	 * @return the old value at the X axis
	 * @see #subtractedX(int)
	 * @throw UnsupportedOperationException if this vector is not mutable
	 */
	public int subtractX(int offset) {
		return this.setX(this.x() - offset);
	}

	/**
	 * Return a mutable vector at this vector's position, but with the specified
	 * translation.
	 * 
	 * @param offset
	 *            the value to subtract
	 * @return a mutable vector at {@code (x - offset, y, z)}
	 * @see #subtractX(int)
	 */
	public Vector3i subtractedX(int offset) {
		return this.withX(this.x() - offset);
	}

	/**
	 * Multiply the specified x value of this vector.
	 * 
	 * @param factor
	 *            the factor of multiplication
	 * @return the old x value
	 */
	public double multiplyX(double factor) {
		return this.setX((int) Math.round(this.x() * factor));
	}

	/**
	 * Return a mutable copy of this vector, with a multiplied x value.
	 * 
	 * @param factor
	 *            the factor of multiplication
	 * @return a mutable vector at {@code (x * offset, y, z)}
	 */
	public Vector3i mulipliedX(double factor) {
		Vector3i vector = this.toMutable();
		vector.multiplyX(factor);
		return vector;
	}

	/**
	 * Returns the y-coordinate of this vector.
	 * 
	 * @return the y-coordinate of this vector
	 */
	public int y() {
		return this.y;
	}

	/**
	 * Sets the y position to the specified value.
	 * 
	 * @param value
	 *            the new y value
	 * @return the old y value
	 */
	public int setY(int value) {
		if (!this.isMutable()) {
			throw new UnsupportedOperationException("vector is not mutable");
		}
		int old = this.y;
		this.y = value;
		return old;
	}

	/**
	 * Returns a translated mutable vector. The returned vector will be at the
	 * same position as this one, but with the y value set to the specified
	 * value.
	 * 
	 * @param value
	 *            the new y value
	 * @return a mutable vector that uses the specified value for its y axis
	 */
	public Vector3i withY(int value) {
		Vector3i result = this.toMutable();
		result.setY(value);
		return result;
	}

	/**
	 * Add the specified y value to this vector.
	 * 
	 * @param offset
	 *            the value to add
	 * @return the old y value
	 */
	public int addY(int offset) {
		return this.setY(this.y() + offset);
	}

	/**
	 * Return a mutable vector that has the same position as this one, except
	 * for the specified translation.
	 * 
	 * @param offset
	 *            the value to add
	 * @return a vector at {@code (x, y + offset, z)}
	 */
	public Vector3i addedY(int offset) {
		Vector3i vector = this.toMutable();
		vector.addY(offset);
		return vector;
	}

	/**
	 * Subtract the specified value from this vector's Y axis.
	 * 
	 * @param offset
	 *            the value to subtract
	 * @return the old value at the Y axis
	 * @see #subtractedY(int)
	 * @throw UnsupportedOperationException if this vector is not mutable
	 */
	public int subtractY(int offset) {
		return this.setY(this.y() - offset);
	}

	/**
	 * Return a mutable vector at this vector's position, but with the specified
	 * translation.
	 * 
	 * @param offset
	 *            the value to subtract
	 * @return a mutable vector at {@code (x, y - offset, z)}
	 * @see #subtractY(int)
	 */
	public Vector3i subtractedY(int offset) {
		return this.withY(this.y() - offset);
	}

	/**
	 * Multiply the specified y value of this vector.
	 * 
	 * @param factor
	 *            the factor of multiplication
	 * @return the old y value
	 */
	public double multiplyY(double factor) {
		return this.setY((int) Math.round(this.y() * factor));
	}

	/**
	 * Return a mutable copy of this vector, with a multiplied y value.
	 * 
	 * @param factor
	 *            the factor of multiplication
	 * @return a mutable vector at {@code (x, y * offset, z)}
	 */
	public Vector3i mulipliedY(double factor) {
		Vector3i vector = this.toMutable();
		vector.multiplyY(factor);
		return vector;
	}

	/**
	 * Returns the z-coordinate of this vector.
	 * 
	 * @return the z-coordinate of this vector
	 */
	public int z() {
		return this.z;
	}

	/**
	 * Sets the z position to the specified value.
	 * 
	 * @param value
	 *            the new z value
	 * @return the old z value
	 */
	public int setZ(int value) {
		if (!this.isMutable()) {
			throw new UnsupportedOperationException("vector is not mutable");
		}
		int old = this.z;
		this.z = value;
		return old;
	}

	/**
	 * Returns a translated mutable vector. The returned vector will be at the
	 * same position as this one, but with the z value set to the specified
	 * value.
	 * 
	 * @param value
	 *            the new z value
	 * @return a mutable vector that uses the specified value for its z axis
	 */
	public Vector3i withZ(int value) {
		Vector3i result = this.toMutable();
		result.setZ(value);
		return result;
	}

	/**
	 * Add the specified z value to this vector.
	 * 
	 * @param offset
	 *            the value to add
	 * @return the old z value
	 */
	public int addZ(int offset) {
		return this.setZ(this.z() + offset);
	}

	/**
	 * Return a mutable vector that has the same position as this one, except
	 * for the specified translation.
	 * 
	 * @param offset
	 *            the value to add
	 * @return a vector at {@code (x, y, z + offset)}
	 */
	public Vector3i addedZ(int offset) {
		Vector3i vector = this.toMutable();
		vector.addZ(offset);
		return vector;
	}

	/**
	 * Subtract the specified value from this vector's Z axis.
	 * 
	 * @param offset
	 *            the value to subtract
	 * @return the old value at the Z axis
	 * @see #subtractedZ(int)
	 * @throw UnsupportedOperationException if this vector is not mutable
	 */
	public int subtractZ(int offset) {
		return this.setZ(this.z() - offset);
	}

	/**
	 * Return a mutable vector at this vector's position, but with the specified
	 * translation.
	 * 
	 * @param offset
	 *            the value to subtract
	 * @return a mutable vector at {@code (x, y, z - offset)}
	 * @see #subtractZ(int)
	 */
	public Vector3i subtractedZ(int offset) {
		return this.withZ(this.z() - offset);
	}

	/**
	 * Multiply the specified z value of this vector.
	 * 
	 * @param factor
	 *            the factor of multiplication
	 * @return the old z value
	 */
	public double multiplyZ(double factor) {
		return this.setZ((int) Math.round(this.z() * factor));
	}

	/**
	 * Return a mutable copy of this vector, with a multiplied z value.
	 * 
	 * @param factor
	 *            the factor of multiplication
	 * @return a mutable vector at {@code (x, y, z * offset)}
	 */
	public Vector3i mulipliedZ(double factor) {
		Vector3i vector = this.toMutable();
		vector.multiplyZ(factor);
		return vector;
	}

	@Override
	public void set(Vector3i vector) {
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		this.setX(vector.x());
		this.setY(vector.y());
		this.setZ(vector.z());
	}

	/**
	 * Sets all of this vector's values to the specified value.
	 * 
	 * @param value
	 *            the value that will be used
	 */
	public void set(int value) {
		this.setX(value);
		this.setY(value);
		this.setZ(value);
	}

	/**
	 * Sets all of this vector's values to the specified values.
	 * 
	 * @param x
	 *            the new x value
	 * @param y
	 *            the new y value
	 * @param z
	 *            the new z value
	 */
	public void set(int x, int y, int z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	@Override
	public void set(Axis axis, Vector3i vector) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		switch (axis) {
		case X:
			this.setX(vector.x());
			return;
		case Y:
			this.setY(vector.y());
			return;
		case Z:
			this.setZ(vector.z());
			return;
		case XY:
			this.setX(vector.x());
			this.setY(vector.y());
			return;
		case XZ:
			this.setX(vector.x());
			this.setZ(vector.z());
			return;
		case YZ:
			this.setY(vector.y());
			this.setZ(vector.z());
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	/**
	 * Sets values at the specified axes to the specified value.
	 * 
	 * @param axis
	 *            the axes that will be modified
	 * @param value
	 *            the added value
	 */
	public void set(Axis axis, int value) {
		if (!this.isMutable()) {
			throw new UnsupportedOperationException("vector is not mutable");
		}
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		switch (axis) {
		case X:
			this.setX(value);
			return;
		case Y:
			this.setY(value);
			return;
		case Z:
			this.setZ(value);
			return;
		case XY:
			this.setX(value);
			this.setY(value);
			return;
		case XZ:
			this.setX(value);
			this.setZ(value);
			return;
		case YZ:
			this.setY(value);
			this.setZ(value);
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	/**
	 * Return a mutable copy of this vector, with the copy's axis values set to
	 * the specified value.
	 * 
	 * @param axis
	 *            the axes that are modified
	 * @param value
	 *            the new axis value
	 * @return a modified, mutable copy of this vector
	 */
	public Vector3i with(Axis axis, int value) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		Vector3i result = this.toMutable();
		result.set(axis, value);
		return result;
	}

	@Override
	public void add(Vector3i vector) {
		this.addX(vector.x());
		this.addY(vector.y());
		this.addZ(vector.z());
	}

	/**
	 * Adds the specified value to all of this vector's values.
	 * 
	 * @param value
	 *            the value that will be used
	 */
	public void add(int value) {
		this.addX(value);
		this.addY(value);
		this.addZ(value);
	}

	@Override
	public void add(Axis axis, Vector3i vector) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		switch (axis) {
		case X:
			this.addX(vector.x());
			return;
		case Y:
			this.addY(vector.y());
			return;
		case Z:
			this.addZ(vector.z());
			return;
		case XY:
			this.addX(vector.x());
			this.addY(vector.y());
			return;
		case XZ:
			this.addX(vector.x());
			this.addZ(vector.z());
			return;
		case YZ:
			this.addY(vector.y());
			this.addZ(vector.z());
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	/**
	 * Adds the specified value to the specified axes.
	 * 
	 * @param axis
	 *            the axes that will be modified
	 * @param value
	 *            the added value
	 */
	public void add(Axis axis, int value) {
		if (!this.isMutable()) {
			throw new UnsupportedOperationException("vector is not mutable");
		}
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		switch (axis) {
		case X:
			this.addX(value);
			return;
		case Y:
			this.addY(value);
			return;
		case Z:
			this.addZ(value);
			return;
		case XY:
			this.addX(value);
			this.addY(value);
			return;
		case XZ:
			this.addX(value);
			this.addZ(value);
			return;
		case YZ:
			this.addY(value);
			this.addZ(value);
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	/**
	 * Returns a mutable vector that's translated by the specified amount.
	 * 
	 * @param value
	 *            the value that will be used
	 * @return a mutable vector that's at this position, but translated by the
	 *         specified amount
	 */
	public Vector3i added(int value) {
		Vector3i result = this.toMutable();
		result.add(value);
		return result;
	}

	/**
	 * Returns a mutable vector at this position, plus the specified
	 * translation.
	 * 
	 * @param axis
	 *            the axes that will be translated
	 * @param value
	 *            the added value
	 * @return a mutable vector translated from this position
	 */
	public Vector3i added(Axis axis, int value) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		Vector3i result = this.toMutable();
		result.add(axis, value);
		return result;
	}

	@Override
	public void subtract(Vector3i vector) {
		this.subtractX(vector.x());
		this.subtractY(vector.y());
		this.subtractZ(vector.z());
	}

	/**
	 * Subtracts the specified value from each of this vector's values.
	 * 
	 * @param value
	 *            the value that will be used
	 */
	public void subtract(int value) {
		this.subtractX(value);
		this.subtractY(value);
		this.subtractZ(value);
	}

	@Override
	public void subtract(Axis axis, Vector3i vector) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		switch (axis) {
		case X:
			this.subtractX(vector.x());
			return;
		case Y:
			this.subtractY(vector.y());
			return;
		case Z:
			this.subtractZ(vector.z());
			return;
		case XY:
			this.subtractX(vector.x());
			this.subtractY(vector.y());
			return;
		case XZ:
			this.subtractX(vector.x());
			this.subtractZ(vector.z());
			return;
		case YZ:
			this.subtractY(vector.y());
			this.subtractZ(vector.z());
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	/**
	 * Subtracts the specified value from the specified axes.
	 * 
	 * @param axis
	 *            the axes that will be modified
	 * @param value
	 *            the subtracted value
	 */
	public void subtract(Axis axis, int value) {
		if (!this.isMutable()) {
			throw new UnsupportedOperationException("vector is not mutable");
		}
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		switch (axis) {
		case X:
			this.subtractX(value);
			return;
		case Y:
			this.subtractY(value);
			return;
		case Z:
			this.subtractZ(value);
			return;
		case XY:
			this.subtractX(value);
			this.subtractY(value);
			return;
		case XZ:
			this.subtractX(value);
			this.subtractZ(value);
			return;
		case YZ:
			this.subtractY(value);
			this.subtractZ(value);
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	/**
	 * Returns a mutable vector that's translated by the specified amount.
	 * 
	 * @param value
	 *            the value that will be used
	 * @return a mutable vector that's at this position, but translated by the
	 *         specified amount
	 */
	public Vector3i subtracted(int value) {
		Vector3i result = this.toMutable();
		result.add(value);
		return result;
	}

	/**
	 * Returns a mutable vector at this position, minus the specified
	 * translation.
	 * 
	 * @param axis
	 *            the axes that will be translated
	 * @param value
	 *            the subtracted value
	 * @return a mutable vector translated from this position
	 */
	public Vector3i subtracted(Axis axis, int value) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		Vector3i result = this.toMutable();
		result.subtract(axis, value);
		return result;
	}

	@Override
	public void multiply(Vector3i vector) {
		this.multiplyX(vector.x());
		this.multiplyY(vector.y());
		this.multiplyZ(vector.z());
	}

	@Override
	public void multiply(double factor) {
		this.multiplyX(factor);
		this.multiplyY(factor);
		this.multiplyZ(factor);
	}

	@Override
	public void multiply(Axis axis, Vector3i vector) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		if (vector == null) {
			throw new NullPointerException("vector must not be null");
		}
		switch (axis) {
		case X:
			this.multiplyX(vector.x());
			return;
		case Y:
			this.multiplyY(vector.y());
			return;
		case Z:
			this.multiplyZ(vector.z());
			return;
		case XY:
			this.multiplyX(vector.x());
			this.multiplyY(vector.y());
			return;
		case XZ:
			this.multiplyX(vector.x());
			this.multiplyZ(vector.z());
			return;
		case YZ:
			this.multiplyY(vector.y());
			this.multiplyZ(vector.z());
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	@Override
	public void multiply(Axis axis, double factor) {
		if (!this.isMutable()) {
			throw new UnsupportedOperationException("vector is not mutable");
		}
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		switch (axis) {
		case X:
			this.multiplyX(factor);
			return;
		case Y:
			this.multiplyY(factor);
			return;
		case Z:
			this.multiplyZ(factor);
			return;
		case XY:
			this.multiplyX(factor);
			this.multiplyY(factor);
			return;
		case XZ:
			this.multiplyX(factor);
			this.multiplyZ(factor);
			return;
		case YZ:
			this.multiplyY(factor);
			this.multiplyZ(factor);
			return;
		}
		throw new IllegalArgumentException("Axis is invalid");
	}

	@Override
	public double length() {
		return Math.sqrt(Math.pow(this.x(), 2) + Math.pow(this.y(), 2) + Math.pow(this.z(), 2));
	}

	@Override
	public void normalize() {
		float len = (float) this.length();
		this.set(Math.round(this.x() / len),
				Math.round(this.y() / len),
				Math.round(this.z() / len));
	}

	@Override
	public void interpolate(Vector3i dest, float offset) {
		if (dest == null) {
			throw new NullPointerException("dest must not be null");
		}
		if (offset >= 1f) {
			this.set(dest);
		} else if (offset >= 0f) {
			this.x += (dest.x - this.x) * offset;
			this.y += (dest.y - this.y) * offset;
			this.z += (dest.z - this.z) * offset;
		}
	}

	@Override
	public void cross(Vector3i other) {
		this.set(this.y() * other.z() - other.y() * this.z(),
				-this.x() * other.z() + other.x() * this.z(),
				this.x() * other.y() - other.x() * this.y());
	}

	@Override
	public void clear() {
		this.set(0);
	}

	@Override
	public void clear(Axis axis) {
		this.set(axis, 0);
	}

	@Override
	public Vector3i toMutable() {
		return Vector3i.mutable(x, y, z);
	}

	@Override
	public Vector3i toFrozen() {
		if (!this.isMutable()) {
			return this;
		}
		return Vector3i.frozen(x, y, z);
	}

	@Override
	public boolean at(Vector3i vector) {
		if (vector == null) {
			return false;
		}
		return this.x() == vector.x() &&
				this.y() == vector.y() &&
				this.z() == vector.z();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Vector3i)) {
			return false;
		}
		final Vector3i other = (Vector3i) obj;
		if (this.isMutable() != other.isMutable()) {
			return false;
		}
		if (this.x() != other.x()) {
			return false;
		}
		if (this.y() != other.y()) {
			return false;
		}
		if (this.z() != other.z()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (this.isMutable() ? 1 : 0);
		result = 31 * result + this.x();
		result = 31 * result + this.y();
		result = 31 * result + this.z();
		return result;
	}

	@Override
	public String toString() {
		return String.format("Vector3i[%s (%d, %d, %d)]", this.isMutable(), this.x(), this.y(), this.z());
	}

}
