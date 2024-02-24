package com.lvum.algorithms;

import com.lvum.Automaton;

import java.util.*;

/**
 * Minimization of a DFA using the <a href="https://www.geeksforgeeks.org/minimization-of-dfa/">Equivalence Method.</a>
 */
public class MinimizationEquivalence implements Algorithm<Automaton> {

    @Override
    public Automaton run(Automaton automaton) {
        Automaton result = new Automaton(automaton.getAlphabet());

        Set<String> finalStates = automaton.getFinalStates();
        Map<String, Set<String>> states = new HashMap<>();
        int k = 1;

        automaton.getStates().stream()
                .filter(state -> !automaton.isFinal(state))
                .forEach(state -> states.put(Integer.toString(k), new HashSet<>(Collections.singletonList(state))));


        return result;
    }
}
