package com.bluespot.geom.points;

/**
 * Represents a single point in space, in varying precision. Points may or may
 * not be mutable, but mutability must never change for a given object.
 * <p>
 * Points use a slightly unusual recursive type definition. This lets us have a
 * common interface for points, while still avoiding the performance penalties
 * of boxing.
 * 
 * @author Aaron Faanes
 * @param <P>
 *            the type of point object. This should be recursive.
 * 
 */
public interface Point3D<P extends Point3D<?>> {

	public static enum Axis {
		X, Y, Z;
	}

	/**
	 * Set this point's values to the specified point.
	 * 
	 * @param point
	 *            the point that will be copied
	 * @throws UnsupportedOperationException
	 *             if this point is immutable
	 */
	public void set(P point);

	/**
	 * Copy another point's value at the specified axis.
	 * 
	 * @param axis
	 *            the axis to copy
	 * @param point
	 *            the point from which to copy
	 */
	public void set(Axis axis, P point);

	/**
	 * Return whether this point can be directly modified. This value is a
	 * constant for a given instance.
	 * 
	 * @return {@code true} if this point can be directly modified
	 */
	public boolean isMutable();

	/**
	 * Create and return a new, mutable instance of this point. The returned
	 * point will have the same position. New instances will be created even if
	 * this point is already mutable.
	 * 
	 * @return a new mutable instance of this point
	 */
	public P toMutable();

	/**
	 * Return an immutable instance of this point. If the point is already
	 * immutable, then that point may be returned directly.
	 * 
	 * @return an immutable instance of this point
	 */
	public P toFrozen();

	/**
	 * Return whether this point is at the same location as the specified point.
	 * This behaves similar to {@link #equals(Object)} but ignores the
	 * mutability of the specified point.
	 * 
	 * @param point
	 *            the point that will be compared against
	 * @return {@code true} if this point is at the same location as the
	 *         specified point
	 */
	public boolean at(P point);

	/**
	 * Return a mutable point that lies between this point and the specified
	 * destination. The offset may be any value, but interpolation always occurs
	 * between this point and the specified one: large or negative offset are
	 * handled specially:
	 * <ul>
	 * <li>If {@code offset <= 0}, this point should be returned
	 * <li>If {@code offset >= 1}, {@code destination} should be returned
	 * </ul>
	 * Returning copies instead of always interpolating allows clients to
	 * reliably detect when interpolation is complete.
	 * 
	 * @param dest
	 *            the final point
	 * @param offset
	 *            the percentage of distance traveled.
	 * @return a point that lies between this point and the destination
	 * @see #interpolate(Point3D, float)
	 */
	public P toInterpolated(P dest, float offset);

	/**
	 * Interpolates between this point and the destination. This point will be
	 * modified as a result of this operation. Offsets that are not between zero
	 * and one are handled specially:
	 * <ul>
	 * <li>If {@code offset <= 0}, nothing is modified
	 * <li>If {@code offset >= 1}, this point is set to {@code destination}
	 * </ul>
	 * This special behavior allows clients to reliably detect when
	 * interpolation is complete.
	 * 
	 * @param dest
	 *            the final point
	 * @param offset
	 *            the percentage of distance traveled
	 * @see #toInterpolated(Point3D, float)
	 */
	public void interpolate(P dest, float offset);

}