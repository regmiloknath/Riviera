package collections.table;

import java.util.List;

import collections.table.iteration.TableIterator;
import geom.vectors.Vector3i;

/**
 * A two-dimensional collection of elements.
 * 
 * @author Aaron Faanes
 * @param <T>
 *            The type of element in this table
 * @see AbstractTable
 */
public interface Table<T> extends Iterable<T> {

	/**
	 * Resets the table to an "empty" state. This state might be null values, or
	 * it may be default elements, depending on the implementation.
	 */
	public void clear();

	/**
	 * Returns the element at the specified position.
	 * 
	 * @param x
	 *            the x value of the requested element
	 * @param y
	 *            the y value of the requested element
	 * @return the requested element
	 * @throw IndexOutOfBoundsException if the position is out of bounds
	 */
	public T get(int x, int y);

	/**
	 * Returns the element at the specified point.
	 * <p>
	 * This method will never wrap. If you're interested in wrapping
	 * functionality, see {@link Table#tableIterator}.
	 * 
	 * @param location
	 *            location of the element to return. This point is not affected
	 *            by this method.
	 * @return the element at the specified location.
	 * @throws IndexOutOfBoundsException
	 *             if the point is out of range.
	 */
	public T get(Vector3i location);

	/**
	 * Returns the height of this table. If the height is greater than
	 * Integer.MAX_VALUE, this returns Integer.MAX_VALUE.
	 * <p>
	 * Notice that this returns the dimension of a fully generated table, and
	 * not the immediate size. For tables that are fully initialized all the
	 * time, like 2-dimensional array-backed tables, there's no difference
	 * between the immediate size, and the potential size. However, for
	 * dynamically generated tables, certain areas may not be created yet, and
	 * the immediate size of the table may differ from the potential size.
	 * 
	 * @return the height of this table
	 */
	public int height();

	/**
	 * Returns the width of this table. If the width is greater than
	 * Integer.MAX_VALUE, this returns Integer.MAX_VALUE.
	 * <p>
	 * Notice that this returns the dimension of a fully generated table, and
	 * not the immediate size. For tables that are fully initialized all the
	 * time, like 2-dimensional array-backed tables, there's no difference
	 * between the immediate size, and the potential size. However, for
	 * dynamically generated tables, certain areas may not be created yet, and
	 * the immediate size of the table may differ from the potential size.
	 * 
	 * @return the width of this table
	 */
	public int width();

	/**
	 * Replaces the element at the specified point with the specified element.
	 * <p>
	 * This method will never wrap. If you're interested in wrapping
	 * functionality, see <code>tableIterator</code>.
	 * 
	 * @param location
	 *            location of the element to change
	 * @param element
	 *            element to be stored at the specified location
	 * @throws IndexOutOfBoundsException
	 *             if the location is out of range
	 * @return the old element that was at this location. If no element was at
	 *         the specified point, the default value will be returned.
	 * 
	 * @see Table#tableIterator
	 */
	public T put(Vector3i location, T element);

	/**
	 * Resets the element at the specified location to an "empty" state. This
	 * state might be null values, or it may be defaults, depending on the
	 * implementation.
	 * 
	 * @param location
	 *            the location to reset
	 * @return the element that was removed, if any. If no element was removed,
	 *         the default value will be returned.
	 */
	public T remove(Vector3i location);

	/**
	 * Returns the area of this table. If the area is greater than
	 * Integer.MAX_VALUE, this returns Integer.MAX_VALUE.
	 * <p>
	 * Notice that this returns the dimension of a fully generated table, and
	 * not the immediate size. For tables that are fully initialized all the
	 * time, like 2-dimensional array-backed tables, there's no difference
	 * between the immediate size, and the potential size. However, for
	 * dynamically generated tables, certain areas may not be created yet, and
	 * the immediate size of the table may differ from the potential size.
	 * 
	 * @return the area of this table
	 */
	public int size();

	/**
	 * Return the dimensions of this table as a vector.
	 * 
	 * @return the dimensions of this table as a vector
	 */
	public Vector3i dimensions();

	/**
	 * Calls {@link Table#subTable(Vector3i, Vector3i)}, with the size of the
	 * subTable being that of this table's width and height minus the specified
	 * {@code newOrigin}.
	 * 
	 * @param newOrigin
	 *            the new origin of the subtable
	 * @return a table that represents a partial view of this table
	 * @throws IndexOutOfBoundsException
	 *             if the newOrigin is out of bounds
	 * @see Table#subTable(Vector3i, Vector3i)
	 */
	public Table<T> subTable(Vector3i newOrigin);

	/**
	 * Returns a table that is backed by this table's data.
	 * {@link Table#get(Vector3i)} will refer to the new origin of the created
	 * subtable. Changes made to the subtable are reflected in the original
	 * table.
	 * <p>
	 * This is intended to mirror the functionality of {@link List#subList}.
	 * 
	 * @param newOrigin
	 *            the new origin of the subtable
	 * @param size
	 *            the new size of the subtable
	 * @throws IndexOutOfBoundsException
	 *             if the newOrigin is out of bounds, or if the dimensions are
	 *             too large for the subTable
	 * @return a table that represents a partial view of this table
	 */
	public Table<T> subTable(Vector3i newOrigin, Vector3i size);

	/**
	 * Returns a table iterator over the elements in this table. The order is
	 * guaranteed to be consistent across calls, as long as the table is
	 * unmodified but is otherwise unspecified.
	 * 
	 * @return a table iterator over the elements in this table
	 * @see TableIterator
	 */
	public TableIterator<T> tableIterator();
}
