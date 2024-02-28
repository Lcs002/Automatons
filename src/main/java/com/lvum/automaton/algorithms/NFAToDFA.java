package com.lvum.automaton.algorithms;

import com.lvum.automaton.Automaton;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <h1>Converts NFA to DFA</h1>
 * <p>Converts an NFA to its equivalent DFA. Doesn't take into account Epsilon transitions.</p>
 * <p><b>Result:</b> <b>{@link Automaton}</b> that is the equivalent DFA of the original NFA.</p>
 */
public class NFAToDFA implements Algorithm<Automaton> {

    @Override
    public Automaton run(Automaton automaton) {
        // The automaton resultant of the conversion
        Automaton result = new Automaton(automaton.getAlphabet());
        // List of Set of States we have already checked
        List<Set<String>> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<Set<String>> queue = new LinkedList<>();

        // Add and Mark the first State
        queue.add(new HashSet<>(Collections.singletonList(automaton.getInitialState())));
        marked.add(new HashSet<>(Collections.singletonList(automaton.getInitialState())));

        // Set the initial State of the new Automaton
        result.setInitialState(automaton.getInitialState());

        // While there is still some Set of States to check...
        while (!queue.isEmpty()) {
            Set<String> superstate = queue.poll();
            // Get all Transitions of the Superstate for each entry
            for (Character entry : automaton.getAlphabet()) {
                Set<String> nextSuperstate = new HashSet<>();
                // Fill the next Superstate with the union of sets of next states of each current state in superstate
                for (String state : superstate) {
                    // Get next states of the current state with a certain entry
                    // And add it to the next Superstate
                    nextSuperstate.addAll(
                            automaton.getTransitions().stream()
                                    .filter(transition -> transition.from().equals(state))
                                    .filter(transition -> transition.entry().equals(entry))
                                    .map(Automaton.Transition::to)
                                    .collect(Collectors.toSet())
                    );
                }
                // If its empty it means there is no transition with this entry on the current superstate
                if (!nextSuperstate.isEmpty()) {
                    // Check if we have already evaluated the next Superstate
                    if (!marked.contains(nextSuperstate)) {
                        queue.add(nextSuperstate);
                        marked.add(nextSuperstate);
                        boolean isFinal = nextSuperstate.stream().anyMatch(automaton::isFinal);
                        if (isFinal) result.addFinalState(String.join(Automaton.SEPARATOR, nextSuperstate));
                    }
                    // Add the transition connecting current Superstate and next Superstate with certain entry
                    result.addTransition(
                            String.join(Automaton.SEPARATOR, superstate),
                            String.join(Automaton.SEPARATOR, nextSuperstate),
                            entry
                    );
                }
            }
        }
        // Finally return the new Automaton
        return result;
    }
}
