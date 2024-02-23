package com.lvum.algorithms.utility;

import com.lvum.Automaton;
import com.lvum.algorithms.Algorithm;

import java.util.*;

/**
 * Automaton Algorithm
 * <p>
 * Reduces a DFA so only accessible states are present.
 */
public class RemoveUnreachable implements Algorithm<Automaton> {

    @Override
    public Automaton run(Automaton automaton) {
        // The automaton resultant of the reduction
        Automaton result = new Automaton(automaton.getAlphabet());
        // List of Set of States we have already checked
        List<String> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<String> queue = new LinkedList<>();

        // Add and Mark the first State
        result.setInitialState(automaton.getInitialState());
        marked.add(automaton.getInitialState());
        queue.add(automaton.getInitialState());

        // While there is still some States to check...
        while (!queue.isEmpty()) {
            // Get the current State
            String state = queue.poll();
            // For each symbol in the language
            for (Character symbol : automaton.getAlphabet()) {
                // Get the next State of the current State given a certain symbol
                String nextState = automaton.getTransitions().stream()
                        .filter(transition -> transition.from().equals(state))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automaton.Transition::to)
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
                    // Add the transition to the result automaton
                    result.addTransition(state, nextState, symbol);
                    // If the next State is a Final State
                    if (automaton.getFinalStates().contains(nextState)) {
                        // Add it as a Final State to the result automaton
                        result.addFinalState(nextState);
                    }
                }
            }
        }
        // Return the reduced automaton
        return result;
    }
}
