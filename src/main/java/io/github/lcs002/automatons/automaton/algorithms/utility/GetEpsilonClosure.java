package io.github.lcs002.automatons.automaton.algorithms.utility;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;
import io.github.lcs002.automatons.automaton.algorithms.AutomatonAlgorithms;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h2>Algorithm for State ε-Closure</h2>
 * <p><b>Result:</b> <b>Set&lt;String&gt;</b> that contains the epsilon closure of a state.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *     <li>The automaton <b>{@link IsDFA must be a NFA}</b>.</li>
 * </ol>
 */
public class GetEpsilonClosure extends Algorithm<Set<String>> {
    private String state;


    public GetEpsilonClosure(Automaton automaton, String state) {
        super(automaton);
        this.state = state;
    }


    @Override
    public Set<String> call() {
        // TODO Change this to a iterative approach
        Set<String> closure = new HashSet<>();
        Set<String> epsilonNextStates = automaton.getTransitions().stream()
                .filter(transition -> transition.from().equals(state))
                .filter(transition -> transition.entry().equals(Automaton.EPSILON))
                .map(Automaton.Transition::to)
                .collect(Collectors.toSet());
        for (String nextState : epsilonNextStates) {
            closure.add(nextState);
            Set<String> nextClosure = AutomatonAlgorithms.eClosure(automaton, nextState);
            closure.addAll(nextClosure);
        }
        return closure;
    }
}
