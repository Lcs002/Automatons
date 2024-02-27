package com.lvum.algorithms.properties;

import com.lvum.Automaton;
import com.lvum.algorithms.Algorithm;
import com.lvum.algorithms.utility.IsComplete;
import com.lvum.algorithms.utility.IsDFA;

import java.util.*;

/**
 * <h1>Algorithm for Automaton Intersection</h1>
 *
 * <p><b>Result:</b> <b>NFA</b> that accepts the intersection of the language between both automata.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 *  <li> The automatons have the same <b>alphabet</b>.</li>
 * </ol>
 */
public class Intersection implements Algorithm<Automaton> {
    private Automaton other;


    public Intersection(Automaton other) {
        this.other = other;
    }


    @Override
    public Automaton run(Automaton automaton) {
        // The automata must be a dfa
        if (Boolean.FALSE.equals(automaton.run(new IsDFA()))) return null;
        // The automata must have the same alphabet
        if (!automaton.getAlphabet().equals(other.getAlphabet())) return null;
        Queue<Set<String>> toCheck = new LinkedList<>();
        List<Set<String>> checked = new ArrayList<>();
        // The result Automaton
        Automaton result = new Automaton(automaton.getAlphabet());
        result.setInitialState(automaton.getInitialState() + Automaton.SEPARATOR + other.getInitialState());
        return result;

        // Unite both starting state
        // For each state, test transitions with all symbols
        // Mark new state and add to queue
    }
}
