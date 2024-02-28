package com.lvum.automaton.algorithms.minimization;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;

import java.util.*;

/**
 * Minimization of a DFA using the <a href="https://www.geeksforgeeks.org/minimization-of-dfa/">Equivalence Method.</a>
 */
// TODO: Implement the Minimization of a DFA using the Equivalence Method
public class MinimizationEquivalence implements Algorithm<Automata> {

    @Override
    public Automata run(Automata automata) {
        Automata result = new Automata(automata.getAlphabet());

        Set<String> finalStates = automata.getFinalStates();
        Map<String, Set<String>> states = new HashMap<>();
        int k = 1;

        automata.getStates().stream()
                .filter(state -> !automata.isFinal(state))
                .forEach(state -> states.put(Integer.toString(k), new HashSet<>(Collections.singletonList(state))));


        return result;
    }
}
