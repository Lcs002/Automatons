package com.lvum.algorithms.utility;

import com.lvum.Automaton;
import com.lvum.algorithms.Algorithm;

/**
 * <h1>Checks if an Automaton is a DFA</h1>
 * <p><b>Result:</b> <b>Boolean</b> that indicates if the automaton is a DFA.</p>
 */
public class IsDFA implements Algorithm<Boolean> {
    @Override
    public Boolean run(Automaton automaton) {
        return !automaton.getAlphabet().contains(Automaton.EPSILON)
                && automaton.getTransitions().stream().noneMatch(transition -> transition.entry().equals(Automaton.EPSILON))
                && automaton.getTransitions().stream()
                .mapToLong(transition -> automaton.getTransitions().stream()
                        .filter(t -> t.from().equals(transition.from()))
                        .filter(t -> t.entry().equals(transition.entry()))
                        .count())
                .max().orElse(0) <= 1;
    }
}
