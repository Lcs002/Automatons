package com.lvum.algorithms;

import com.lvum.Automaton;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Automaton Algorithm
 * <p>
 * Converts an NFA to its equivalent DFA, taking into account Epsilon transitions.
 */
public class NFAToDFAEpsilon implements Algorithm {

    @Override
    public Automaton run(Automaton automaton) {
        // The automaton resultant of the conversion
        Automaton result = new Automaton(automaton.getLanguage());
        // List of Set of States we have already checked
        List<Set<String>> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<Set<String>> queue = new LinkedList<>();
        // Initial State of the automaton
        String initialState = automaton.getInitialState();

        // Get the Epsilon Closure of the initial State
        Set<String> initialStates = getEpsilonClosure(initialState, automaton);
        initialStates.add(initialState);
        queue.add(initialStates);
        // Mark the initial State
        marked.add(new HashSet<>(Collections.singletonList(initialState)));

        // While there is still some Set of States to check...
        while (!queue.isEmpty()) {
            Set<String> superstate = queue.poll();
            // Get all Transitions of the Superstate for each entry
            for (Character entry : automaton.getLanguage()) {
                // Epsilon is not an entry, so we ignore it
                if (entry.equals(Automaton.EPSILON)) continue;
                Set<String> nextSuperstate = new HashSet<>();
                // Fill the next Superstate with the union of sets of next states of each current state in superstate
                for (String state : superstate) {
                    // Get next states of the current state with a certain entry
                    automaton.getTransitions().stream()
                            .filter(transition -> transition.from().equals(state))
                            .filter(transition -> transition.entry().equals(entry))
                            .map(Automaton.Transition::to)
                            .forEach(nextState -> {
                                // Add it to the next Superstate
                                nextSuperstate.add(nextState);
                                // Get the Epsilon Closure of the next states
                                // Add them to the next Superstate
                                nextSuperstate.addAll(getEpsilonClosure(nextState, automaton));
                            });
                }
                // If its empty it means there is no transition with this entry on the current superstate
                if (!nextSuperstate.isEmpty()) {
                    // Check if we have already evaluated the next Superstate
                    if (!marked.contains(nextSuperstate)) {
                        queue.add(nextSuperstate);
                        marked.add(nextSuperstate);
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
        return result;
    }

    private Set<String> getEpsilonClosure(String state, Automaton automaton) {
        Set<String> closure = new HashSet<>();
        Set<String> epsilonNextStates = automaton.getTransitions().stream()
                .filter(transition -> transition.from().equals(state))
                .filter(transition -> transition.entry().equals(Automaton.EPSILON))
                .map(Automaton.Transition::to)
                .collect(Collectors.toSet());
        for (String nextState : epsilonNextStates) {
            closure.add(nextState);
            closure.addAll(getEpsilonClosure(nextState, automaton));
        }
        return closure;
    }
}
