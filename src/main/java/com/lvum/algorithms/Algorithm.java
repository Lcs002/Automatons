package com.lvum.algorithms;

import com.lvum.Automaton;

/**
 * Automaton Algorithm
 * <p>
 * Interface for all algorithms that can be run on an Automaton.
 * @param <T> The type of the result of the algorithm.
 */
public interface Algorithm<T> {
    T run(Automaton automaton);
}
