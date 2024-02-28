package com.lvum.automaton.algorithms.utility;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;

import java.util.*;

/**
 * <h1>Removes Unreachable States from an Automata</h1>
 * <p><b>Result:</b> <b>{@link Automata}</b> without unreachable states.</p>
 */
public class RemoveUnreachable implements Algorithm<Automata> {

    @Override
    public Automata run(Automata automata) {
        // The automata resultant of the reduction
        Automata result = new Automata(automata.getAlphabet());
        // List of Set of States we have already checked
        List<String> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<String> queue = new LinkedList<>();

        // Add and Mark the first State
        result.setInitialState(automata.getInitialState());
        marked.add(automata.getInitialState());
        queue.add(automata.getInitialState());

        // While there is still some States to check...
        while (!queue.isEmpty()) {
            // Get the current State
            String state = queue.poll();
            // For each symbol in the language
            for (Character symbol : automata.getAlphabet()) {
                // Get the next State of the current State given a certain symbol
                String nextState = automata.getTransitions().stream()
                        .filter(transition -> transition.from().equals(state))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automata.Transition::to)
                        .findFirst()
                        .orElse(null);
                // If the next State is not null
                if (nextState != null) {
                    // And it hasn't been marked yet
                    if (!marked.contains(nextState)) {
                        // Mark it and add it to the queue
                        marked.add(state);
                        queue.add(nextState);
                    }
                    // Add the transition to the result automata
                    result.addTransition(state, nextState, symbol);
                    // If the next State is a Final State
                    if (automata.isFinal(nextState)) {
                        // Add it as a Final State to the result automata
                        result.addFinalState(nextState);
                    }
                }
            }
        }
        // Return the reduced automata
        return result;
    }
}
