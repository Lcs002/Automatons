package com.lvum.algorithms.utility;

import com.lvum.Automaton;
import com.lvum.algorithms.Algorithm;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Automaton Algorithm
 * <p>
 * Returns the epsilon closure of a state.
 */
public class GetEpsilonClosure implements Algorithm<Set<String>> {
    private String state;


    public GetEpsilonClosure(String state) {
        this.state = state;
    }


    @Override
    public Set<String> run(Automaton automaton) {
        // TODO Change this to a iterative approach
        Set<String> closure = new HashSet<>();
        Set<String> epsilonNextStates = automaton.getTransitions().stream()
                .filter(transition -> transition.from().equals(state))
                .filter(transition -> transition.entry().equals(Automaton.EPSILON))
                .map(Automaton.Transition::to)
                .collect(Collectors.toSet());
        for (String nextState : epsilonNextStates) {
            closure.add(nextState);
            Set<String> nextClosure = automaton.run(new GetEpsilonClosure(nextState));
            closure.addAll(nextClosure);
        }
        return closure;
    }
}
