package com.lvum.algorithms;

import com.lvum.Automaton;

import java.util.*;
import java.util.stream.Collectors;

public class NFAToDFA implements Algorithm {

    @Override
    public Automaton run(Automaton automaton) {
        Automaton result = new Automaton(automaton.getLanguage());
        List<Set<String>> marked = new ArrayList<>();
        Queue<Set<String>> queue = new LinkedList<>();

        queue.add(new HashSet<>(Collections.singletonList(automaton.getInitialState())));
        marked.add(new HashSet<>(Collections.singletonList(automaton.getInitialState())));

        while (!queue.isEmpty()) {
            Set<String> currentStates = queue.poll();
            for (String state : currentStates) {
                for (Character entry : automaton.getLanguage()) {
                    Set<String> nextState =
                            automaton.getTransitionsFrom(state).stream()
                                    .filter(x -> x.entry().equals(entry))
                                    .map(Automaton.Transition::to)
                                    .collect(Collectors.toSet());
                    if (!marked.contains(nextState)) {
                        queue.add(nextState);
                        marked.add(nextState);
                    }
                    result.addTransition(state, String.join("-", nextState), entry);
                }
            }
        }

        return result;
    }
}
