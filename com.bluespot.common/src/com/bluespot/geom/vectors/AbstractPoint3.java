/**
 * 
 */
package com.bluespot.geom.vectors;

import com.bluespot.geom.Axis;

/**
 * A basic {@link Vector3} implementation.
 * 
 * @author Aaron Faanes
 * 
 * @param <P>
 *            the type of this point
 */
public abstract class AbstractPoint3<P extends Vector3<P>> implements Vector3<P> {

	private final boolean mutable;

	protected AbstractPoint3(boolean mutable) {
		this.mutable = mutable;
	}

	@Override
	public boolean isMutable() {
		return this.mutable;
	}

	@Override
	public P with(Axis axis, P point) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		if (point == null) {
			throw new NullPointerException("Point must not be null");
		}
		P result = this.toMutable();
		result.set(axis, point);
		return result;
	}

	@Override
	public P added(P point) {
		P result = this.toMutable();
		result.add(point);
		return result;
	}

	@Override
	public P added(Axis axis, P point) {
		if (axis == null) {
			throw new NullPointerException("Axis must not be null");
		}
		if (point == null) {
			throw new NullPointerException("Point must not be null");
		}
		P result = this.toMutable();
		result.add(axis, point);
		return result;
	}

	@Override
	public P multiplied(P point) {
		P result = this.toMutable();
		result.multiply(point);
		return result;
	}

	@Override
	public P multiplied(double factor) {
		P result = this.toMutable();
		result.multiply(factor);
		return result;
	}

	@Override
	public P multiplied(Axis axis, P point) {
		P result = this.toMutable();
		result.multiply(axis, point);
		return result;
	}

	@Override
	public P multiplied(Axis axis, double factor) {
		P result = this.toMutable();
		result.multiply(axis, factor);
		return result;
	}

	@Override
	public P interpolated(P dest, float offset) {
		P result = this.toMutable();
		result.interpolate(dest, offset);
		return result;
	}

	@Override
	public P cleared(Axis axis) {
		P result = this.toMutable();
		result.clear(axis);
		return result;
	}

}