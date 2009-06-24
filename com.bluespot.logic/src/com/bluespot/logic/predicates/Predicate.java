package com.bluespot.logic.predicates;

import com.bluespot.logic.Predicates;
import com.bluespot.logic.adapters.Adapter;
import com.bluespot.logic.predicates.builder.PredicateBuilder;

/**
 * An interface that defines a predicate. Predicates represent only boolean
 * methods and should not affect any values in any way. Predicates should accept
 * as wide a range of values as possible; only in the most severe cases should
 * an exception be thrown instead of returning {@code false}. Null values should
 * generally evaluate to {@code false}.
 * <p>
 * Predicates can be constructed using a {@link PredicateBuilder}. This allows
 * you to construct predicates in a readable, domain-specific way. These
 * builders also provide more traditional imperative methods if you're not
 * interesting in working with the DSL.
 * <p>
 * Many common predicates have already been written and factory methods for them
 * reside in {@link Predicates}. Using these in conjunction with builders makes
 * creating simple predicates easy and complex predicates possible.
 * <p>
 * All predicates should implement {@link Object#equals(Object)} appropriately,
 * even composite ones. This is possible because constructed predicates are
 * immutable.
 * <p>
 * Predicates are, in their crudest form, an {@link Adapter} implementations
 * that convert some arbitrary value to a {@code Boolean}. I opted against
 * realizing this in the code, however, because it does not make sense
 * semantically. Predicates are responsible for testing values, whereas adapters
 * are responsible for converting values. These are separate responsibilities,
 * and I don't believe that testing is a special case of conversion
 * semantically, so I didn't extend {@code Adapter}.
 * 
 * @author Aaron Faanes
 * 
 * @param <T>
 *            the type of value this predicate should expect
 */
public interface Predicate<T> {

    /**
     * Tests the specified value. This method should accept the widest range of
     * values as is appropriate. Specifically, null and other degenerate values
     * should be expected cases for this method.
     * <p>
     * Unless otherwise noted, values that are equal should cause this predicate
     * to evaluate in an equal manner. This method should also evaluate
     * consistently given consistent inputs. This intentionally resembles the
     * requirements for a well-written {@link Object#equals(Object)}
     * implementation.
     * 
     * @param value
     *            the value to evaluate
     * @return some boolean value that is a response to the specified value
     */
    public boolean test(final T value);
}
