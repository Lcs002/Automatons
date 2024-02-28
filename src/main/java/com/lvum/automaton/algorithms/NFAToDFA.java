package com.lvum.automaton.algorithms;

import com.lvum.automaton.Automata;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <h1>Converts NFA to DFA</h1>
 * <p>Converts an NFA to its equivalent DFA. Doesn't take into account Epsilon transitions.</p>
 * <p><b>Result:</b> <b>{@link Automata}</b> that is the equivalent DFA of the original NFA.</p>
 */
public class NFAToDFA implements Algorithm<Automata> {

    @Override
    public Automata run(Automata automata) {
        // The automata resultant of the conversion
        Automata result = new Automata(automata.getAlphabet());
        // List of Set of States we have already checked
        List<Set<String>> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<Set<String>> queue = new LinkedList<>();

        // Add and Mark the first State
        queue.add(new HashSet<>(Collections.singletonList(automata.getInitialState())));
        marked.add(new HashSet<>(Collections.singletonList(automata.getInitialState())));

        // Set the initial State of the new Automata
        result.setInitialState(automata.getInitialState());

        // While there is still some Set of States to check...
        while (!queue.isEmpty()) {
            Set<String> superstate = queue.poll();
            // Get all Transitions of the Superstate for each entry
            for (Character entry : automata.getAlphabet()) {
                Set<String> nextSuperstate = new HashSet<>();
                // Fill the next Superstate with the union of sets of next states of each current state in superstate
                for (String state : superstate) {
                    // Get next states of the current state with a certain entry
                    // And add it to the next Superstate
                    nextSuperstate.addAll(
                            automata.getTransitions().stream()
                                    .filter(transition -> transition.from().equals(state))
                                    .filter(transition -> transition.entry().equals(entry))
                                    .map(Automata.Transition::to)
                                    .collect(Collectors.toSet())
                    );
                }
                // If its empty it means there is no transition with this entry on the current superstate
                if (!nextSuperstate.isEmpty()) {
                    // Check if we have already evaluated the next Superstate
                    if (!marked.contains(nextSuperstate)) {
                        queue.add(nextSuperstate);
                        marked.add(nextSuperstate);
                        boolean isFinal = nextSuperstate.stream().anyMatch(automata::isFinal);
                        if (isFinal) result.addFinalState(String.join(Automata.SEPARATOR, nextSuperstate));
                    }
                    // Add the transition connecting current Superstate and next Superstate with certain entry
                    result.addTransition(
                            String.join(Automata.SEPARATOR, superstate),
                            String.join(Automata.SEPARATOR, nextSuperstate),
                            entry
                    );
                }
            }
        }
        // Finally return the new Automata
        return result;
    }
}
