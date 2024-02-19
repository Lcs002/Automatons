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
            for (Character entry : automaton.getLanguage()) {
                Set<String> nextSuperstate = new HashSet<>();
                for (String state : superstate) {
                    // Get next states of the current state with a certain entry
                    nextSuperstate.addAll(
                            automaton.getTransitionsFrom(state).stream()
                                    .filter(x -> x.entry().equals(entry))
                                    .map(Automaton.Transition::to)
                                    .collect(Collectors.toSet())
                    );
                }
                // Check if we have already evaluated the next state
                if (!marked.contains(nextSuperstate)) {
                    queue.add(nextSuperstate);
                    marked.add(nextSuperstate);
                }

                result.addTransition(
                        String.join("-", superstate),
                        String.join("-", nextSuperstate),
                        entry
                );
            }
        }
        return result;
    }
}
