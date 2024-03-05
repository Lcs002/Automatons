package com.github.lcs002.automatons.automaton.algorithms;

import com.github.lcs002.automatons.automaton.Automaton;

/**
 * <h2>Algorithm</h2>
 * <p>Interface that represents an algorithm.</p>
 * <p>Every algorithm must implement this interface, implementing the method <b>run</b>.</p>
 * @param <T> The result of the algorithm.
 */
public interface Algorithm<T> {
    T run(Automaton automaton);
}
