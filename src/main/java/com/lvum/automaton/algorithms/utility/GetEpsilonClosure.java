package com.lvum.automaton.algorithms.utility;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>Algorithm for State ε-Closure</h1>
 * <p><b>Result:</b> <b>Set&lt;String&gt;</b> that contains the epsilon closure of a state.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *     <li>The automaton <b>{@link IsDFA must be a NFA}</b>.</li>
 * </ol>
 */
public class GetEpsilonClosure implements Algorithm<Set<String>> {
    private String state;


    public GetEpsilonClosure(String state) {
        this.state = state;
    }


    @Override
    public Set<String> run(Automata automata) {
        // TODO Change this to a iterative approach
        Set<String> closure = new HashSet<>();
        Set<String> epsilonNextStates = automata.getTransitions().stream()
                .filter(transition -> transition.from().equals(state))
                .filter(transition -> transition.entry().equals(Automata.EPSILON))
                .map(Automata.Transition::to)
                .collect(Collectors.toSet());
        for (String nextState : epsilonNextStates) {
            closure.add(nextState);
            Set<String> nextClosure = automata.run(new GetEpsilonClosure(nextState));
            closure.addAll(nextClosure);
        }
        return closure;
    }
}
