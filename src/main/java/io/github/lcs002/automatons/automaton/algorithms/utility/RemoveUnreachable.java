package io.github.lcs002.automatons.automaton.algorithms.utility;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;

import java.util.*;

/**
 * <h2>Removes Unreachable States from an Automaton</h2>
 * <p><b>Result:</b> <b>{@link Automaton}</b> without unreachable states.</p>
 */
public final class RemoveUnreachable extends Algorithm<Automaton> {

    public RemoveUnreachable(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton call() {
        // The automaton resultant of the reduction
        Automaton.Builder result = new Automaton.Builder().setAlphabet(automaton.getAlphabet());
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
                    if (automaton.isFinal(nextState)) {
                        // Add it as a Final State to the result automaton
                        result.addFinalState(nextState);
                    }
                }
            }
        }
        // Return the reduced automaton
        return result.build();
    }
}
