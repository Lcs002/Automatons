package io.github.lcs002.automatons.automaton.algorithms;

import io.github.lcs002.automatons.Method;
import io.github.lcs002.automatons.automaton.Automaton;

/**
 * <h2>Algorithm</h2>
 * <p>Abstract class that represents an algorithm.</p>
 * <p>Every algorithm must extend this class, implementing the method <b>call</b>.</p>
 * @param <T> The result of the algorithm.
 */
public abstract class Algorithm<T> implements Method<T> {
    protected final Automaton automaton;


    protected Algorithm(Automaton automaton) {
        this.automaton = automaton;
    }
}
