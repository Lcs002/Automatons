package com.lvum.algorithms;

import com.lvum.Automaton;

import java.util.*;
import java.util.stream.Collectors;

public class NFAToDFA implements Algorithm {

    @Override
    public Automaton run(Automaton automaton) {
        // The automaton resultant of the conversion
        Automaton result = new Automaton(automaton.getLanguage());
        // List of Set of States we have already checked
        List<Set<String>> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<Set<String>> queue = new LinkedList<>();

        // Add and Mark the first State
        queue.add(new HashSet<>(Collections.singletonList(automaton.getInitialState())));
        marked.add(new HashSet<>(Collections.singletonList(automaton.getInitialState())));

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
                    nextSuperstate.addAll(
                            automaton.getTransitions().stream()
                                    .filter(transition -> transition.from().equals(state))
                                    .filter(transition -> transition.entry().equals(entry))
                                    .map(Automaton.Transition::to)
                                    .collect(Collectors.toSet())
                    );
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
        // Finally return the new Automaton
        return result;
    }
}
