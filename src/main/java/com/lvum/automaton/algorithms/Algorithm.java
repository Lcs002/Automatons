package com.lvum.automaton.algorithms;

import com.lvum.automaton.Automaton;

/**
 * <h1>Algorithm</h1>
 * <p>Interface that represents an algorithm.</p>
 * <p>Every algorithm must implement this interface, implementing the method <b>run</b>.</p>
 * @param <T> The result of the algorithm.
 */
public interface Algorithm<T> {
    T run(Automaton automaton);
}
