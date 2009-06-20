package com.bluespot.logic.visitors;

import com.bluespot.logic.predicates.Predicate;

/**
 * A {@link Visitor} that conditionally forwards values to a given visitor. For
 * any given value, that value must first evaluate to {@code true} from the
 * sentinel's predicate before it is sent to the sentinel's visitor.
 * 
 * @author Aaron Faanes
 * 
 * @param <T>
 *            the type of value expected by this sentinel
 * @see CompositeSentinel
 */
public class SingleSentinel<T> implements Visitor<T> {

    private final Predicate<? super T> predicate;
    private final Visitor<? super T> visitor;

    /**
     * Constructs a sentinel that guards the specified visitor with the
     * specified predicate.
     * 
     * @param predicate
     *            the predicate that guards this sentinel's visitor
     * @param visitor
     *            the visitor that is guarded by this sentinel
     * @throws NullPointerException
     *             if either argument is null
     */
    public SingleSentinel(final Predicate<? super T> predicate, final Visitor<? super T> visitor) {
        if (predicate == null) {
            throw new NullPointerException("predicate is null");
        }
        if (visitor == null) {
            throw new NullPointerException("visitor is null");
        }
        this.predicate = predicate;
        this.visitor = visitor;
    }

    /**
     * Returns the {@link Predicate} used by this sentinel.
     * 
     * @return the {@code Predicate} used by this sentinel
     */
    public Predicate<? super T> getPredicate() {
        return this.predicate;
    }

    /**
     * Returns the {@link Visitor} that is guarded by this sentinel
     * 
     * @return the {@code Visitor} that is guarded by this sentinel
     */
    public Visitor<? super T> getVisitor() {
        return this.visitor;
    }

    /**
     * Checks the specified value. If it passes this sentinel's test, it will be
     * passed to all of this sentinel's visitors.
     * 
     * @param value
     *            the value to check
     */
    public void accept(final T value) {
        if (!this.predicate.test(value)) {
            return;
        }
        this.visitor.accept(value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SingleSentinel<?>)) {
            return false;
        }
        final SingleSentinel<?> other = (SingleSentinel<?>) obj;
        if (!this.getPredicate().equals(other.getPredicate())) {
            return false;
        }
        if (!this.getVisitor().equals(other.getVisitor())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getPredicate().hashCode();
        result = 31 * result + this.getVisitor().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("If value %s, then do %s.", this.getPredicate(), this.getVisitor());
    }

}