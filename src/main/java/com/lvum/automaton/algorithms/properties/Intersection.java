package com.lvum.automaton.algorithms.properties;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;
import com.lvum.automaton.algorithms.utility.IsComplete;
import com.lvum.automaton.algorithms.utility.IsDFA;
import org.javatuples.Pair;

import java.util.*;

/**
 * <h1>Algorithm for Automata Intersection</h1>
 *
 * <p><b>Result:</b> <b>NFA</b> that accepts the intersection of the language between both automata.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 *  <li> The automaton <b>{@link IsComplete is complete}</b>.</li>
 *  <li> The automatons have the same <b>alphabet</b>.</li>
 * </ol>
 */
public class Intersection implements Algorithm<Automata> {
    private final Automata other;


    public Intersection(Automata other) {
        this.other = other;
    }


    @Override
    public Automata run(Automata automata) {
        // The automata must be a dfa
        if (Boolean.FALSE.equals(automata.run(new IsDFA()))) return null;
        // The automata must be complete
        if (Boolean.FALSE.equals(automata.run(new IsComplete()))) return null;
        // The automata must have the same alphabet
        if (!automata.getAlphabet().equals(other.getAlphabet())) return null;

        Queue<Pair<String, String>> queue = new LinkedList<>();
        Set<Pair<String, String>> checked = new HashSet<>();
        // The result Automata
        Automata result = new Automata(automata.getAlphabet());
        // The initial state of the result automata is the intersection of the initial states of the two automata
        Pair<String, String> initialStates = new Pair<>(automata.getInitialState(), other.getInitialState());
        result.setInitialState(initialStates.getValue0() + Automata.SEPARATOR + initialStates.getValue1());
        queue.add(initialStates);
        checked.add(initialStates);
        // While there are states to check
        while (!queue.isEmpty()) {
            Pair<String, String> current = queue.poll();
            // For each symbol in the alphabet
            for (char symbol : automata.getAlphabet()) {
                // Get the next original state given the current state and the symbol
                String nextOriginal = automata.getTransitions().stream()
                        .filter(transition -> transition.from().equals(current.getValue0()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automata.Transition::to)
                        .findFirst()
                        .orElse(null);
                // Get the next other state given the current state and the symbol
                String nextOther = other.getTransitions().stream()
                        .filter(transition -> transition.from().equals(current.getValue1()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automata.Transition::to)
                        .findFirst()
                        .orElse(null);
                Pair<String, String> nextPair = new Pair<>(nextOriginal, nextOther);
                // If the next state has not been checked yet
                if (!checked.contains(nextPair)) {
                    // Add it to the queue
                    queue.add(nextPair);
                    // And mark the current as checked
                    checked.add(current);
                    // If both states are final, add the new state as final
                    if (automata.isFinal(nextOriginal) && other.isFinal(nextOther)) {
                        result.addFinalState(nextPair.getValue0() + Automata.SEPARATOR + nextPair.getValue1());
                    }
                }
                // Add the transition to the result automata
                result.addTransition(
                        current.getValue0() + Automata.SEPARATOR + current.getValue1(),
                        nextPair.getValue0() + Automata.SEPARATOR + nextPair.getValue1(),
                        symbol
                );
            }
        }
        // Return the result automata
        return result;
    }
}
