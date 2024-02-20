package com.lvum.algorithms;

import com.lvum.Automaton;

import java.util.*;
import java.util.stream.Collectors;

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

        // Add and Mark the first State
        Set<String> initialStates = getEpsilonClosure(initialState, automaton);
        initialStates.add(initialState);
        queue.add(initialStates);
        marked.add(new HashSet<>(Collections.singletonList(initialState)));

        // While there is still some Set of States to check...
        while (!queue.isEmpty()) {
            Set<String> superstate = queue.poll();
            // Get all Transitions of the Superstate for each entry
            for (Character entry : automaton.getLanguage()) {
                Set<String> nextSuperstate = new HashSet<>();
                // Fill the next Superstate with the union of sets of next states of each current state in superstate
                for (String state : superstate) {
                    // Get next states of the current state with a certain entry
                    // And add it to the next Superstate
                    nextSuperstate.addAll(getEpsilonClosure(state, automaton));
                    automaton.getTransitionsFrom(state).stream()
                            .filter(x -> x.entry().equals(entry))
                            .map(Automaton.Transition::to)
                            .forEach(x -> {
                                nextSuperstate.add(x);
                                nextSuperstate.addAll(getEpsilonClosure(x, automaton));
                            });
                }
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
        return result;
    }

    private Set<String> getEpsilonClosure(String state, Automaton automaton) {
        Set<String> closure = new HashSet<>();
        Set<String> epsilonNextStates = automaton.getTransitionsFrom(state).stream()
                .filter(x -> x.entry().equals(Automaton.EPSILON))
                .map(Automaton.Transition::to)
                .collect(Collectors.toSet());
        for (String nextState : epsilonNextStates) {
            closure.addAll(getEpsilonClosure(nextState, automaton));
        }
        return closure;
    }
}
